/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.controller;

import br.com.hellohi.api.models.Usuario;
import br.com.hellohi.api.rest.UsuarioResource;
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
public class UsuarioController {
    
    
     @Autowired
    UsuarioResource ur;
    

    @RequestMapping(path="/usuario", method =RequestMethod.GET)
    public ModelAndView carregaUsuario() {
        ModelAndView mv = new ModelAndView("usuario/listaUsuario");
        Iterable<Usuario> usuarios = ur.listaUsuarios();
        mv.addObject("usuario" , usuarios);
    return mv;
    }
    
    
    
    
    
}
