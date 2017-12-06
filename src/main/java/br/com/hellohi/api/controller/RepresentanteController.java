/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.controller;

import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.repository.RepresentanteRepository;
import br.com.hellohi.api.rest.EmpresaResource;
import br.com.hellohi.api.rest.RepresentanteResource;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author junior
 */

@Component
@Controller
public class RepresentanteController {

    @Autowired
    RepresentanteResource rr;
    RepresentanteRepository representanteRepository;

    @Autowired
    EmpresaResource er;
    Empresa empresa;

    @Autowired
    PaginaController pc;

    @RequestMapping(path="/representante", method = RequestMethod.GET)
    public ModelAndView carregaRepresentante() {
        ModelAndView mvRepresentante = new ModelAndView("representante/listaRepresentante");
        Iterable<Representante> representantes = rr.listaRepresentante();
        empresa = er.buscarEmpresaId(1l);
        mvRepresentante.addObject("representante", representantes);
        mvRepresentante.addObject("empresa", empresa);
        return mvRepresentante;
    }

//    @RequestMapping(path = "/hellohi/api/empresa/{idEmpresa}/representante", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE , MediaType.APPLICATION_JSON_VALUE} ,headers = "content-type=application/x-www-form-urlencoded , application/json",produces = {MediaType.APPLICATION_JSON_VALUE})
//    public  String salvarRepresentante(@Valid Representante representante , Errors erros , Long idEmpresa ) {
//        if(erros.hasErrors()) {
//            System.out.println(erros.getAllErrors());
//            return "/index/index";
//        }
//        empresa = er.buscarEmpresaId(idEmpresa);
//        representante.setIdRepresentante(null);
//        representante.setEmpresa(empresa);
//        representanteRepository.save(representante);
//        return "Sucesso";
//    }
    
    
    
    
    
//    //Salva Representante relacionado com a Empresa que foi passado o Id pelo Combobox e pego no Request
//    @RequestMapping(path="/hellohi/api/representante/{idEmpresa}", method= RequestMethod.POST)
//    public String salvarRepresentante(@RequestBody Representante representante, @Valid Errors erros, @PathParam("idEmpresa") Long idEmpresa) {
//        if(erros.hasErrors()) {
//            System.out.println(erros.getAllErrors());
//            return "/index/index";
//        }
//        
//        empresa = er.buscarEmpresaId(idEmpresa);
////        representante.setIdRepresentante(null);
//        representante.setEmpresa(empresa); 
//        System.out.println("Representante"+ representante.getNome());
//        representanteRepository.save(representante);
//        return pc.abrirPaginaInicial();
//    }

}
