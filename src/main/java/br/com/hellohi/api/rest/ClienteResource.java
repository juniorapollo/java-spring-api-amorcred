/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.controller.NotificacoesController;
import br.com.hellohi.api.models.Alerta;
import br.com.hellohi.api.models.Cliente;
import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.models.enums.Perfil;
import br.com.hellohi.api.repository.AlertaRepository;
import br.com.hellohi.api.repository.ClienteRepository;
import br.com.hellohi.api.repository.RepresentanteRepository;
import br.com.hellohi.api.security.UserSS;
import br.com.hellohi.api.service.UserService;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author junior
 */
@RestController
public class ClienteResource {

    @Autowired
    BCryptPasswordEncoder senha;

    @Autowired
    ClienteRepository cr;
    Cliente cliente;

    @Autowired
    RepresentanteResource rr;

    @Autowired
    RepresentanteRepository representanteRepository;
    Representante representante;

    @Autowired
    EmpresaResource er;
    Empresa empresa;

    @Autowired
    NotificacoesController nc;

    @Autowired
    CheckinResource checkinResource;

    @Autowired
    AlertaRepository ar;
    Alerta alerta;

    @Value("${baseUrl}")
    private String baseUrl;

    public UserSS user() {
        return UserService.authenticated();
    }

    @PreAuthorize("hasAnyRole('OPERADOR')")
    @RequestMapping(path = "${baseUrl}/sistema/cadastro/cliente", method = RequestMethod.GET)
    public ModelAndView listaClientePage() {

        Iterable<Cliente> cliente = listaCliente();
        ModelAndView mv = new ModelAndView("cadastro/listaCliente");

        mv.addObject("cliente", cliente);
        nc.carregaNotificacoesView(mv);

        return mv;

    }

    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "${baseUrl}/sistema/cadastro/cliente-adicionar", method = RequestMethod.GET)
    public ModelAndView adicionarCliente(Cliente cliente) {
        ModelAndView mv = new ModelAndView("cadastro/adicionar/adicionarCliente");
        mv.addObject("cliente", cliente);
        try {
            mv.addObject("representante", rr.listaRepresentante());
        } catch (Exception e) {
            System.out.println("Cliente Resourse _ Não Carregou a Lista de Representante");
        }
        nc.carregaNotificacoesView(mv);
        return mv;
    }

    @PreAuthorize("hasAnyRole('OPERADOR','REPRESENTANTE')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/cliente", method = RequestMethod.GET)
    public Iterable<Cliente> listaCliente() {
        Iterable<Cliente> listaCliente = cr.findByEmpresa(user().getEmpresa());
        return listaCliente;
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/cliente/{idCliente}", method = RequestMethod.GET)
    public Cliente clientePorId(@PathVariable("idCliente") Long idCliente) {
        try {
            cliente = cr.findByIdCliente(idCliente);
            if (Objects.equals(cliente.getEmpresa().getIdEmpresa(), user().getEmpresa().getIdEmpresa())) {
                return cliente;
            }

        } catch (Exception e) {
            System.out.println("IdUsuário Não Existe : " + e);
        }

        System.out.println("Usuário não pertence a Empresa");
        return null;
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/cliente", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView salvarCliente(@Valid Cliente cliente, BindingResult result, RedirectAttributes atributes) {

        if (result.hasErrors()) {
            return adicionarCliente(cliente);
        }
        try {
           
            representante = rr.representantePorId(cliente.getRepresentante().getIdRepresentante());         
            cliente.setRepresentante(representante);           
            cliente.setSenha(senha.encode(cliente.getSenha()));       
            cliente.addPerfil(Perfil.CLIENTE);          
            cliente.setLogin(cliente.getEmail());          
            cliente.setAtivo(true);           
            cliente.setMsgNotificacao(false);        
            cliente.setEmpresa(er.buscarEmpresaId(1l));
           try {
                setarCoordenadasCliente(cliente, cliente.getLogradouro(), cliente.getNumeroEnd(), cliente.getBairro(), cliente.getCidade(), cliente.getEstado());
           } catch (Exception e) {
                System.err.println("Error , Catch Salvar Cliente:164 não conveteu em Lat e Lng " + e);
                cr.save(cliente);
                ModelAndView mv = new ModelAndView();
                mv.setViewName("redirect:" + baseUrl + "/sistema/cadastro/cliente");
                atributes.addFlashAttribute("mensagem", "Cliente - " + cliente.getCodigoInternoCliente() + " - " + cliente.getRazaoSocial() + " salvo com sucesso.");
                return mv;
            }
            cr.save(cliente);
                ModelAndView mv = new ModelAndView();
                mv.setViewName("redirect:" + baseUrl + "/sistema/cadastro/cliente");
                atributes.addFlashAttribute("mensagem", "Cliente - " + cliente.getCodigoInternoCliente() + " - " + cliente.getRazaoSocial() + " salvo com sucesso.");
                return mv;

        } catch (Exception e) {
            System.out.println("Erro " + e);
            result.addError(new ObjectError("Email Duplicado", "Email já cadastrado."));
            return adicionarCliente(cliente);
        }

    }

    //Edita Cliente
    @PreAuthorize("hasRole('SUPERVISOR')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/cliente", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView editarCliente(@Valid Cliente cliente, BindingResult result, RedirectAttributes atributes) {

        if (result.hasErrors()) {
            return adicionarCliente(cliente);
        }
        try {

            if (cliente.getSenha().length() > 6) {
                cr.save(cliente);
            } else {
                cliente.setSenha(senha.encode(cliente.getSenha()));
            }

            representante = rr.representantePorId(cliente.getRepresentante().getIdRepresentante());
            cliente.setRepresentante(representante);
            cliente.addPerfil(Perfil.CLIENTE);
            cliente.setLogin(cliente.getEmail());
            cliente.setAtivo(true);
            cliente.setMsgNotificacao(false);
            // cliente.setIdDispositivo(null);
            UserSS user = UserService.authenticated();
            cliente.setEmpresa(user.getEmpresa());
            setarCoordenadasCliente(cliente, cliente.getLogradouro(), cliente.getNumeroEnd(), cliente.getBairro(), cliente.getCidade(), cliente.getEstado());

            cr.save(cliente);

            ModelAndView mv = new ModelAndView();
            mv.setViewName("redirect:" + baseUrl + "/sistema/cadastro/cliente");
            atributes.addFlashAttribute("mensagem", "Cliente - " + cliente.getCodigoInternoCliente() + " -  editado com sucesso.");

            return mv;
        } catch (Exception e) {
            System.out.println("Catch PUT:" + e);
            result.addError(new ObjectError("Email Duplicado", "Email já cadastrado."));
            return adicionarCliente(cliente);
        }

    }

    //Deletar Cliente
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/cliente/{idCliente}", method = RequestMethod.DELETE)
    public ModelAndView deletaPorId(@PathVariable("idCliente") Long idCliente, RedirectAttributes atributes) {

        cliente = clientePorId(idCliente);
        cliente.setAtivo(false);//desativa o usuario
        cr.save(cliente);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:" + baseUrl + "/sistema/cadastro/cliente");
        atributes.addFlashAttribute("mensagem", "Cliente - " + cliente.getCodigoInternoCliente() + " removido com sucesso.");//Adiciona Variavel Mensagem para View

        return mv;
    }

    //Apaga a Notificação da View que o Cliente Enviou
    @PreAuthorize("hasAnyRole('OPERADOR')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/apagar/notificacao/{idCliente}", method = RequestMethod.POST)
    public ModelAndView apagarNotificacao(@PathVariable("idCliente") Long idCliente) {

        try {
            cliente = clientePorId(idCliente);
            cliente.setMsgNotificacao(false);
            cliente = cr.save(cliente);
        } catch (Exception e) {
            System.out.println(e);
        }
        ModelAndView mv = new ModelAndView("redirect:" + baseUrl + "/sistema");
        return mv;
    }

    //Ativar notificacoes 
    //Recebe email e a mensagem que o cliente digitarno app  e busca no banco o cliente para ativar as notificacoes
    @RequestMapping(path = "${baseUrl}/hellohi/api/notificacao/ativar", method = RequestMethod.POST)
    public void ativarNotificacao(@RequestParam("email") String emailCliente, @RequestParam("msgObs") String msgObs, @RequestParam("msgValor") String msgValor) {

        try {
            try {
                cliente = cr.findByLogin(emailCliente);
            } catch (Exception e) {
                System.out.println("CLIENTE RESOURCE , Erro ao buscar o Cliente por EMAIL, ao efetuar a requisição de operação" + e);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        cliente.setMsgNotificacao(true); //Notificacão Verdadeira
        cliente.setMsgObservacao(msgObs);
        cliente.setMsgValor(msgValor);
        cliente = cr.save(cliente); // Salvando Cliente
    }

    //Adiciona a empresa do usuario logado no Cliente 
    public void adicionarEmpresa(Cliente cliente) {
        UserSS user = UserService.authenticated();
        cliente.setEmpresa(user.getEmpresa());
    }

    //Adiciona a empresa do usuario logado no Cliente 
    public Representante pegarRepresentantePorLogin(String email) {
        representante = representanteRepository.findByLogin(email);
        return representante;
    }

    //Lista empresa por representante
    @PreAuthorize("hasAnyRole('OPERADOR','REPRESENTANTE')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/carteiraCliente", method = RequestMethod.POST)
    public Iterable<Cliente> listarClientePorRepresentante(@RequestParam("email") String email) {

        try {
            try {
                representante = pegarRepresentantePorLogin(email);

            } catch (Exception e) {
                System.out.println("CLIENTE RESOURCE , Erro ao buscar o Representante por EMAIL, ao efetuar o pedido de carteira de cliente" + e);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        Iterable<Cliente> clientes = cr.findByRepresentante(representante);
        System.out.println(clientes);
        return clientes;
    }

    @PreAuthorize("hasAnyRole('CLIENTE','REPRESENTANTE')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/cliente/cadastrarDispositivo", method = RequestMethod.POST)
    public void cadastrarIdDispositivo(@RequestParam("email") String email, HttpServletRequest req) {
        String idDispositivo = req.getHeader("idDispositivo");
        try {
            try {
                cliente = cr.findByLogin(email);
                cliente.setIdDispositivo(idDispositivo);
                cr.save(cliente);
            } catch (Exception e) {
                System.out.println("CLIENTE RESOURCE , Erro ao buscar o Cliente por EMAIL, ao cadastrar Dispositivo" + e);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setarCoordenadasCliente(Cliente cli, String logradouro, int numeroEnd, String bairro, String cidade, String estado) throws JSONException, IOException {
        double array[] = checkinResource.converterEnderecoLatitudeLongitude(logradouro, numeroEnd, bairro, cidade, estado);
        cli.setLatitude(array[0]);
        cli.setLongitude(array[1]);
    }

}
