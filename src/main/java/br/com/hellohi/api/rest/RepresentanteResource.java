/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.models.Usuario;
import br.com.hellohi.api.repository.EmpresaRepository;
import br.com.hellohi.api.repository.RepresentanteRepository;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
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
public class RepresentanteResource {

    @Autowired
    RepresentanteRepository rr;
    Representante representante;

    @Autowired
    EmpresaRepository er;
    Empresa empresa;

    @RequestMapping(path = "/hellohi/api/representante", method = RequestMethod.GET)
    public Iterable<Representante> listaRepresentante() {
        Iterable<Representante> listaRepresentante = rr.findAll();
        return listaRepresentante;

    }

    @RequestMapping(path = "/hellohi/api/representante/{idRepresentante}", method = RequestMethod.GET)
    public Representante representantePorId(@PathVariable("idRepresentante") Long idRepresentante) {
        return rr.findByIdRepresentante(idRepresentante);
    }

    @RequestMapping(path = "/hellohi/api/representante/{idEmpresa}", method = RequestMethod.POST)
    public Representante salvarRepresentante(@RequestBody @Valid Representante representante, @PathVariable Long idEmpresa) {
        empresa = er.findByIdEmpresa(idEmpresa);
        representante.setEmpresa(empresa);
        representante.setIdRepresentante(null);
        representante = rr.save(representante);
        return representante;
    }

    @RequestMapping(path = "/hellohi/api/empresa/representante/{idRepresentante}", method = RequestMethod.PUT)
    public Representante editarRepresentante(@RequestBody @Valid Representante representante, @PathVariable Long idRepresentante) {
        empresa = er.findByIdEmpresa(representantePorId(idRepresentante).getEmpresa().getIdEmpresa());
        representante.setIdRepresentante(idRepresentante);
        representante.setEmpresa(empresa);
        representante = rr.save(representante);
        return representante;
    }

    @RequestMapping(path = "hellohi/api/representante/{idRepresentante}", method = RequestMethod.DELETE)
    public String deletarRepresentante(@PathVariable("idRepresentante") Long idRepresentante) {
        rr.delete(representantePorId(idRepresentante));
        return "HTML DE Representante DELETADO COM SUCESSO"; //metodo vai ser void e retornar um metodo do paginaController que é uma pagina de Deletado com Sucesso
    }

    //Método Retorna todos Representantes que se relacionam com a Impresa no qual foi passado como parametro o id {idEmpresa}
    @RequestMapping(path = "hellohi/api/representante/empresa/{idEmpresa}", method = RequestMethod.GET)
    public ArrayList<Representante> listarRepresentantePorEmpresa(@PathVariable("idEmpresa") Long idEmpresa) {
        empresa = er.findByIdEmpresa(idEmpresa);
        ArrayList<Representante> novaListaRepresentante = new ArrayList<>();
        try {
            for (Representante r : listaRepresentante()) {
                if (r.getEmpresa() == empresa) {
                    novaListaRepresentante.add(r);
                }
            }
            return novaListaRepresentante;
        } catch (Exception e) {
            return novaListaRepresentante;
        }
    }

}
