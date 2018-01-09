/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.controller;

import br.com.hellohi.api.models.Checkin;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.rest.CheckinResource;
import br.com.hellohi.api.rest.RepresentanteResource;
import org.apache.commons.codec.language.RefinedSoundex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** 
 *
 * @author junior
 */
@Controller
public class CheckinController {

    @Autowired
    CheckinResource cr;

    @Autowired
    RepresentanteResource rr;

    @RequestMapping(path = "/checkin", method = RequestMethod.GET)
    public ModelAndView loadCheckin() {
        ModelAndView mv = new ModelAndView("checkin/checkin");
//        ArrayList <Checkin> checkins = new ArrayList<>();
//        checkins.add(cr.pegarCheckinPorId(47l));
//        checkins.add(cr.pegarCheckinPorId(1l));
        Checkin c = cr.pegarCheckinPorId(45l);
        mv.addObject("checkin", c);
        return mv;
    }

//    @RequestMapping(path = "/checkin/relatorio", method = RequestMethod.GET)
//    public ModelAndView pageListaCheckin() throws IOException, JSONException {
//
//        ModelAndView mv = new ModelAndView("checkin/checkinRelatorio");
//
//        Iterable<Representante> representantes = rr.listaRepresentante();
//        Iterable<Checkin> checkins = cr.listaCheckin();
//
//        mv.addObject("representante", representantes);
//        mv.addObject("checkin", checkins);
//
//        return mv;
//    }
//
    @RequestMapping(path = "/checkin/relatorio/{idRepresentante}/{idCheckinMapa}", method = RequestMethod.GET)
    public ModelAndView setarRepresentanteView(@PathVariable("idRepresentante") Long idRepresentante,@PathVariable("idCheckinMapa") Long idCheckinMapa, RedirectAttributes atributes) {

        Checkin checkinMapa = cr.pegarCheckinPorId(idCheckinMapa);
        Iterable<Checkin> checkins = cr.listaCheckin();
        Iterable<Representante> representantes = rr.listaRepresentante();
        Iterable<Checkin> checkinsRepresentante = cr.checkinsPorRepresentante(idRepresentante);

        ModelAndView mv = new ModelAndView("checkin/checkinRelatorio");

        if (idRepresentante == 0 && idCheckinMapa == 0 ) {
            mv.addObject("representante", representantes);
            mv.addObject("checkin", checkins);
           
            
            return mv;

        } else {
            mv.addObject("representante", representantes);
            mv.addObject("checkin", checkinsRepresentante);
            mv.addObject("checkinMapa", checkinMapa);
           
            return mv;
        }
    }

    @RequestMapping(path = "/checkin/relatorio/mapa/{idRepresentante}/{idCheckin}", method = RequestMethod.GET)
    public ModelAndView abrirMapa(
            @PathVariable("idRepresentante") Long idRepresentante,
            @PathVariable("idCheckin") Long idCheckin,
            RedirectAttributes atributes) {

        Checkin checkinMapa = cr.pegarCheckinPorId(idCheckin);

        ModelAndView mv = new ModelAndView("redirect:/checkin/relatorio/" + idRepresentante+"/"+idCheckin);

        atributes.addFlashAttribute("mensagem", ".AAA");

        return mv;

    }

}

//    @RequestMapping(path = "/checkin/relatorio/?{lat},{lng}", method = RequestMethod.GET)
//    public ModelAndView setarMapaView() {
//        ModelAndView mv = new ModelAndView("checkin/checkinRepresentante");
//
//        Iterable<Representante> representantes = rr.listaRepresentante();
//
//        mv.addObject("representante", representantes);
//        return mv;
//    }

