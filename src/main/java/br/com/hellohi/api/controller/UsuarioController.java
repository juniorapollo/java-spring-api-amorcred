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
import org.springframework.web.bind.annotation.PathVariable;
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
    Usuario usuario;

    @RequestMapping(path = "cadastro/usuario", method = RequestMethod.GET)
    public ModelAndView listarUsuarios() {
        ModelAndView mv = new ModelAndView("cadastro/listaUsuario");//HTML

        Iterable<Usuario> usuarios = ur.listaUsuarios();
        mv.addObject("usuario", usuarios);//Adicionando Lista Usuário HTML

        return mv;
    }

    
    
    @RequestMapping(path = "cadastro/usuario-editar/{idUsuario}", method = RequestMethod.GET)
    public ModelAndView editarUsuario(@PathVariable("idUsuario") Long idUsuario) {
        this.usuario = ur.pegarUsuarioId(idUsuario);
        
        ModelAndView mv = new ModelAndView("cadastro/editar/editaUsuario");//HTML
        mv.addObject("usuario", usuario);//Carrega Usuário HTML
        
        return mv;
    }

}
