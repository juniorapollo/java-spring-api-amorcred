/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.controller;

import br.com.hellohi.api.models.Cliente;
import br.com.hellohi.api.rest.ClienteResource;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ClienteController {

    @Autowired
    ClienteResource cr;
    Cliente cliente;

    

    
    //Requisição para Editar Cliente
    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    @RequestMapping(path = "/sistema/cadastro/cliente-editar/{idCliente}", method = RequestMethod.GET)
    public ModelAndView editarCliente(@PathVariable("idCliente") Long idCliente) {
        this.cliente = cr.clientePorId(idCliente);
        return cr.adicionarCliente(cliente);
    }

}
