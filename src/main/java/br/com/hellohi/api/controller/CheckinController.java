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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    NotificacoesController nc;

//    @RequestMapping(path = "/sistema/checkin", method = RequestMethod.GET)
//    public ModelAndView loadCheckin() {
//        ModelAndView mv = new ModelAndView("checkin/checkin");
////        ArrayList <Checkin> checkins = new ArrayList<>();
////        checkins.add(cr.pegarCheckinPorId(47l));
////        checkins.add(cr.pegarCheckinPorId(1l));
//        Checkin c = cr.pegarCheckinPorId(45l);
//        mv.addObject("checkin", c);
//        return mv;
//    }
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
    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "/sistema/checkin/lista/{idRepresentante}/{idCheckinMapa}/", method = RequestMethod.GET)
    public ModelAndView setarRepresentanteView(@PathVariable("idRepresentante") Long idRepresentante, @PathVariable("idCheckinMapa") Long idCheckinMapa, RedirectAttributes atributes) {

        ModelAndView mv = new ModelAndView("checkin/checkinRelatorio");
        nc.carregaNotificacoesView(mv);
        if (idRepresentante == 0 && idCheckinMapa == 0) {
            Iterable<Representante> representantes = rr.listaRepresentante();
            Iterable<Checkin> checkins = cr.listaCheckin();
            mv.addObject("representante", representantes);
            mv.addObject("checkin", checkins);

            return mv;
        } else {
            Iterable<Representante> representantes = rr.listaRepresentante();
            Iterable<Checkin> checkinsRepresentante = cr.checkinsPorRepresentante(idRepresentante);
            Checkin checkinMapa = cr.pegarCheckinPorId(idCheckinMapa);

            mv.addObject("representante", representantes);
            mv.addObject("checkin", checkinsRepresentante);
            mv.addObject("checkinMapa", checkinMapa);

            return mv;
        }
    }

    @PreAuthorize("hasAnyRole('OPERADOR')")
    @RequestMapping(path = "/sistema/checkin/lista/mapa/{idRepresentante}/{idCheckin}/", method = RequestMethod.GET)
    public ModelAndView abrirMapa(
            @PathVariable("idRepresentante") Long idRepresentante,
            @PathVariable("idCheckin") Long idCheckin,
            RedirectAttributes atributes) {

        Checkin checkinMapa = cr.pegarCheckinPorId(idCheckin);

        ModelAndView mv = new ModelAndView("redirect:/sistema/checkin/lista/" + idRepresentante + "/" + idCheckin + "/");
        nc.carregaNotificacoesView(mv);
        atributes.addFlashAttribute("mensagem", ".");

        return mv;
    }

    @PreAuthorize("hasAnyRole('OPERADOR')")
    @RequestMapping(path = "/sistema/checkin/lista/{idRepresentante}/{idCheckin}/relatorio", method = RequestMethod.GET)
    public ModelAndView gerarRelatorioCheckin(@PathVariable("idRepresentante") Long idRepresentante, @PathVariable("idCheckin") Long idCheckin, RedirectAttributes atributes) {

        Iterable<Checkin> checkins = cr.listaCheckin();
        Iterable<Representante> representantes = rr.listaRepresentante();

        ModelAndView mv = new ModelAndView("relatorios/relatorioCheckin");
        nc.carregaNotificacoesView(mv);
        if (idRepresentante == 0 && idCheckin == 0) {
            mv.addObject("representante", representantes);
            mv.addObject("checkin", checkins);
            return mv;
        } else {
            Iterable<Checkin> checkinsRepresentante = cr.checkinsPorRepresentante(idRepresentante);
            Checkin checkin = cr.pegarCheckinPorId(idCheckin);
            mv.addObject("representante", representantes);
            mv.addObject("checkin", checkinsRepresentante);
            mv.addObject("checkinMapa", checkin);
            return mv;
        }

    }

    @RequestMapping(path = "/sistema/relatorio/checkin", method = RequestMethod.GET)
    public String relatorioCheckin() {
        return "relatorios/relatorioCheckin";
    }

}
