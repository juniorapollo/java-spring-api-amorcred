
package br.com.hellohi.api.controller;

import br.com.hellohi.api.models.Cliente;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.rest.ClienteResource;
import br.com.hellohi.api.rest.RepresentanteResource;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author junior
 */
@Controller
public class NotificacoesController {

    @Autowired
    ClienteResource cr;

    @Autowired
    RepresentanteResource rr;

    /**
     * Retornar o Objetos Clientes que efetuou chamada de notificação
     * @return 
     */
    public ArrayList pegarClientesQuePediuOperacao() {
        ArrayList<Cliente> todosClientes = (ArrayList<Cliente>) cr.listaCliente();
        ArrayList<Cliente> clienteNotificacao = new ArrayList<>();

        todosClientes.stream().filter((cliente) -> (cliente.isMsgNotificacao())).forEach((cliente) -> {
            clienteNotificacao.add(cliente);
        });
        return clienteNotificacao;
    }

    /**
     * Retorna "Inteiro" quantidade de Cliente que efetuou chamada de notificação
    * @return 
     */
    public int qtdClientePediuOperacao() {
        int countClienteNotificacao = pegarClientesQuePediuOperacao().size();
        return countClienteNotificacao;
    }

    //Método de Carregar Objeto para Mostrar Notificações nas views
    public void carregaNotificacoesView(ModelAndView mv) {
        int countClienteNotificacao = qtdClientePediuOperacao();
        ArrayList<Cliente> clienteNotificacao = pegarClientesQuePediuOperacao();
        mv.addObject("totalNotificacao", countClienteNotificacao);
        mv.addObject("clienteNotificacao", clienteNotificacao);

    }

    //Enviar Notificações para Cliente e  ou Representantes
    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "${baseUrl}/sistema/notificacao", method = RequestMethod.GET)
    public ModelAndView notificacao() {
        //Cria a View Html
        ModelAndView mv = new ModelAndView("notificacoes/notificacao");
        Iterable<Cliente> clientes = cr.listaCliente(); // Recupera lista de Clientes para Enviar as Notificacoes
        Iterable<Representante> representantes = rr.listaRepresentante();  // Recupera Lista de Representantes 

        mv.addObject("cliente", clientes);//Carrega lista clientes
        mv.addObject("representante", representantes);//Carrega lista representantes
        carregaNotificacoesView(mv);
        return mv;
    }

    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "${baseUrl}/sistema/hellohi/api/notificacao/view", method = RequestMethod.GET)
    public ModelAndView carregarNotificacoesNavbar() {
        ModelAndView mv = new ModelAndView("navbar/clientesNotificacoes");
        carregaNotificacoesView(mv);
        return mv;
    }

}
