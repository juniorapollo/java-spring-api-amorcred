/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.repository;

import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Usuario;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author junior
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    public Usuario findByIdUsuario(Long idUsuario);

    public Usuario findByLogin(String login);

    public Iterable<Usuario> findByEmpresa(Empresa empresa);
   

}
