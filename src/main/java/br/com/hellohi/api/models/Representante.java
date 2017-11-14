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
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author junior
 */
@Entity
@Table(name = "representante")
public class Representante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRepresentante;

    @OneToMany
    private List<AgendaProspeccao> agendaProspeccao;

    @OneToMany()
    private List<Cliente> cliente; //Tentar Iterable

    @NotBlank(message = "Informe Nome")
    private String nome;

    @NotBlank(message = "Informe Cpf")
    private String cpf;

    @NotBlank(message = "Informe Sexo")
    private String sexo;

    private String telefone;

    @NotBlank(message = "Informe Celular")
    private String celular;

    @NotBlank(message = "Informe Email")
    private String email;

    @NotBlank(message = "Informe Função")
    private String funcao;

    @NotBlank(message = "Informe Cidade")
    private String cidadeAtuacao;

    @NotBlank(message = "Informe Estado")
    private String estado;

    @NotBlank(message = "Informe Login")
    private String login;

    @NotBlank(message = "Informe Senha")
    private String senha;

    @ManyToOne()
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa; //Tentar Iterable

    //Getters e Setters
    public Long getIdRepresentante() {
        return idRepresentante;
    }

    public void setIdRepresentante(Long idRepresentante) {
        this.idRepresentante = idRepresentante;
    }

    public List<AgendaProspeccao> getAgendaProspeccao() {
        return agendaProspeccao;
    }

    public void setAgendaProspeccao(List<AgendaProspeccao> agendaProspeccao) {
        this.agendaProspeccao = agendaProspeccao;
    }

    public List<Cliente> getCliente() {
        return cliente;
    }

    public void setCliente(List<Cliente> cliente) {
        this.cliente = cliente;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getCidadeAtuacao() {
        return cidadeAtuacao;
    }

    public void setCidadeAtuacao(String cidadeAtuacao) {
        this.cidadeAtuacao = cidadeAtuacao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
