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
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

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

    @JsonIgnore
    @OneToMany
    private List<AgendaProspeccao> agendaProspeccao;

    @OneToMany()
    private List<Cliente> cliente; //Tentar Iterable

    @NotBlank(message = "Informe Nome")
    private String nome;

    @NotBlank(message = "Informe Cpf")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "Informe Sexo")
    private String sexo;

    private String telefone;

    @NotBlank(message = "Informe Celular")
    private String celular;

    @Column(unique=true)
    @NotBlank(message = "Informe Email")
    private String email;

    @NotBlank(message = "Informe Função")
    private String funcao;

    @NotBlank(message = "Informe Cep")
    private String cep;

    @NotBlank(message = "Informe Estado")
    private String estado;

    @NotBlank(message = "Informe Cidade")
    private String cidadeAtuacao;

    @NotBlank(message = "Informe Bairro")
    private String bairro;

    @NotBlank(message = "Informe Rua")
    private String logradouro;

    private String numeroEnd;

    private String complemento;

    @JsonIgnore
    @Column(unique=true)
    private String login = email;

    @JsonIgnore
    @NotBlank(message = "Informe Senha")
    private String senha;

    private String idDispositivo;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa; //Tentar Iterable

    @JsonIgnore
    @OneToMany()
    private List<Checkin> checkins; //Tentar Iterable

    private boolean ativo = true;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER) // EAGER, par reftornar o perfil ENUM com o Usuario no Json
    @CollectionTable(name = "PERFIL_REPRESENTANTE")
    private Set<Integer> perfis = new HashSet<>();

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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidadeAtuacao() {
        return cidadeAtuacao;
    }

    public void setCidadeAtuacao(String cidadeAtuacao) {
        this.cidadeAtuacao = cidadeAtuacao;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
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

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public List<Checkin> getCheckins() {
        return checkins;
    }

    public void setCheckins(List<Checkin> checkins) {
        this.checkins = checkins;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCod());
    }

    public String getNumeroEnd() {
        return numeroEnd;
    }

    public void setNumeroEnd(String numeroEnd) {
        this.numeroEnd = numeroEnd;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

}
