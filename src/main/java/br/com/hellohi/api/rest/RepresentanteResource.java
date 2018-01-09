/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.controller.RepresentanteController;
import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.repository.RepresentanteRepository;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
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
@Controller
public class RepresentanteResource {

    @Autowired
    RepresentanteRepository rr;
    Representante representante;
    RepresentanteController rc;

    @Autowired
    EmpresaResource empresaResource;
    Empresa empresa;

    @RequestMapping(path = "cadastro/representante-adicionar", method = RequestMethod.GET)
    public ModelAndView adicionarRepresentante(Representante representante) {
        ModelAndView mv = new ModelAndView("cadastro/adicionar/adicionarRepresentante");
        mv.addObject("representante", representante);
        return mv;
    }
    
    
    @RequestMapping(path = "/hellohi/api/representante", method = RequestMethod.GET)
    public Iterable<Representante> listaRepresentante() {
        Iterable<Representante> listaRepresentante = rr.findAll();
        return listaRepresentante;
    }

    @RequestMapping(path = "/hellohi/api/representante/{idRepresentante}", method = RequestMethod.GET)
    public Representante representantePorId(@PathVariable("idRepresentante") Long idRepresentante) {
        return rr.findByIdRepresentante(idRepresentante);
    }

    @RequestMapping(path = "/hellohi/api/representante", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView salvarRepresentante(@Valid Representante representante, BindingResult result, RedirectAttributes atributes) {

        if (result.hasErrors()) {
            return adicionarRepresentante(representante);
        }

        empresa = empresaResource.buscarEmpresaId(2l);
        representante.setEmpresa(empresa);        
        representante.setSenha(new BCryptPasswordEncoder().encode(representante.getSenha()));
        
        rr.save(representante);

        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/cadastro/representante");
        atributes.addFlashAttribute("mensagem", "Representante - " + representante.getIdRepresentante() + " " + representante.getNome() + " salvo com sucesso.");

        return mv;
    }

    //Edita representante
    @RequestMapping(path = "/hellohi/api/representante", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView editarRepresentante(@Valid Representante representante,  BindingResult result, RedirectAttributes atributes) {

        if (result.hasErrors()) {
            return adicionarRepresentante(representante);
        }
        
        rr.save(representante);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/cadastro/representante");// Redireciona para o Controller Representante
        atributes.addFlashAttribute("mensagem", "Representante - " + representante.getIdRepresentante() + " editado com sucesso.");//Adiciona Variavel Mensagem para View
        
        return mv;
    }

    @RequestMapping(path = "hellohi/api/representante/{idRepresentante}", method = RequestMethod.DELETE)
    public ModelAndView deletarRepresentante(@PathVariable("idRepresentante") Long idRepresentante, RedirectAttributes atributes) {
        
        representante = rr.findByIdRepresentante(idRepresentante);
        representante.setAtivo(false);//desativ o usuario
        rr.save(representante);
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/cadastro/representante");
        atributes.addFlashAttribute("mensagem", "Representante - " + representante.getIdRepresentante() + " removido com sucesso.");//Adiciona Variavel Mensagem para View
        
        return mv;
    }

    //Método Retorna todos Representantes que se relacionam com a Impresa no qual foi passado como parametro o id {idEmpresa}
    @RequestMapping(path = "hellohi/api/representante/empresa/{idEmpresa}", method = RequestMethod.GET)
    public ArrayList<Representante> listarRepresentantePorEmpresa(@PathVariable("idEmpresa") Long idEmpresa) {
        empresa = empresaResource.buscarEmpresaId(idEmpresa);
        ArrayList<Representante> novaListaRepresentante = new ArrayList<>();
        try {
            for (Representante r : listaRepresentante()) {
                if (r.getEmpresa() == empresa) {
                    novaListaRepresentante.add(r);
                }
            }
            return novaListaRepresentante;
        } catch (Exception e) {
            return novaListaRepresentante;
        }
    }

    //ESSE ESTA SALVANDO NO HML MAIS NAO NO POSTMAN  <NAO RETORNA A PAGINA>
//    //Salvar representante passando o Id do Empresa
//    @RequestMapping(path = "/hellohi/api/empresa/{idEmpresa}/representante", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE , MediaType.APPLICATION_JSON_VALUE} ,headers = "content-type=application/x-www-form-urlencoded , application/json",produces = {MediaType.APPLICATION_JSON_VALUE})
//    public String salvarRepresentante(@Valid Representante representante, @PathVariable Long idEmpresa) {
//        empresa = er.buscarEmpresaId(idEmpresa);
//        representante.setIdRepresentante(null);
//        representante.setEmpresa(empresa);
//        rr.save(representante);
//        return "Sucesso";
//    }
}
