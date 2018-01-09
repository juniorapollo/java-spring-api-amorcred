/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.models.Cliente;
import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.repository.ClienteRepository;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    ClienteRepository cr;
    Cliente cliente;

    @Autowired
    RepresentanteResource rr;
    Representante representante;

    @Autowired
    EmpresaResource er;
    Empresa empresa;

    @RequestMapping(path = "cadastro/cliente-adicionar", method = RequestMethod.GET)
    public ModelAndView adicionarCliente(Cliente cliente) {

        ModelAndView mv = new ModelAndView("cadastro/adicionar/adicionarCliente");
        mv.addObject("cliente", cliente);
        return mv;
    }

    @RequestMapping(path = "/hellohi/api/cliente", method = RequestMethod.GET)
    public Iterable<Cliente> listaCliente() {
        Iterable<Cliente> listaCliente = cr.findAll();
        return listaCliente;
    }

    @RequestMapping(path = "/hellohi/api/cliente/{idCliente}", method = RequestMethod.GET)
    public Cliente clientePorId(@PathVariable("idCliente") Long idCliente) {
        cliente = cr.findByIdCliente(idCliente);
        return cliente;
    }

    @RequestMapping(path = "/hellohi/api/cliente", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView salvarCliente(@Valid Cliente cliente, BindingResult result, RedirectAttributes atributes) {

        if (result.hasErrors()) {
            return adicionarCliente(cliente);
        }

        if (cliente.getIdCliente() == null) {
            System.out.println("ID NULO");
            representante = rr.representantePorId(1l);
            cliente.setRepresentante(representante);
            cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));

            cr.save(cliente);

            ModelAndView mv = new ModelAndView();
            mv.setViewName("redirect:/cadastro/cliente");
            atributes.addFlashAttribute("mensagem", "Cliente - " + cliente.getCodigoInternoCliente() + " - " + cliente.getRazaoSocial() + " salvo com sucesso.");

            return mv;
        } else {
            cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
            cr.save(cliente);

            ModelAndView mv = new ModelAndView();
            mv.setViewName("redirect:/cadastro/cliente");
            atributes.addFlashAttribute("mensagem", "Cliente - " + cliente.getCodigoInternoCliente() + " -  editado com sucesso.");

            return mv;
        }

    }

    //Edita Cliente
    @RequestMapping(path = "/hellohi/api/cliente", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView editarCliente(@Valid Cliente cliente, BindingResult result, RedirectAttributes atributes) {

        if (result.hasErrors()) {
            return adicionarCliente(cliente);
        } else {
          
            cliente = cr.save(cliente); 

            ModelAndView mv = new ModelAndView();
            mv.setViewName("redirect:/cadastro/cliente");
            atributes.addFlashAttribute("mensagem", "Cliente - " + cliente.getCodigoInternoCliente() + " -  editado com sucesso.");

            return mv;
        }

    }

    //Deletar Cliente
    @RequestMapping(path = "/hellohi/api/cliente/{idCliente}", method = RequestMethod.DELETE)
    public ModelAndView deletaPorId(@PathVariable("idCliente") Long idCliente, RedirectAttributes atributes) {

        cliente = cr.findByIdCliente(idCliente);
        cliente.setAtivo(false);//desativ o usuario
        cr.save(cliente);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/cadastro/cliente");
        atributes.addFlashAttribute("mensagem", "Cliente - " + cliente.getCodigoInternoCliente() + " removido com sucesso.");//Adiciona Variavel Mensagem para View

        return mv;
    }

//    //Listar Clientes dos respectivos Representantes Comerciais{idRepresentante}  e avisar se um determinado Representante nao tem Cliente Cadastrado
//    @RequestMapping(path = "/hellohi/api/representante/cliente/{idRepresentante}", method = RequestMethod.GET)
//    public ArrayList<Cliente> listarClientePorRepresentante(@PathVariable("idRepresentante") Long idRepresentante) {
//        representante = rr.representantePorId(idRepresentante);
//        ArrayList<Cliente> novaListaCliente = new ArrayList<>();
//        try {
//            for (Cliente c : listaCliente()) {
//                if (c.getRepresentante() == representante) {
//                    novaListaCliente.add(c);
//                }
//            }
//            if (novaListaCliente.isEmpty()) {
//                System.out.println("REPRESENTANTE SEM CADASTRO DE CLIENTE");
//            }
//            return novaListaCliente;
//        } catch (Exception e) {
//            return novaListaCliente;
//        }
//    }
    //Listar Clientes dos respectivas Empresa {idEmpresa}
    @RequestMapping(path = "/hellohi/api/empresa/cliente/{idEmpresa}", method = RequestMethod.GET)
    public ArrayList<Cliente> listarClientePorEmpresa(@PathVariable("idEmpresa") Long idEmpresa) {
        ArrayList<Cliente> novaListaCliente = new ArrayList<>();
        empresa = er.buscarEmpresaId(idEmpresa);
        try {
            for (Cliente c : listaCliente()) {
                if (c.getRepresentante().getEmpresa() == empresa) {
                    novaListaCliente.add(c);
                }
            }
            return novaListaCliente;
        } catch (Exception e) {
            return novaListaCliente;
        }

    }

}
