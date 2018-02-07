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
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

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
    @CNPJ(message = "CNPJ inválido")
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
    private String estado;

    @JsonIgnore
    private String login = email;

    @JsonIgnore
    @NotBlank(message = "Informe Senha")
    private String senha;

    private String idDispositivo;
    
    @JsonIgnore
    private boolean ativo = true;

    @JsonIgnore
    private boolean msgNotificacao = false;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa; //Tentar Iterable

    @ManyToOne()
    @NotNull(message = "Selecione um Gerente")
    @JoinColumn(name = "idRepresentante")
    private Representante representante; //Tentar Iterable

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER) // EAGER, par reftornar o perfil ENUM com o Usuario no Json
    @CollectionTable(name = "PERFIL_CLIENTE")
    private Set<Integer> perfis = new HashSet<>();

//GETTERS E SETTERS
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public boolean isMsgNotificacao() {
        return msgNotificacao;
    }

    public void setMsgNotificacao(boolean msgNotificacao) {
        this.msgNotificacao = msgNotificacao;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCod());
    }

    public String getLogin() {
        return email;
    }

    public void setLogin(String login) {
        this.login = login;
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

        
    
}
