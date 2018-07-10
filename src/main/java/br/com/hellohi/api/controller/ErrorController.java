
package br.com.hellohi.api.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Classe que controla os erros gerados pelas requições inválidas.
 * @author junior
 */
@ControllerAdvice
public class ErrorController {
    
    @Value("${baseUrl}")
    String baseUrl;

   
    @Bean
    public EmbeddedServletContainerCustomizer error404() {
          
        return (ConfigurableEmbeddedServletContainer container) -> {
            container.addErrorPages(new ErrorPage(HttpStatus.valueOf(404), baseUrl+"/sistema/error/"+404));
        };
    }
    
    @Bean
    public EmbeddedServletContainerCustomizer error403() {
          
        return (ConfigurableEmbeddedServletContainer container) -> {
            container.addErrorPages(new ErrorPage(HttpStatus.valueOf(403), baseUrl+"/sistema/error/"+403));
        };
    }
    
    @Bean
    public EmbeddedServletContainerCustomizer errorForbidden() {
          
        return (ConfigurableEmbeddedServletContainer container) -> {
            container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, baseUrl+"/sistema/error/"+403));
        };
    }
    
    @Bean
    public EmbeddedServletContainerCustomizer error500() {
          
        return (ConfigurableEmbeddedServletContainer container) -> {
            container.addErrorPages(new ErrorPage(HttpStatus.valueOf(500), baseUrl+"/sistema/error/"+500));
        };
    }

}
