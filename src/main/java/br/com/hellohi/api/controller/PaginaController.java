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
import br.com.hellohi.api.repository.ClienteRepository;
import br.com.hellohi.api.repository.RepresentanteRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
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
    RepresentanteRepository representanteRepository;
    Representante representante;

    @Autowired
    ClienteRepository clienteRepository;
    Cliente cliente;

    @RequestMapping(path = "/entrar", method = RequestMethod.GET)
    public String login() {
        return "login/loginPage";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        int countRepresentante = 0;
        int countCliente = 0;
        int countClienteNotificacao = 0;

        ModelAndView mv = new ModelAndView("index/home");

        ArrayList<Checkin> checkins = (ArrayList<Checkin>) checkinRepository.findAll();;
        ArrayList<Representante> representantes = (ArrayList<Representante>) representanteRepository.findAll();;
        ArrayList<Cliente> clientes = (ArrayList<Cliente>) clienteRepository.findAll();;

        ArrayList<Cliente> clienteNotificacao = verificarNotificacao(true);

        for (Representante representante1 : representantes) {
            if (representante1.isAtivo()) {
                countRepresentante++;
            }
        }

        for (Cliente cliente1 : clientes) {
            if (cliente1.isAtivo()) {
                countCliente++;
            }
        }

        for (Cliente clienteNotificacao1 : clienteNotificacao) {
            if (clienteNotificacao1.isMsgNotificacao()) {
                countClienteNotificacao++;
            }
        }

        mv.addObject("totalCheckins", checkins.size());
        mv.addObject("totalRepresentantes", countRepresentante);
        mv.addObject("totalClientes", countCliente);
        mv.addObject("totalNotificacao", countClienteNotificacao);
        mv.addObject("clienteNotificacao", clienteNotificacao);

        return mv;
    }

    @RequestMapping("/model/menu")
    public String abrirMenu() {
        return "/menu/menu";
    }

    @RequestMapping("/model/navbar")
    public String abrirNavbar() {
        return "/navbar/navbar";
    }

    @RequestMapping("/model/modelo")
    public String abrirModelo() {
        return "/model/modelo";
    }

    @RequestMapping("/mapa/mapa")
    public String abrirMapa() {
        return "/fragments/mapa";
    }

//    @RequestMapping("/model/tabelaEditavel")
//    public String abrirDataTable() {
//        return "/model/modelTabelaEditavel";
//    }
//    
    @RequestMapping(path = "requisicao/notificacao", method = RequestMethod.GET)
    public ArrayList<Cliente> verificarNotificacao(boolean msg) {
        ArrayList<Cliente> clientes = clienteRepository.msgNotificacao(msg);

        return clientes;

    }

    @RequestMapping(path = "apagar/notificacao/{idCliente}", method = RequestMethod.GET)
    public ModelAndView apagarNotificacao(@PathVariable("idCliente") Long idCliente) {
        cliente = clienteRepository.findByIdCliente(idCliente);
        cliente.setMsgNotificacao(false);
        clienteRepository.save(cliente);
        ModelAndView mv = new ModelAndView("redirect:/");
        return mv;
    }

}
