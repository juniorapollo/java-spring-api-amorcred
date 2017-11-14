/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.models.AgendaManutencao;
import br.com.hellohi.api.models.Cliente;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.models.Usuario;
import br.com.hellohi.api.repository.AgendaManutencaoRepository;
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
public class AgendaManutencaoResource {

    @Autowired
    AgendaManutencaoRepository amr;
    AgendaManutencao agenda;

    @Autowired
    ClienteResource cr;
    Cliente cliente;

    @Autowired
    UsuarioResource ur;
    Usuario usuario;

    @Autowired
    RepresentanteResource rr;
    Representante representante;

    //Listar todas Agendas
    @RequestMapping(path = "/hellohi/api/agenda/manutencao", method = RequestMethod.GET)
    public Iterable<AgendaManutencao> listaAgenda() {
        Iterable<AgendaManutencao> a = amr.findAll();
        return a;
    }

    //Pegar Agenda de Manutenção por ID
    @RequestMapping(path = "/hellohi/api/agenda/manutencao/{idAgendaManutencao}", method = RequestMethod.GET)
    public AgendaManutencao pegarAgendaManutencaoPorId(@PathVariable("idAgendaManutencao") Long idAgendaManutencao) {
        agenda = amr.findByIdAgendaManutencao(idAgendaManutencao);
        return agenda;
    }

    //Agenda de Manutencao Por Cliente
    @RequestMapping(path = "/hellohi/api/agenda/manutencao/cliente/{idCliente}", method= RequestMethod.GET )
    public ArrayList<AgendaManutencao> pegarAgendaPorCliente(@PathVariable("idCliente") Long idCliente) {
        cliente = cr.clientePorId(idCliente);
        if (cliente == null) { // Se o Cliente nao Existe
            System.out.println("Cliente Nao Existe Retorna Nulo");
            return null;
        }
        ArrayList<AgendaManutencao> novaListaAgenda = new ArrayList<>();
        try {
            for (AgendaManutencao a : listaAgenda()) {
                if (a.getCliente() == cliente) {
                    novaListaAgenda.add(a);
                }
            }
            //Veirifca se a lista está vazia
            if (novaListaAgenda.isEmpty()) {
                System.out.println("CLIENTE NAO TEM AGENDA (ABRIR PAGINA AVISANDO)");
            }
            return novaListaAgenda;

        } catch (Exception e) {
            System.out.println("CATCH");
            return novaListaAgenda; // Retorna Lista Vazia
        }
    }

    //Agenda de Manutencao Por Representante
    @RequestMapping(path = "/hellohi/api/agenda/manutencao/representante/{idRepresentante}")
    public ArrayList<AgendaManutencao> pegarAgendaPorRepresentante(@PathVariable("idRepresentante") Long idRepresentante) {
        representante = rr.representantePorId(idRepresentante);
        System.out.println(representante.getNome());
        ArrayList<AgendaManutencao> novaListaAgenda = new ArrayList<>();
        try {
            for (AgendaManutencao a : listaAgenda()) {
                if (a.getCliente().getRepresentante() == representante) {
                    novaListaAgenda.add(a);
                }
            }
            //Veirifca se a lista está vazia
            if (novaListaAgenda.isEmpty()) {
                System.out.println("Representante NAO TEM AGENDA (ABRIR PAGINA AVISANDO)");
            }
            return novaListaAgenda;

        } catch (Exception e) {
            System.out.println("CATCH" + e);
            return novaListaAgenda; // Retorna Lista Vazia
        }
    }

    //Salvar Agenda passa como parâmetro id Cliente e id Usuario
    @RequestMapping(path = "/hellohi/api/agenda/manutencao/cliente/{idCliente}/usuario/{idUsuario}", method = RequestMethod.POST)
    public AgendaManutencao salvarAgenda(@RequestBody @Valid AgendaManutencao agenda,
            @PathVariable("idCliente") Long idCliente, @PathVariable("idUsuario") Long idUsuario) {
        try {
            cliente = cr.clientePorId(idCliente);
            Long idEmpresa = cliente.getRepresentante().getEmpresa().getIdEmpresa();
            ArrayList<Usuario> listaUsuarios = ur.listarUsuariosPorEmpresa(idEmpresa);

            listaUsuarios.stream().filter((u) -> (Objects.equals(u.getIdUsuario(), idUsuario))).forEachOrdered((u) -> {
                agenda.setUsuario(u);
            });

            agenda.setCliente(cliente);
            agenda.setDataHoraAgendamento(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
            agenda.setIdAgendaManutencao(null);
            amr.save(agenda);
        } catch (Exception e) {
            System.out.println("AGENDA MANUTENCAO RESOURCE RETORNA PAGINA DE ERRO");
        }
        return agenda;
    }

    @RequestMapping(path = "/hellohi/api/agenda/manutencao/{idAgendaManutencao}", method = RequestMethod.DELETE)
    public Iterable<AgendaManutencao> deletarAgenda(@PathVariable("idAgendaManutencao") Long idAgendaManutencao) {
        agenda = pegarAgendaManutencaoPorId(idAgendaManutencao);
        amr.delete(agenda);

        return listaAgenda();
    }

}
