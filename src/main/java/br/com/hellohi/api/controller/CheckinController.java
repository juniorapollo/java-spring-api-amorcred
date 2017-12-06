/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.controller;

import br.com.hellohi.api.models.Checkin;
import br.com.hellohi.api.rest.CheckinResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author junior
 */
@Controller
public class CheckinController {

    @Autowired
    CheckinResource cr;

   @RequestMapping(path="/checkin", method =RequestMethod.GET)
    public ModelAndView loadCheckin() {
        ModelAndView mv = new ModelAndView("checkin/checkin");
//        ArrayList <Checkin> checkins = new ArrayList<>();
//        checkins.add(cr.pegarCheckinPorId(47l));
//        checkins.add(cr.pegarCheckinPorId(1l));
       Checkin c = cr.pegarCheckinPorId(45l);
       mv.addObject("checkin" , c);
    return mv;
    }

    @RequestMapping(path = "/checkins", method = RequestMethod.GET)
    public ModelAndView loadPageListaCheckin() {
        ModelAndView mv = new ModelAndView("checkin/checkin");
        
        Iterable<Checkin> checkins = cr.listaCheckin();
        mv.addObject("checkin", checkins);
        return mv;
    }

}
