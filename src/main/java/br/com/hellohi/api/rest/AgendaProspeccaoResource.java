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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Objects;
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

    @RequestMapping(path = "/hellohi/api/agenda/prospeccao", method = RequestMethod.GET)
    public Iterable<AgendaProspeccao> listaAgendaProsp() {
        Iterable<AgendaProspeccao> agenda = apr.findAll();  
        return agenda;
    }

    //Pegr AGenda por ID
    @RequestMapping(path = "/hellohi/api/agenda/prospeccao/{idAgendaProspeccao}", method = RequestMethod.GET)
    public AgendaProspeccao pegarAgendaProspeccaoPorId(@PathVariable("idAgendaProspeccao") Long idAgendaProspeccao) {
        return apr.findByIdAgendaProspeccao(idAgendaProspeccao);
    }

//Salvar Agenda passa como parâmetro id Representante e id Usuario
    @RequestMapping(path = "/hellohi/api/agenda/prospeccao/representante/{idRepresentante}/usuario/{idUsuario}", method = RequestMethod.POST)
    public AgendaProspeccao salvarAgendaProspeccao(@RequestBody @Valid AgendaProspeccao agenda,
            @PathVariable("idRepresentante") Long idRepresentante, @PathVariable("idUsuario") Long idUsuario) {

        try {
            representante = rr.representantePorId(idRepresentante);
            Long idEmpresa = representante.getEmpresa().getIdEmpresa();
            ArrayList<Usuario> listaUsuarios = ur.listarUsuariosPorEmpresa(idEmpresa);
            listaUsuarios.stream().filter((u) -> (Objects.equals(u.getIdUsuario(), idUsuario))).forEachOrdered((u) -> {
                agenda.setUsuario(u);
            });

            agenda.setRepresentante(representante);
            agenda.setDataHoraAgendamento(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
            agenda.setIdAgendaProspeccao(null);
            apr.save(agenda);

        } catch (Exception e) {
            System.out.println("AGENDA MANUTENCAO RESOURCE RETORNA PAGINA DE ERRO");
        }
        return agenda;
    }

    //Agenda de Prospeccao Por Representante
    @RequestMapping(path = "/hellohi/api/agenda/prospeccao/representante/{idRepresentante}")
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
    @RequestMapping(path = "/hellohi/api/agenda/prospeccao/{idAgendaProspeccao}", method = RequestMethod.DELETE)
    public Iterable<AgendaProspeccao> deletarAgendaProsp(@PathVariable("idAgendaProspeccao") Long idAgendaProspeccao) {
        apr.delete(pegarAgendaProspeccaoPorId(idAgendaProspeccao));
        return listaAgendaProsp();
    }

}
