/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.repository;

import br.com.hellohi.api.models.Cliente;
import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Representante;
import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author junior
 */
public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    public Cliente findByIdCliente(Long idCliente);

    public ArrayList<Cliente> findByMsgNotificacao(boolean msg);

    public Cliente findByLogin(String login);

    public Iterable<Cliente> findByEmpresa(Empresa empresa);

    public Iterable<Cliente> findByRepresentante(Representante representante);

}
