/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.controller.UsuarioController;
import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Usuario;
import br.com.hellohi.api.repository.EmpresaRepository;
import br.com.hellohi.api.repository.UsuarioRepository;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
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

    @Autowired
    UsuarioRepository ur;
    Usuario usuario;
    UsuarioController uc;

    @Autowired
    EmpresaResource empresaResource;
    EmpresaRepository er;
    Empresa empresa;
    
    
    //Retorna View para cadastro 
    @RequestMapping(path = "cadastro/usuario-adicionar", method = RequestMethod.GET)
    public ModelAndView adicionarUsuario(Usuario usuario) {
        ModelAndView mv = new ModelAndView("cadastro/adicionar/adicionarUsuario");//HTML
        mv.addObject("usuario", usuario);
        return mv;
    }

    //Retorna lista  Usuários em JSON
    @RequestMapping(path = "/hellohi/api/usuario", method = RequestMethod.GET)
    public Iterable<Usuario> listaUsuarios() {
        Iterable<Usuario> listaUsuario = ur.findAll();
        return listaUsuario;
    }

    @RequestMapping(path = "/hellohi/api/usuario/{idUsuario}", method = RequestMethod.GET)
    public Usuario pegarUsuarioId(@PathVariable("idUsuario") Long idUsuario) {
        usuario = ur.findByIdUsuario(idUsuario);
        return usuario;
    }

    @RequestMapping(path = "/hellohi/api/usuario", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView salvarUsuario(@Valid Usuario usuario, BindingResult result , RedirectAttributes atributes) {
//        if(usuarioSession.getNivelUsuario().equals("1")){         
//        empresa = er.findByIdEmpresa(usuarioSession.getEmpresa().getIdEmpresa());
//        usuario.setIdUsuario(null);
//        usuario.setEmpresa(usuarioSession.getEmpresa());
//        return this.uc.listaUsuario();
//        }
//        return this.uc.listaUsuario();   NAO APAGAR
        System.out.println("Entrou no Metodo Salvar Usuario");

        if (result.hasErrors()) {
           return adicionarUsuario(usuario);
        }

        usuario.setIdUsuario(null);
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        ur.save(usuario);

        ModelAndView mv = new ModelAndView("redirect:/cadastro/usuario");
        atributes.addFlashAttribute("mensagem", "Usuário - " + usuario.getIdUsuario()+ " " + usuario.getNome() + " salvo com sucesso.");

        return mv;
    }

    //Edita Usuario
    @RequestMapping(path = "/hellohi/api/usuario", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView editarUsuario(@Valid Usuario usuario, Errors erros, RedirectAttributes atributes) {
        if (erros.hasErrors()) {
            return adicionarUsuario(usuario);
        }
        
        ur.save(usuario);
        ModelAndView mv = new ModelAndView("redirect:/cadastro/usuario");
        atributes.addFlashAttribute("mensagem", "Usuário -" + usuario.getIdUsuario() + " editado com sucesso.");
        return mv;
    }

    //Deleta Usuario
    @RequestMapping(path = "hellohi/api/usuario/{idUsuario}", method = RequestMethod.DELETE)
    public ModelAndView deletarUsuario(@PathVariable("idUsuario") Long idUsuario, RedirectAttributes atributes) {

        usuario = pegarUsuarioId(idUsuario);
        usuario.setAtivo(false);//desativa o usuario
        ur.save(usuario);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/cadastro/usuario");
        atributes.addFlashAttribute("mensagem", "Usuário - " + usuario.getIdUsuario() + " - " + usuario.getNome() + " removido com sucesso.");

        return mv;
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
