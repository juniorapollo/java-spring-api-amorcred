
package br.com.hellohi.api.controller;

import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.rest.RepresentanteResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author junior
 */
@Component
@Controller
public class RepresentanteController {

    @Autowired
    RepresentanteResource rr;
    Representante representante;

    @Autowired
    NotificacoesController nc;

    @PreAuthorize("hasAnyRole('OPERADOR')")
    @RequestMapping(path = "${baseUrl}/sistema/cadastro/representante", method = RequestMethod.GET)
    public ModelAndView listarRepresentantes() {
        ModelAndView mv = new ModelAndView("cadastro/listaRepresentante");

        Iterable<Representante> representantes = rr.listaRepresentante();
        mv.addObject("representante", representantes);
        nc.carregaNotificacoesView(mv);
        return mv;
    }

    //Requisição para Editar Representante
    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    @RequestMapping(path = "${baseUrl}/sistema/cadastro/representante-editar/{idRepresentante}", method = RequestMethod.GET)
    public ModelAndView editaRepresentante(@PathVariable("idRepresentante") Long idRepresentante) {
        this.representante = rr.representantePorId(idRepresentante);
        ModelAndView mv = new ModelAndView("cadastro/adicionar/adicionarRepresentante");
        mv.addObject("representante", representante);
        nc.carregaNotificacoesView(mv);
        return mv;
    }
}
