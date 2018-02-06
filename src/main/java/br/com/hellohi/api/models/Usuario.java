/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.models;

import br.com.hellohi.api.models.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

/*
 *
 * @author junior
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUsuario;

    @OneToMany
    private List<AgendaProspeccao> agendaProspeccao;

    @OneToMany
    private List<AgendaManutencao> agendaManutencao;

    @NotBlank(message = "Informe Nome")
    private String nome;

    @CPF(message="CPF inválido")
    private String cpf;
    
    @Column(unique = true)
    @NotBlank(message = "Informe Email")
    private String email;

    @NotBlank(message="Seleciona o nível do Usuário")
    private String nivelUsuario;
    
    @NotBlank(message = "Seleciona Sexo")
    private String sexo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa ;
    
    private boolean ativo = true;
        
    @JsonIgnore
    private String login = email;

    @JsonIgnore
    private String senha;
    
//    @JsonIgnore
    @ElementCollection(fetch=FetchType.EAGER) // EAGER, par reftornar o perfil ENUM com o Usuario no Json
    @CollectionTable(name="PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    

    //Getters e Setters
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<AgendaProspeccao> getAgendaProspeccao() {
        return agendaProspeccao;
    }

    public void setAgendaProspeccao(List<AgendaProspeccao> agendaProspeccao) {
        this.agendaProspeccao = agendaProspeccao;
    }

    public List<AgendaManutencao> getAgendaManutencao() {
        return agendaManutencao;
    }

    public void setAgendaManutencao(List<AgendaManutencao> agendaManutencao) {
        this.agendaManutencao = agendaManutencao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNivelUsuario() {
        return nivelUsuario;
    }

    public void setNivelUsuario(String nivelUsuario) {
        this.nivelUsuario = nivelUsuario;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getLogin() {
        return email;
    }

    public void setLogin(String login) {
        this.login = login;
    }
     

    
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }
    
    public void addPerfil(Perfil perfil){
        perfis.add(perfil.getCod());
        
    }
    

}
