/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author junior
 */
@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

     public Empresa() {
    }
    
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idEmpresa;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @OneToMany()
    private List<Checkin> checkins; //Tentar Iterable
    
    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @OneToMany()
    private List<Cliente> clientes; //Tentar Iterable

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER) 
    @OneToMany()
    private List<Usuario> usuarios; //Tentar Iterable

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @OneToMany()
    private List<Representante> representantes; //Tentar Iterable

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Informe Cnpj")
    private String cnpj;

    @Column(unique = true)
    @NotBlank(message = "Informe Razão Social")
    private String razaoSocial;

    @NotBlank(message = "Informe Nome Fantasia")
    private String nomeFantasia;
    
    @NotBlank(message = "Informe Responsável")
    private String responsavel;
    
    
    @NotBlank(message = "Informe Email")
    private String email;

    @NotBlank(message = "Informe Cep")
    private String cep;

    @NotBlank(message = "Informe Logradouro")
    private String logradouro;

     @NotBlank(message = "Informe Numero Endereço")
    private String numeroEnd;

    @NotBlank(message = "Informe Bairro")
    private String bairro;

    @NotBlank(message = "Informe Cidade")
    private String cidade;

    @NotBlank(message = "Informe Estado")
    private String uf;

    private String complemento;

    @NotBlank(message = "Informe Telefone")
    private String telefone;
    
    private boolean ativo;

   

    
    
    //Getters e Setters
    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public List<Checkin> getCheckins() {
        return checkins;
    }

    public void setCheckins(List<Checkin> checkins) {
        this.checkins = checkins;
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

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
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

    public String getNumeroEnd() {
        return numeroEnd;
    }

    public void setNumeroEnd(String numeroEnd) {
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

    
    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Representante> getRepresentantes() {
        return representantes;
    }

    public void setRepresentantes(List<Representante> representantes) {
        this.representantes = representantes;

    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Empresa{" + "idEmpresa=" + idEmpresa + ", checkins=" + checkins + ", clientes=" + clientes + ", usuarios=" + usuarios + ", representantes=" + representantes + ", cnpj=" + cnpj + ", razaoSocial=" + razaoSocial + ", nomeFantasia=" + nomeFantasia + ", responsavel=" + responsavel + ", email=" + email + ", cep=" + cep + ", logradouro=" + logradouro + ", numeroEnd=" + numeroEnd + ", bairro=" + bairro + ", cidade=" + cidade + ", uf=" + uf + ", complemento=" + complemento + ", telefone=" + telefone + ", ativo=" + ativo + '}';
    }
    
    

}
