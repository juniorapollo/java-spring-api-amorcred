/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.service;

import br.com.hellohi.api.models.Cliente;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.models.Usuario;
import br.com.hellohi.api.models.enums.Perfil;
import br.com.hellohi.api.repository.ClienteRepository;
import br.com.hellohi.api.repository.EmpresaRepository;
import br.com.hellohi.api.repository.RepresentanteRepository;
import br.com.hellohi.api.repository.UsuarioRepository;
import br.com.hellohi.api.security.UserClienteSS;
import br.com.hellohi.api.security.UserRepresentanteSS;
import br.com.hellohi.api.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author junior
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    ClienteRepository cr;

    @Autowired
    UsuarioRepository ur;

    @Autowired
    RepresentanteRepository rr;

    @Autowired
    EmpresaRepository er;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Usuario usuario = ur.findByLogin(login);
        Representante representante = rr.findByLogin(login);
        Cliente cliente = cr.findByLogin(login);

        if (!(usuario == null)) {
            System.out.println("ENTROU - USUARIO ------------------------------------------------------------");
            if (usuario.isAtivo()) {
                return new UserSS(usuario.getIdUsuario(), usuario.getNome(), usuario.getLogin(), usuario.getSenha(), usuario.getNivelUsuario(), usuario.getEmpresa(), usuario.getPerfis());
            } else {
                throw new UsernameNotFoundException("Usuário não encontrado");
            }

        } else if (!(representante == null)) {
            System.out.println("ENTROU - REPRESENTANTE ------------------------------------------------------------");
            if (representante.isAtivo()) {
                return new UserRepresentanteSS(representante.getIdRepresentante(), representante.getNome(), representante.getLogin(), representante.getSenha(), representante.getEmpresa(), representante.getPerfis());
            } else {
                throw new UsernameNotFoundException("Usuário não encontrado");
            }

        } else if (!(cliente == null)) {
            System.out.println("ENTROU - CLIENTE ------------------------------------------------------------");
            if (cliente.isAtivo()) {
                System.out.println("Cliente Ativo");
                return new UserClienteSS(cliente.getIdCliente(), cliente.getNomeFantasia(), cliente.getLogin(), cliente.getSenha(), cliente.getRepresentante().getEmpresa(), cliente.getPerfis());
            } else {
                throw new UsernameNotFoundException("Usuário não encontrado");
            }

        } else {
            System.out.println("NAO EXISTE USUARIO---------------------------------------------------------");
        }

        throw new UsernameNotFoundException("Usuário ou senha Inválidos");
    }

}
