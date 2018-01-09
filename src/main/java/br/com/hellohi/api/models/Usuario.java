/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
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
    
    @NotBlank(message = "Informe Email")
    private String email;

    @NotBlank(message = "Informe Login")
    private String login;

    @NotBlank(message = "Informe Senha")
    private String senha;

    @Max(value=3)
    @Min(value=1,message="Seleciona o nível do Usuário")
    private int nivelUsuario;
    
    @NotBlank(message = "Seleciona Sexo")
    private String sexo;

    @ManyToOne()
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa ;
    
    private boolean ativo = true;

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

    public String getLogin() {
        return login;
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

    public int getNivelUsuario() {
        return nivelUsuario;
    }

    public void setNivelUsuario(int nivelUsuario) {
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
    
    

}
