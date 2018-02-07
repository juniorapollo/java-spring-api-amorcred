/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.rest;

import br.com.hellohi.api.controller.NotificacoesController;
import br.com.hellohi.api.controller.RepresentanteController;
import br.com.hellohi.api.models.Checkin;
import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Representante;
import br.com.hellohi.api.models.enums.Perfil;
import br.com.hellohi.api.repository.RepresentanteRepository;
import br.com.hellohi.api.security.UserSS;
import br.com.hellohi.api.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author junior
 */
@RestController
@Controller
public class RepresentanteResource {

    @Autowired
    BCryptPasswordEncoder senha;

    @Autowired
    RepresentanteRepository rr;
    Representante representante;
    RepresentanteController rc;

    @Autowired
    EmpresaResource empresaResource;
    Empresa empresa;

    @Autowired
    NotificacoesController nc;

    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    @RequestMapping(path = "/sistema/cadastro/representante-adicionar", method = RequestMethod.GET)
    public ModelAndView adicionarRepresentante(Representante representante) {
        ModelAndView mv = new ModelAndView("cadastro/adicionar/adicionarRepresentante");
        mv.addObject("representante", representante);

        nc.carregaNotificacoesView(mv);
        return mv;
    }

    @PreAuthorize("hasAnyRole('OPERADOR')")
    @RequestMapping(path = "/hellohi/api/representante", method = RequestMethod.GET)
    public Iterable<Representante> listaRepresentante() {
               UserSS user = UserService.authenticated();
       
        if (user == null || !user.hasRole(Perfil.SYSTEM_ADMIN_ALL)) {
            Iterable<Representante> listaRepresentante = rr.findByEmpresa(user.getEmpresa());
            return listaRepresentante;
        } else {
            Iterable<Representante> listaRepresentante = rr.findAll();
            return listaRepresentante;
        }

    }

    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    @RequestMapping(path = "/hellohi/api/representante/{idRepresentante}", method = RequestMethod.GET)
    public Representante representantePorId(@PathVariable("idRepresentante") Long idRepresentante) {
        try {
            this.representante = rr.findByIdRepresentante(idRepresentante);                   
            for (Representante r : listaRepresentante()) {
                if(r == this.representante){
                  return representante;  
                }
            }        
        } catch (Exception e) {
            System.out.println(e);
        }       
        System.out.println("REPRESENTANTE RWSOURCE ERRO ");
        throw new AuthorizationServiceException("Você não tem permissão!");
    }

    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    @RequestMapping(path = "/hellohi/api/representante/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView salvarRepresentante(@Valid Representante representante, BindingResult result, RedirectAttributes atributes) {

        if (result.hasErrors()) {
            return adicionarRepresentante(representante);
        }

        UserSS user = UserService.authenticated();
        empresa = empresaResource.buscarEmpresaId(user.getEmpresa().getIdEmpresa());
        representante.setEmpresa(empresa);
        representante.setSenha(senha.encode(representante.getSenha()));
        representante.setAtivo(true);
        representante.setLogin(representante.getEmail());
        representante.addPerfil(Perfil.REPRESENTANTE);
        rr.save(representante);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/sistema/cadastro/representante");
        atributes.addFlashAttribute("mensagem", "Representante - " + representante.getIdRepresentante() + " " + representante.getNome() + " salvo com sucesso.");

        return mv;
    }

    //Edita representante
    @PreAuthorize("hasAnyRole('SUPERVISOR')")
    @RequestMapping(path = "/hellohi/api/representante", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE}, headers = "content-type=application/x-www-form-urlencoded , application/json", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView editarRepresentante(@Valid Representante representante, BindingResult result, RedirectAttributes atributes) {

        if (result.hasErrors()) {
            return adicionarRepresentante(representante);
        }

        if (representante.getSenha().length() > 6) {
            rr.save(representante);
        } else {
            representante.setSenha(senha.encode(representante.getSenha()));
        }

        UserSS user = UserService.authenticated();
        representante.setEmpresa(empresaResource.buscarEmpresaId(user.getEmpresa().getIdEmpresa()));
        representante.setLogin(representante.getEmail());
        representante.addPerfil(Perfil.REPRESENTANTE);
        rr.save(representante);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/sistema/cadastro/representante");// Redireciona para o Controller Representante
        atributes.addFlashAttribute("mensagem", "Representante - " + representante.getIdRepresentante() + " editado com sucesso.");//Adiciona Variavel Mensagem para View

        return mv;
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    @RequestMapping(path = "/hellohi/api/representante/{idRepresentante}", method = RequestMethod.DELETE)
    public ModelAndView deletarRepresentante(@PathVariable("idRepresentante") Long idRepresentante, RedirectAttributes atributes) {

        representante = representantePorId(idRepresentante);
        representante.setAtivo(false);//desativa o representante
        rr.save(representante);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/sistema/cadastro/representante");
        atributes.addFlashAttribute("mensagem", "Representante - " + representante.getIdRepresentante() + " removido com sucesso.");//Adiciona Variavel Mensagem para View
        nc.carregaNotificacoesView(mv);
        return mv;
    }

    

    //ESSE ESTA SALVANDO NO HML MAIS NAO NO POSTMAN  <NAO RETORNA A PAGINA>
//    //Salvar representante passando o Id do Empresa
//    @RequestMapping(path = "/hellohi/api/empresa/{idEmpresa}/representante", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE , MediaType.APPLICATION_JSON_VALUE} ,headers = "content-type=application/x-www-form-urlencoded , application/json",produces = {MediaType.APPLICATION_JSON_VALUE})
//    public String salvarRepresentante(@Valid Representante representante, @PathVariable Long idEmpresa) {
//        empresa = er.buscarEmpresaId(idEmpresa);
//        representante.setIdRepresentante(null);
//        representante.setEmpresa(empresa);
//        rr.save(representante);
//        return "Sucesso";
//    }
    Iterable<Checkin> findByRepresentante(Iterable<Representante> listaRepresentante) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @PreAuthorize("hasAnyRole('OPERADOR','REPRESENTANTE')")
    @RequestMapping(path = "/hellohi/api/representante/cadastrarDispositivo", method = RequestMethod.POST)
    public void cadastrarIdDispositivo(@RequestParam("email") String email, HttpServletRequest req) {
        String idDispositivo = req.getHeader("idDispositivo");
        
        System.out.println(email);
        System.out.println("IdDispositivo = " + idDispositivo);

        try {
            try {
                representante = rr.findByLogin(email);
                representante.setIdDispositivo(idDispositivo);
                rr.save(representante);
                System.out.println("Cliente = " + representante.getNome());
            } catch (Exception e) {
                System.out.println("Representante RESOURCE , Erro ao buscar o representante por EMAIL, ao cadastrar Dispositivo" + e);
            } 
        } catch (Exception e) {
            System.out.println(e);
        }

    } 
}
