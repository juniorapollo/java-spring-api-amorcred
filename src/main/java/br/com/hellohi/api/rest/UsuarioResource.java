/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Usuario;
import br.com.hellohi.api.repository.EmpresaRepository;
import br.com.hellohi.api.repository.UsuarioRepository;
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
public class UsuarioResource {

    @Autowired
    UsuarioRepository ur;
    Usuario usuario;

    @Autowired
    EmpresaResource empresaResource;

    @Autowired
    EmpresaRepository er;
    Empresa empresa;

    @RequestMapping(path = "/hellohi/api/usuario", method = RequestMethod.GET)
    public Iterable<Usuario> listaUsuarios() {
        Iterable<Usuario> listaUsuario = ur.findAll();
        return listaUsuario;
    }

    @RequestMapping(path = "hellohi/api/usuario/{idUsuario}", method = RequestMethod.GET)
    public Usuario pegarUsuarioId(@PathVariable("idUsuario") Long idUsuario) {
        usuario = ur.findByIdUsuario(idUsuario);
        return usuario;
    }

    @RequestMapping(path = "hellohi/api/usuario/empresa/{idEmpresa}", method = RequestMethod.POST)
    public Usuario salvarUsuario(@RequestBody @Valid Usuario usuario, @PathVariable("idEmpresa") Long idEmpresa) {
        empresa = er.findByIdEmpresa(idEmpresa);
        usuario.setIdUsuario(null);
        usuario.setEmpresa(empresa);
        return ur.save(usuario);
    }

    //Edita Usuario
    @RequestMapping(path = "hellohi/api/usuario/{idUsuario}", method = RequestMethod.PUT)
    public Usuario editarUsuario(@RequestBody @Valid Usuario usuario, @PathVariable("idUsuario") Long idUsuario) {
        Empresa e = empresaResource.buscarEmpresaId(usuario.getEmpresa().getIdEmpresa());
        usuario.setIdUsuario(idUsuario);
        usuario.setEmpresa(e);
        return ur.save(usuario);
    }

    //Deleta Usuario
    @RequestMapping(path = "hellohi/api/usuario/{idUsuario}", method = RequestMethod.DELETE)
    public String deletarUsuario(@PathVariable("idUsuario") Long idUsuario) {
        ur.delete(ur.findByIdUsuario(idUsuario));
        return "HTML DE USUARIO DELETADO COM SUCESSO"; //metodo vai ser void e retornar um metodo do paginaController que é uma pagina de Deletado com Sucesso
    }

    //Método Retorna todos Usuários que se relacionam com a Impresa no qual foi passado como parametro o id {idEmpresa}
    @RequestMapping(path = "hellohi/api/usuario/empresa/{idEmpresa}", method = RequestMethod.GET)
    public ArrayList<Usuario> listarUsuariosPorEmpresa(@PathVariable("idEmpresa") Long idEmpresa) {
        empresa = er.findByIdEmpresa(idEmpresa);
        ArrayList<Usuario> novaLIstaUsuario = new ArrayList<>();
        try {
            for (Usuario u : listaUsuarios()) {
                if (u.getEmpresa() == empresa) {
                    novaLIstaUsuario.add(u);
                }
            }
            return novaLIstaUsuario;
        } catch (Exception e) {
            return novaLIstaUsuario;
        }
    }

}
