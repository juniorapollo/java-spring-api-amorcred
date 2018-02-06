/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.controller.NotificacoesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author junior
 */
@RestController
public class NotificacoesClienteResource {

    @Autowired
    NotificacoesController nc;

    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "/sistema/hellohi/api/pedidos/notificacoes", method = RequestMethod.GET)
    public int pedidosNotificacoesValidos() {
        
        return nc.qtdClientePediuOperacao();
    }
    
     

}
