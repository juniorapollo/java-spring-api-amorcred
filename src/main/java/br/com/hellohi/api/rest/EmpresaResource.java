package br.com.hellohi.api.rest;

import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.Usuario;
import br.com.hellohi.api.models.enums.Perfil;
import br.com.hellohi.api.repository.EmpresaRepository;
import br.com.hellohi.api.repository.UsuarioRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
public class EmpresaResource {

    @Autowired
    BCryptPasswordEncoder senha;

    @Autowired
    EmpresaRepository er;
    Empresa empresa;

    @Autowired
    UsuarioRepository ur;

    @Value("${baseUrl}")
    private String baseUrl;

    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
    @RequestMapping(path = "${baseUrl}/sistema/cadastro/empresa", method = RequestMethod.GET)
    public ModelAndView listaEmpresaPage() {

        Iterable<Empresa> empresa = listaEmpresa();
        ModelAndView mv = new ModelAndView("cadastro/listaEmpresa");

        mv.addObject("empresa", empresa);

        return mv;
    }

    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    @RequestMapping(path = "${baseUrl}/sistema/cadastro/empresa-adicionar", method = RequestMethod.GET)
    public ModelAndView adicionarEmpresa(Empresa empresa) {
        try {
            ModelAndView mv = new ModelAndView("cadastro/adicionar/adicionarEmpresa");
            mv.addObject("empresa", empresa);
            return mv;
        } catch (Exception e) {
            System.out.println("Empresa Resourse:55 " + e);
            return null;
        }

    }

    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/empresa", method = RequestMethod.GET)
    public Iterable<Empresa> listaEmpresa() {
        Iterable<Empresa> listaEmpresa = er.findAll();
        return listaEmpresa;
    }

    @PreAuthorize("hasAnyRole('OPERADOR')")
    @RequestMapping(path = "${baseUrl}/hellohi/api/empresa/{id_empresa}", method = RequestMethod.GET)
    public Empresa buscarEmpresaId(@PathVariable("id_empresa") Long idEmpresa) {
        empresa = er.findByIdEmpresa(idEmpresa);
        return empresa;
    }

    @RequestMapping(path = "${baseUrl}/hellohi/api/empresa", method = RequestMethod.POST)
    public ModelAndView salvarEmpresa(@Valid Empresa empresa, BindingResult result, RedirectAttributes atributes) {

        try {
            if (result.hasErrors()) {
                return adicionarEmpresa(empresa);
            }
            empresa.setAtivo(empresa.isAtivo());
            empresa.setIdEmpresa(null);
            er.save(empresa);

            // criarDev(empresa.getEmail() + ".desenvolvedor", empresa, empresa.getEmail() + ".desenvolvedor");
            ModelAndView mv = new ModelAndView();
            mv.setViewName("redirect:" + baseUrl + "/sistema/cadastro/empresa");
            atributes.addFlashAttribute("mensagem", "Empresa - " + empresa.getIdEmpresa() + " - " + empresa.getRazaoSocial() + " salvo com sucesso.");

            return mv;

        } catch (Exception e) {
            System.out.println("Erro : " + e);
            return adicionarEmpresa(empresa);
        }
    }

    @RequestMapping(path = "${baseUrl}/hellohi/api/empresa/{id_empresa}", method = RequestMethod.DELETE)
    public Iterable<Empresa> deletarEmpresaId(@PathVariable("id_empresa") Long id) {
        empresa = buscarEmpresaId(id);
        empresa.setAtivo(false);
        er.save(empresa);
        return listaEmpresa();
    }

    @RequestMapping(path = "${baseUrl}/hellohi/api/empresa/", method = RequestMethod.PUT)
    public Empresa editaEmpresa(@RequestBody @Valid Empresa empresa) {
        return er.save(empresa);
    }    

}
