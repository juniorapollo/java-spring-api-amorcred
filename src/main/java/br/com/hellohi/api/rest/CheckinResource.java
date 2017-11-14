/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.models.AgendaManutencao;
import br.com.hellohi.api.models.AgendaProspeccao;
import br.com.hellohi.api.models.Checkin;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.repository.CheckinRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author junior
 */
@RestController
public class CheckinResource {

    @Autowired
    CheckinRepository cr;
    Checkin checkin;

    @Autowired
    AgendaProspeccaoResource apr;
    AgendaProspeccao ap;

    @Autowired
    AgendaManutencaoResource amr;
    AgendaManutencao am;

    @Autowired
    RepresentanteResource rr;
    Representante representante;

    //Retorna a Lista de Todos os checkins realizados
    @RequestMapping(path = "hellohi/api/checkin", method = RequestMethod.GET)
    public Iterable<Checkin> listaCheckin() {
        return cr.findAll();
    }

    //Retorna a Lista de Todos os checkins por Id
    @RequestMapping(path = "hellohi/api/checkin/{idCheckin}", method = RequestMethod.GET)
    public Checkin pegarCheckinPorId(@PathVariable("idCheckin") Long idCheckin) {
        return cr.findByIdCheckin(idCheckin);
    }

    //Retorna a Lista de Todos os checkins por Agenda de Prospeccao
    @RequestMapping(path = "hellohi/api/checkin/agenda/prospeccao/{idAgendaProspeccao}", method = RequestMethod.GET)
    public ArrayList<Checkin> pegarCheckinPorAgendaProspeccao(@PathVariable("idAgendaProspeccao") Long idAgendaProspeccao) {

        ap = apr.pegarAgendaProspeccaoPorId(idAgendaProspeccao);

        ArrayList<Checkin> novaListaCheckin = new ArrayList<>();
        try {
            for (Checkin c : listaCheckin()) {
                if (c.getAgendaProspeccao() == ap) {
                    novaListaCheckin.add(c);
                }
            }
            //Veirifca se a lista está vazia
            if (novaListaCheckin.isEmpty()) {
                System.out.println("Agenda Prospeccao NAO TEM Checkin (ABRIR PAGINA AVISANDO)");
            }
            return novaListaCheckin;

        } catch (Exception e) {
            System.out.println("CATCH " + e);
            return novaListaCheckin; // Retorna Lista Vazia
        }

    }

    //Retorna a Lista de Todos os checkins por Agenda de Manutencao
    @RequestMapping(path = "hellohi/api/checkin/agenda/manutencao/{idAgendaManutencao}", method = RequestMethod.GET)
    public ArrayList<Checkin> pegarCheckinPorAgendaManutencao(@PathVariable("idAgendaManutencao") Long idAgendaManutencao) {

        am = amr.pegarAgendaManutencaoPorId(idAgendaManutencao);

        ArrayList<Checkin> novaListaCheckin = new ArrayList<>();
        try {
            for (Checkin c : listaCheckin()) {
                if (c.getAgendaManutencao() == am) {
                    novaListaCheckin.add(c);
                }
            }
            //Veirifca se a lista está vazia
            if (novaListaCheckin.isEmpty()) {
                System.out.println("Agenda Manutencao NAO TEM Checkin (ABRIR PAGINA AVISANDO)");
            }
            return novaListaCheckin;

        } catch (Exception e) {
            System.out.println("CATCH " + e);
            return novaListaCheckin; // Retorna Lista Vazia
        }
    }

    //Salvar Checkin referente a agenda da Manutencao
    @RequestMapping(path = "/hellohi/api/checkin/agenda/manutencao/{idAgendaManutencao}", method = RequestMethod.POST)
    public Checkin salvarCheckinManutencao(@RequestBody @Valid Checkin checkin, @PathVariable("idAgendaManutencao") Long idAgendaManutencao) {

        checkin.setAgendaManutencao(amr.pegarAgendaManutencaoPorId(idAgendaManutencao));
        checkin.setIdCheckin(null);

        return cr.save(checkin);

    }

    //Salvar Checkin referente a agenda da Prospeccao
    @RequestMapping(path = "/hellohi/api/checkin/agenda/prospeccao/{idAgendaProspeccao}", method = RequestMethod.POST)
    public Checkin salvarCheckinProspeccao(@RequestBody @Valid Checkin checkin, @PathVariable("idAgendaProspeccao") Long idAgendaProspeccao) {

        checkin.setAgendaProspeccao(apr.pegarAgendaProspeccaoPorId(idAgendaProspeccao));
        checkin.setDataHoraCheckin(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        checkin.setIdCheckin(null);

        return cr.save(checkin);
    }

}
