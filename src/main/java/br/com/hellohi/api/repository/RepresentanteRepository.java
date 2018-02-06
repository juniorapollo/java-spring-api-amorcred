/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.repository;

import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Representante;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author junior
 */
public interface RepresentanteRepository extends CrudRepository<Representante, Long> {

    public Representante findByIdRepresentante(Long idRepresentante);

    public Representante findByLogin(String login);

    public Iterable<Representante> findByEmpresa(Empresa empresa);

   

}
