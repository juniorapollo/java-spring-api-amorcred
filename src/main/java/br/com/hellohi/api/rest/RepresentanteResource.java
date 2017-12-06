/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.controller.PaginaController;
import br.com.hellohi.api.controller.RepresentanteController;
import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.repository.EmpresaRepository;
import br.com.hellohi.api.repository.RepresentanteRepository;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    EmpresaResource er;
    Empresa empresa;
    
    @Autowired
    PaginaController pc;

    @RequestMapping(path = "/hellohi/api/representante", method = RequestMethod.GET)
    public Iterable<Representante> listaRepresentante() {
        Iterable<Representante> listaRepresentante = rr.findAll();
        return listaRepresentante;

    }

    @RequestMapping(path = "/hellohi/api/representante/{idRepresentante}", method = RequestMethod.GET)
    public Representante representantePorId(@PathVariable("idRepresentante") Long idRepresentante) {
        return rr.findByIdRepresentante(idRepresentante);
    }

   
    

    //Edita representante passando o Id do Representante
    @RequestMapping(path = "/hellohi/api/empresa/representante/{idRepresentante}", method = RequestMethod.PUT)
    public Representante editarRepresentante(@RequestBody @Valid Representante representante, @PathVariable Long idRepresentante) {
        empresa = er.buscarEmpresaId(representantePorId(idRepresentante).getEmpresa().getIdEmpresa());
        representante.setIdRepresentante(idRepresentante);
        representante.setEmpresa(empresa);
        representante = rr.save(representante);
        return representante;
    }
    
    

    @RequestMapping(path = "hellohi/api/representante/{idRepresentante}", method = RequestMethod.DELETE)
    public String deletarRepresentante(@PathVariable("idRepresentante") Long idRepresentante) {
        rr.delete(representantePorId(idRepresentante));
        return "HTML DE Representante DELETADO COM SUCESSO"; //metodo vai ser void e retornar um metodo do paginaController que é uma pagina de Deletado com Sucesso
    }

    //Método Retorna todos Representantes que se relacionam com a Impresa no qual foi passado como parametro o id {idEmpresa}
    @RequestMapping(path = "hellohi/api/representante/empresa/{idEmpresa}", method = RequestMethod.GET)
    public ArrayList<Representante> listarRepresentantePorEmpresa(@PathVariable("idEmpresa") Long idEmpresa) {
        empresa = er.buscarEmpresaId(idEmpresa);
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
    
    @RequestMapping(path = "/hellohi/api/empresa/{idEmpresa}/representante", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE , MediaType.APPLICATION_JSON_VALUE} ,headers = "content-type=application/x-www-form-urlencoded , application/json",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView salvarRepresentante(@Valid Representante representante , Errors erros , Long idEmpresa ) {
        ModelAndView mv = new ModelAndView();
         mv.setViewName("redirect:/home");
        if(erros.hasErrors()) {
            System.out.println(erros.getAllErrors());
            return mv;
        }        
        empresa = er.buscarEmpresaId(idEmpresa);
        representante.setIdRepresentante(null);
        representante.setEmpresa(empresa);
        rr.save(representante);
        return mv;
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
