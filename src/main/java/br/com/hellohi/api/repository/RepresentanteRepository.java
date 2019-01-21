/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.repository;

import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Representante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author junior
 */
public interface RepresentanteRepository extends JpaRepository<Representante, Long> {

    public Representante findByIdRepresentante(Long idRepresentante);

    public Representante findByLogin(String login);

    public Iterable<Representante> findByEmpresa(Empresa empresa);
    
    public Iterable<Representante> findByAtivo(boolean ativo);
    
    @Query("SELECT COUNT(u) FROM Representante")
    Long countRepresentante();

    @Query("SELECT nome FROM  Representante  WHERE ativo=true")
     Iterable<Representante> ativos();

}
