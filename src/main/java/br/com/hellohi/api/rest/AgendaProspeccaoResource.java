    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.models.AgendaProspeccao;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.models.Usuario;
import br.com.hellohi.api.repository.AgendaProspeccaoRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe Nao usada
 * @author junior
 */
@RestController
public class AgendaProspeccaoResource {

    @Autowired
    AgendaProspeccaoRepository apr;
    AgendaProspeccao ap;

    @Autowired
    RepresentanteResource rr;
    Representante representante;

    @Autowired
    UsuarioResource ur;
    Usuario usuario;

    @RequestMapping(path = "${baseUrl}/hellohi/api/agenda/prospeccao", method = RequestMethod.GET)
    public Iterable<AgendaProspeccao> listaAgendaProsp() {
        Iterable<AgendaProspeccao> agenda = apr.findAll();  
        return agenda;
    }

    //Pegr AGenda por ID
    @RequestMapping(path = "${baseUrl}/hellohi/api/agenda/prospeccao/{idAgendaProspeccao}", method = RequestMethod.GET)
    public AgendaProspeccao pegarAgendaProspeccaoPorId(@PathVariable("idAgendaProspeccao") Long idAgendaProspeccao) {
        return apr.findByIdAgendaProspeccao(idAgendaProspeccao);
    }

    //Agenda de Prospeccao Por Representante
    @RequestMapping(path = "${baseUrl}/hellohi/api/agenda/prospeccao/representante/{idRepresentante}")
    public ArrayList<AgendaProspeccao> pegarAgendaProspeccaoPorRepresentante(@PathVariable("idRepresentante") Long idRepresentante) {
        representante = rr.representantePorId(idRepresentante);
        System.out.println(representante.getNome());
        ArrayList<AgendaProspeccao> novaListaAgenda = new ArrayList<>();
        try {
            for (AgendaProspeccao a : listaAgendaProsp()) {
                if (a.getRepresentante() == representante) {
                    novaListaAgenda.add(a);
                }
            }
            //Veirifca se a lista está vazia
            if (novaListaAgenda.isEmpty()) {
                System.out.println("Representante NAO TEM AGENDA (ABRIR PAGINA AVISANDO)");
            }
            return novaListaAgenda;

        } catch (Exception e) {
            System.out.println("CATCH " + e);
            return novaListaAgenda; // Retorna Lista Vazia
        }
    }

    //Deletar Determinada Agenda por ID
    @RequestMapping(path = "${baseUrl}/hellohi/api/agenda/prospeccao/{idAgendaProspeccao}", method = RequestMethod.DELETE)
    public Iterable<AgendaProspeccao> deletarAgendaProsp(@PathVariable("idAgendaProspeccao") Long idAgendaProspeccao) {
        apr.delete(pegarAgendaProspeccaoPorId(idAgendaProspeccao));
        return listaAgendaProsp();
    }

}
