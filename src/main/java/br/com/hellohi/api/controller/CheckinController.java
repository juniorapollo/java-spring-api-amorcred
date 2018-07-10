
package br.com.hellohi.api.controller;

import br.com.hellohi.api.models.Checkin;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.repository.CheckinRepository;
import br.com.hellohi.api.rest.CheckinResource;
import br.com.hellohi.api.rest.RepresentanteResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author junior
 */
@Controller
public class CheckinController {
    
    @Value("${baseUrl}")
    private String baseUrl;

    @Autowired
    CheckinResource cr;
    CheckinRepository checkinRepository;

    @Autowired
    RepresentanteResource rr;

    @Autowired
    NotificacoesController nc;

    
    /**
     *  Método para carregar os checkins de todos os representants se for passado
     * idRepresentante 0 e idCheckin 0 , caso contrário ele retorna os checkins 
     * do representante que for passado o Id;
     * @param idRepresentante
     * @param idCheckinMapa
     * @param atributes
     * @return 
     */

    @PreAuthorize("hasRole('OPERADOR')")
    @RequestMapping(path = "${baseUrl}/sistema/checkin/lista/{idRepresentante}/{idCheckinMapa}/", method = RequestMethod.GET)
    public ModelAndView setarRepresentanteView(@PathVariable("idRepresentante") Long idRepresentante, @PathVariable("idCheckinMapa") Long idCheckinMapa, RedirectAttributes atributes) {

        ModelAndView mv = new ModelAndView("checkin/checkinRelatorio");
        nc.carregaNotificacoesView(mv);
        if (idRepresentante == 0 && idCheckinMapa == 0) {
            Iterable<Representante> representantes = rr.listaRepresentante();
            //Iterable<Checkin> checkins = cr.listaCheckin();
            mv.addObject("representante", representantes);
           // mv.addObject("checkin", checkins);

            return mv;
        } else {
            Iterable<Representante> representantes = rr.listaRepresentante();
            Iterable<Checkin> checkinsRepresentante = cr.checkinsPorRepresentante(idRepresentante);
            Checkin checkinMapa = cr.pegarCheckinPorId(idCheckinMapa);

            mv.addObject("representante", representantes);
            mv.addObject("checkin", checkinsRepresentante);
            mv.addObject("checkinMapa", checkinMapa);

            return mv;
        }
    }

    /**
     *  Abrir o Mapa ao clicar no endereço que mostra no relatorio de checkin,
     * é passado como parameto o id do Representante  e o id do checkin que esse
     * Representante fez .
     *  É adicionado um AddFlashAttributes para abrir o mapa em cima da Tabela; 
     * @param idRepresentante
     * @param idCheckin
     * @param atributes
     * @return 
     */
    
    @PreAuthorize("hasAnyRole('OPERADOR')")
    @RequestMapping(path = "${baseUrl}/sistema/checkin/lista/mapa/{idRepresentante}/{idCheckin}/", method = RequestMethod.GET)
    public ModelAndView abrirMapa(
            @PathVariable("idRepresentante") Long idRepresentante,
            @PathVariable("idCheckin") Long idCheckin,
            RedirectAttributes atributes) {

        Checkin checkinMapa = cr.pegarCheckinPorId(idCheckin);

        ModelAndView mv = new ModelAndView("redirect:"+baseUrl+"/sistema/checkin/lista/" + idRepresentante + "/" + idCheckin + "/");
        nc.carregaNotificacoesView(mv);
        atributes.addFlashAttribute("mensagem", ".");

        return mv;
    }

    @PreAuthorize("hasAnyRole('OPERADOR')")
    @RequestMapping(path = "${baseUrl}/sistema/checkin/lista/{idRepresentante}/{idCheckin}/relatorio", method = RequestMethod.GET)
    public ModelAndView gerarRelatorioCheckin(@PathVariable("idRepresentante") Long idRepresentante, @PathVariable("idCheckin") Long idCheckin, RedirectAttributes atributes) {

        Iterable<Checkin> checkins = cr.listaCheckin();
        Iterable<Representante> representantes = rr.listaRepresentante();

        ModelAndView mv = new ModelAndView("relatorios/relatorioCheckin");
        nc.carregaNotificacoesView(mv);
        if (idRepresentante == 0 && idCheckin == 0) {
            mv.addObject("representante", representantes);
            mv.addObject("checkin", checkins);
            return mv;
        } else {
            Iterable<Checkin> checkinsRepresentante = cr.checkinsPorRepresentante(idRepresentante);
            Checkin checkin = cr.pegarCheckinPorId(idCheckin);
            mv.addObject("representante", representantes);
            mv.addObject("checkin", checkinsRepresentante);
            mv.addObject("checkinMapa", checkin);
            return mv;
        }

    }

    @RequestMapping(path = "${baseUrl}/sistema/relatorio/checkin", method = RequestMethod.GET)
    public String relatorioCheckin() {
        return "relatorios/relatorioCheckin";
    }

   
    
}
