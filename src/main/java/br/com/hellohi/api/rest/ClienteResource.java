/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.controller.NotificacoesController;
import br.com.hellohi.api.models.Cliente;
import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.models.enums.Perfil;
import br.com.hellohi.api.repository.ClienteRepository;
import br.com.hellohi.api.repository.RepresentanteRepository;
import br.com.hellohi.api.security.UserSS;
import br.com.hellohi.api.service.UserService;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
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

    public UserSS user() {
        return UserService.authenticated();
    }

    @PreAuthorize("hasAnyRole('OPERADOR')")
    @RequestMapping(path = "/sistema/cadastro/cliente", method = RequestMethod.GET)
    public ModelAndView listaClientePage() {

        Iterable<Cliente> cliente = listaCliente();
        ModelAndView mv = new ModelAndView("cadastro/listaCliente");

        mv.addObject("cliente", cliente);
        nc.carregaNotificacoesView(mv);

        return mv;

    }

    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "/sistema/cadastro/cliente-adicionar", method = RequestMethod.GET)
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
    @RequestMapping(path = "/hellohi/api/cliente", method = RequestMethod.GET)
    public Iterable<Cliente> listaCliente() {
        Iterable<Cliente> listaCliente = cr.findByEmpresa(user().getEmpresa());
        return listaCliente;
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "/hellohi/api/cliente/{idCliente}", method = RequestMethod.GET)
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
    @RequestMapping(path = "/hellohi/api/cliente", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
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
            adicionarEmpresa(cliente);

            cr.save(cliente);

            ModelAndView mv = new ModelAndView();
            mv.setViewName("redirect:/sistema/cadastro/cliente");
            atributes.addFlashAttribute("mensagem", "Cliente - " + cliente.getCodigoInternoCliente() + " - " + cliente.getRazaoSocial() + " salvo com sucesso.");

            return mv;

        } catch (Exception e) {
            System.out.println("Erro ao Salvar Cliente " + e);
        }

        throw new AuthorizationServiceException("Você não tem permissão!");

    }

    //Edita Cliente
    @PreAuthorize("hasRole('SUPERVISOR')")
    @RequestMapping(path = "/hellohi/api/cliente", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView editarCliente(@Valid Cliente cliente, BindingResult result, RedirectAttributes atributes) {

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
            adicionarEmpresa(cliente);

            cr.save(cliente);

            ModelAndView mv = new ModelAndView();
            mv.setViewName("redirect:/sistema/cadastro/cliente");
            atributes.addFlashAttribute("mensagem", "Cliente - " + cliente.getCodigoInternoCliente() + " -  editado com sucesso.");

            return mv;
        } catch (Exception e) {
            System.out.println("Erro ao Salvar Cliente " + e);
        }

        throw new AuthorizationServiceException("Você não tem permissão!");
    }

    //Deletar Cliente
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @RequestMapping(path = "/hellohi/api/cliente/{idCliente}", method = RequestMethod.DELETE)
    public ModelAndView deletaPorId(@PathVariable("idCliente") Long idCliente, RedirectAttributes atributes) {

        cliente = clientePorId(idCliente);
        cliente.setAtivo(false);//desativ o usuario
        cr.save(cliente);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/sistema/cadastro/cliente");
        atributes.addFlashAttribute("mensagem", "Cliente - " + cliente.getCodigoInternoCliente() + " removido com sucesso.");//Adiciona Variavel Mensagem para View

        return mv;
    }

    //Apaga a Notificação da View que o Cliente Enviou
    @PreAuthorize("hasAnyRole('OPERADOR')")
    @RequestMapping(path = "/hellohi/api/apagar/notificacao/{idCliente}", method = RequestMethod.POST)
    public ModelAndView apagarNotificacao(@PathVariable("idCliente") Long idCliente) {
        try {
            cliente = clientePorId(idCliente);
            cliente.setMsgNotificacao(false);
            cliente = cr.save(cliente);
        } catch (Exception e) {
            System.out.println(e);
        }
        ModelAndView mv = new ModelAndView("redirect:/sistema");
        return mv;
    }

    //Recebe email enviado do APP e busca no banco o cliente para ativar as notificacoes
    @RequestMapping(path = "/hellohi/api/notificacao/ativar", method = RequestMethod.POST)
    public void ativarNotificacao(@RequestParam("email") String emailCliente) {

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
    @RequestMapping(path = "/hellohi/api/carteiraCliente", method = RequestMethod.POST)
    public Iterable<Cliente> listarClientePorRepresentante(@RequestParam("email") String email) {
        System.out.println(email);
        try {
            try {
                representante = pegarRepresentantePorLogin(email);
                System.out.println("Representante = " + representante.getNome());
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
    @RequestMapping(path = "/hellohi/api/cliente/cadastrarDispositivo", method = RequestMethod.POST)
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
}
