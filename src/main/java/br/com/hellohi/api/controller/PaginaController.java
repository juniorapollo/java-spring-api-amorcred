/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author junior
 */
@Controller
public class PaginaController {

    @RequestMapping("/home")
    public String abrirPaginaInicial() {
        return "/index/index";
    }

}
