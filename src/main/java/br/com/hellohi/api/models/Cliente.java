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
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCliente;

    private Integer codigoInternoCliente;

    @OneToMany
    private List<AgendaManutencao> agenda;
    @NotBlank(message = "Informe Cnpj")
    private String cnpj;

    @NotBlank(message = "Informe Razão Social")
    private String razaoSocial;

    @NotBlank(message = "Informe Nome Fantasia")
    private String nomeFantasia;

    @NotBlank(message = "Informe Email")
    private String email;

    @NotBlank(message = "Informe Responsavel")
    private String responsavel;

    @NotBlank(message = "Informe Telefone")
    private String telefone;

    @NotBlank(message = "Informe Celular")
    private String celular;

    @NotBlank(message = "Informe Cep")
    private String cep;

    @NotBlank(message = "Informe Logradouro")
    private String logradouro;

    private String complemento;

    private int numeroEnd;

    @NotBlank(message = "Informe Bairro")
    private String bairro;

    @NotBlank(message = "Informe Cidade")
    private String cidade;

    @NotBlank(message = "Informe Estado")
    private String uf;

    @NotBlank(message = "Informe Senha")
    private String senha;

    @ManyToOne()
    @JoinColumn(name = "idRepresentante")
    private Representante representante; //Tentar Iterable

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getCodigoInternoCliente() {
        return codigoInternoCliente;
    }

    public void setCodigoInternoCliente(Integer codigoInternoCliente) {
        this.codigoInternoCliente = codigoInternoCliente;
    }

    public List<AgendaManutencao> getAgenda() {
        return agenda;
    }

    public void setAgenda(List<AgendaManutencao> agenda) {
        this.agenda = agenda;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public int getNumeroEnd() {
        return numeroEnd;
    }

    public void setNumeroEnd(int numeroEnd) {
        this.numeroEnd = numeroEnd;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

}
