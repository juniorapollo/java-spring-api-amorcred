
package br.com.hellohi.api.rest;

import br.com.hellohi.api.controller.NotificacoesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
