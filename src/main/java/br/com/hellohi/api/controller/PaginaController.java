/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.controller;

import br.com.hellohi.api.models.Checkin;
import br.com.hellohi.api.models.Cliente;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.repository.CheckinRepository;
import br.com.hellohi.api.rest.ClienteResource;
import br.com.hellohi.api.rest.RepresentanteResource;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author junior
 */
@Controller
public class PaginaController {

    @Autowired
    CheckinRepository checkinRepository;
    Checkin checkin;

    @Autowired
    RepresentanteResource rr;
    Representante representante;

    @Autowired
    ClienteResource cr;
    Cliente cliente;

    @Autowired
    NotificacoesController nc;

    
    @RequestMapping(path = "/entrar", method = RequestMethod.GET)
    public String login() {

        return "login/loginPage";
    }

    @PreAuthorize("hasAnyRole('OPERADOR')")
    @RequestMapping(path = "/sistema", method = RequestMethod.GET)
    public ModelAndView home() {
        
        int countRepresentante = 0;
        int countCliente = 0;        

        ModelAndView mv = new ModelAndView("index/home");
        nc.carregaNotificacoesView(mv);
        
        ArrayList<Checkin> checkins = (ArrayList<Checkin>) checkinRepository.findAll();
        ArrayList<Representante> representantes = (ArrayList<Representante>) rr.listaRepresentante();
        ArrayList<Cliente> clientes = (ArrayList<Cliente>) cr.listaCliente();         

        countRepresentante = representantes.stream().filter((representante1)
                -> (representante1.isAtivo())).map((_item) -> 1).reduce(countRepresentante, Integer::sum);

        countCliente = clientes.stream().filter((cliente1)
                -> (cliente1.isAtivo())).map((_item) -> 1).reduce(countCliente, Integer::sum);

        
//        for (Cliente clienteNotificacao1 : clienteNotificacao) {
//            if (clienteNotificacao1.isMsgNotificacao()) {
//                countClienteNotificacao++;
//            }
//        }
        mv.addObject("totalCheckins", checkins.size());
        mv.addObject("totalRepresentantes", countRepresentante);
        mv.addObject("totalClientes", countCliente);
       
        return mv;
    }

    
    @RequestMapping("/sistema/model/menu")
    public String abrirMenu() {
        return "/menu/menu";
    }

    @RequestMapping("/sistema/model/navbar")
    public String abrirNavbar() {
        return "/navbar/navbar";
    }

    @RequestMapping("/sistema/model/modelo")
    public String abrirModelo() {
        return "index/1Index";
    }

    @RequestMapping("/sistema/mapa/mapa")
    public String abrirMapa() {
        return "/fragments/mapa";
    }

    @RequestMapping("/sistema/error/{codError}")
    public ModelAndView error(@PathVariable("codError") Integer codError) {
        final ModelAndView mv = new ModelAndView("error/error");
        String errorMessage = HttpStatus.valueOf(codError).toString();
        mv.addObject("errorMessage", errorMessage);
        return mv;
    }

}
