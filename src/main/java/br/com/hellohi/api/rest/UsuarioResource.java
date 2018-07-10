/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.controller.NotificacoesController;
import br.com.hellohi.api.controller.UsuarioController;
import br.com.hellohi.api.models.enums.Perfil;
import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Usuario;
import br.com.hellohi.api.repository.EmpresaRepository;
import br.com.hellohi.api.repository.UsuarioRepository;
import br.com.hellohi.api.security.UserSS;
import br.com.hellohi.api.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author junior
 */
@RestController
public class UsuarioResource {

    @Value("${baseUrl}")
    private String baseUrl;

    @Autowired
    BCryptPasswordEncoder senha;

    @Autowired
    UsuarioRepository ur;
    Usuario usuario;
    UsuarioController uc;

    @Autowired
    EmpresaResource empresaResource;
    EmpresaRepository er;
    Empresa empresa;

    @Autowired
    NotificacoesController nc;

    //Retorna View para cadastro 
    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    @RequestMapping(path = "${baseUrl}/sistema/cadastro/usuario-adicionar", method = RequestMethod.GET)
    public ModelAndView adicionarUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView("cadastro/adicionar/adicionarUsuario");//HTML
        mv.addObject("usuario", usuario);
        nc.carregaNotificacoesView(mv);
        return mv;
    }

    //Retorna lista  Usuários em JSON
    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/usuario", method = RequestMethod.GET)
    public Iterable<Usuario> listaUsuarios() {
        UserSS user = UserService.authenticated();
        Iterable<Usuario> listaUsuario = ur.findByEmpresa(user.getEmpresa());
        return listaUsuario;
    }
    
    

    

    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/usuario/{idUsuario}", method = RequestMethod.GET)
    public Usuario pegarUsuarioId(@PathVariable("idUsuario") Long idUsuario) {
        try {
            usuario = ur.findByIdUsuario(idUsuario);
            for (Usuario u : listaUsuarios()) {
                if (u == usuario) {
                    return usuario;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        throw new AuthorizationServiceException("Você não tem permissão!");
    }

    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/usuario", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView salvarUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes atributes) {
        try {

            if (result.hasErrors()) {
                return adicionarUsuario(usuario);
            }

            switch (usuario.getNivelUsuario()) {
                case "OPERADOR":
                    usuario.addPerfil(Perfil.OPERADOR);
                    break;
                case "SUPERVISOR":
                    usuario.addPerfil(Perfil.OPERADOR);
                    usuario.addPerfil(Perfil.SUPERVISOR);
                    break;
                case "ADMINISTRADOR":
                    usuario.addPerfil(Perfil.OPERADOR);
                    usuario.addPerfil(Perfil.SUPERVISOR);
                    usuario.addPerfil(Perfil.ADMINISTRADOR);
                    break;
                default:
                    break;
            }

            UserSS user = UserService.authenticated(); //Carrega Usuário Logado no Sistema
            usuario.setIdUsuario(null); // Setando ID Nulo para criar um novo usuário (Garantir)
            usuario.setEmpresa(empresaResource.buscarEmpresaId(user.getEmpresa().getIdEmpresa())); //Setando empresa do Usuario Logado
            usuario.setLogin(usuario.getEmail());//Setando o Login que é o mesmo email 
            usuario.setSenha(senha.encode(usuario.getSenha()));// Criptografando a senha para salvar no banco   
            usuario.setAtivo(true); // Usuário Criado ja Ativo para Usar o Sistema 
            ur.save(usuario);//Salva Usuário criado 

            ModelAndView mv = new ModelAndView("redirect:" + baseUrl + "/sistema/cadastro/usuario");
            atributes.addFlashAttribute("mensagem", "Usuário - " + usuario.getIdUsuario() + " " + usuario.getNome() + " salvo com sucesso.");
            return mv;
        } catch (Exception e) {
            System.out.println("Catch POST:" + e);
            result.addError(new ObjectError("Email Duplicado", "Email já cadastrado."));
            return adicionarUsuario(usuario);
        }

    }

    //Edita Usuario
    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/usuario", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView editarUsuario(@Valid Usuario usuario, Errors erros, BindingResult result, RedirectAttributes atributes) {
        System.out.println("Editou Usuário: " + usuario.getIdUsuario());
        try {
            if (erros.hasErrors()) {
                return adicionarUsuario(usuario);
            }

            switch (usuario.getNivelUsuario()) {
                case "OPERADOR":
                    usuario.addPerfil(Perfil.OPERADOR);
                    break;
                case "SUPERVISOR":
                    usuario.addPerfil(Perfil.OPERADOR);
                    usuario.addPerfil(Perfil.SUPERVISOR);
                    break;
                case "ADMINISTRADOR":
                    usuario.addPerfil(Perfil.OPERADOR);
                    usuario.addPerfil(Perfil.SUPERVISOR);
                    usuario.addPerfil(Perfil.ADMINISTRADOR);
                    break;
                default:
                    break;
            }

            if (usuario.getSenha().length() > 6) {
                usuario.setLogin(usuario.getEmail());
                ur.save(usuario);
            } else {
                usuario.setLogin(usuario.getEmail());
                usuario.setSenha(senha.encode(usuario.getSenha()));
            }

            UserSS user = UserService.authenticated();
            usuario.setEmpresa(empresaResource.buscarEmpresaId(user.getEmpresa().getIdEmpresa()));
            ur.save(usuario);

            ModelAndView mv = new ModelAndView("redirect:" + baseUrl + "/sistema/cadastro/usuario");

            atributes.addFlashAttribute("mensagem", "Usuário -" + usuario.getIdUsuario() + " editado com sucesso.");
            return mv;
        } catch (Exception e) {
            System.out.println("Catch PUT:" + e);
            result.addError(new ObjectError("Email Duplicado", "Email já cadastrado."));
            return adicionarUsuario(usuario);
        }
    }

    //Deleta Usuario
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/usuario/{idUsuario}", method = RequestMethod.DELETE)
    public ModelAndView deletarUsuario(@PathVariable("idUsuario") Long idUsuario, Usuario usuario, RedirectAttributes atributes) {

        usuario = pegarUsuarioId(idUsuario);
        usuario.setAtivo(false);//desativa o usuario
        ur.save(usuario);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:" + baseUrl + "/sistema/cadastro/usuario");
        atributes.addFlashAttribute("mensagem", "Usuário - " + usuario.getIdUsuario() + " - " + usuario.getNome() + " removido com sucesso.");

        return mv;
    }

}
