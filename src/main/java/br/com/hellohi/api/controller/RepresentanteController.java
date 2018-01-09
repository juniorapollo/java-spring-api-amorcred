/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.controller;

import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.rest.RepresentanteResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    Representante representante;

    @RequestMapping(path = "cadastro/representante", method = RequestMethod.GET)
    public ModelAndView listarRepresentantes() {
        ModelAndView mv = new ModelAndView("cadastro/listaRepresentante");

        Iterable<Representante> representantes = rr.listaRepresentante();
        mv.addObject("representante", representantes);

        return mv;
    }

    

    //Requisição para Editar Representante
    @RequestMapping(path = "cadastro/representante-editar/{idRepresentante}", method = RequestMethod.GET)
    public ModelAndView editaRepresentante(@PathVariable("idRepresentante") Long idRepresentante) {
        this.representante = rr.representantePorId(idRepresentante);
        ModelAndView mv = new ModelAndView("cadastro/editar/editaRepresentante");
        mv.addObject("representante", representante);
        return mv;
    }
}
