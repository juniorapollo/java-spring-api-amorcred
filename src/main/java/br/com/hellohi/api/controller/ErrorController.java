/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.controller;


import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 *
 * @author junior
 */
@ControllerAdvice
public class ErrorController {

   
    @Bean
    public EmbeddedServletContainerCustomizer error404() {
          
        return (ConfigurableEmbeddedServletContainer container) -> {
            container.addErrorPages(new ErrorPage(HttpStatus.valueOf(404),"/sistema/error/"+404));
        };
    }
    
    @Bean
    public EmbeddedServletContainerCustomizer error403() {
          
        return (ConfigurableEmbeddedServletContainer container) -> {
            container.addErrorPages(new ErrorPage(HttpStatus.valueOf(403),"/sistema/error/"+403));
        };
    }
    
    @Bean
    public EmbeddedServletContainerCustomizer errorForbidden() {
          
        return (ConfigurableEmbeddedServletContainer container) -> {
            container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN,"/sistema/error/"+403));
        };
    }
    
    @Bean
    public EmbeddedServletContainerCustomizer error500() {
          
        return (ConfigurableEmbeddedServletContainer container) -> {
            container.addErrorPages(new ErrorPage(HttpStatus.valueOf(500),"/error/"+500));
        };
    }

}
