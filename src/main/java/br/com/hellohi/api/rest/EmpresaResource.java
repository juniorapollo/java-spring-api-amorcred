/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.repository.EmpresaRepository;
import br.com.hellohi.api.repository.UsuarioRepository;
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
public class EmpresaResource {

    @Autowired
    EmpresaRepository er;
    Empresa empresa;

    @Autowired
    UsuarioRepository ur;

    @RequestMapping(path = "/hellohi/api/empresa", method = RequestMethod.GET)
    public Iterable<Empresa> listaEmpresa() {
        Iterable<Empresa> listaEmpresa = er.findAll();
        return listaEmpresa;
    }

    @RequestMapping(path = "/hellohi/api/empresa/{id_empresa}", method = RequestMethod.GET)
    public Empresa buscarEmpresaId(@PathVariable("id_empresa") Long idEmpresa) {
        empresa = er.findByIdEmpresa(idEmpresa);
        return empresa;
    }

    @RequestMapping(path = "/hellohi/api/empresa", method = RequestMethod.POST)
    public Empresa salvarEmpresa(@RequestBody @Valid Empresa empresa) {
        empresa.setIdEmpresa(null);
        return er.save(empresa);
    }

    @RequestMapping(path = "/hellohi/api/empresa/{id_empresa}", method = RequestMethod.DELETE)
    public Iterable<Empresa> deletarEmpresaId(@PathVariable("id_empresa") Long id) {
        empresa = buscarEmpresaId(id);
        er.delete(empresa);
        return listaEmpresa();
    }

    @RequestMapping(path = "/hellohi/api/empresa/", method = RequestMethod.PUT)
    public Empresa editaEmpresa(@RequestBody @Valid Empresa empresa) {
        return er.save(empresa);
    }

}
