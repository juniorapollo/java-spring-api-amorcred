
package br.com.hellohi.api.security;

import br.com.hellohi.api.models.Empresa;
import br.com.hellohi.api.models.enums.Perfil;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author junior
 */
public class UserClienteSS implements UserDetails {

    
    
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private String login;
    private String senha;
    private Empresa empresa;

    private Collection<? extends GrantedAuthority> authorities;

    public UserClienteSS() {
    }

    public UserClienteSS(Long id, String nome, String login, String senha, Empresa empresa, Set<Perfil> perfis) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.empresa = empresa;
        this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }
    
  
    public Empresa getEmpresa() {
        return empresa;
    }

    //Retornar os Perfis para Autorizacao
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    //A conta não está inspirada
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //A conta não está Bloqueada
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //As credenciais não estão expiradas
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Os Usuarios estão Ativos?
    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean hasRole(Perfil perfil) {
        return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
    }

}
