/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author junior
 */
@Entity
@Table(name = "checkin")
public class Checkin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCheckin;

    private LocalDateTime dataHoraCheckin;

    private String dataFormatada;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    private String enderecoCompleto;

  
    @Lob
    private String foto;

    private String nomeEmpresa;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idAgendaProspeccao")
    private AgendaProspeccao agendaProspeccao;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idAgendaManutencao")
    private AgendaManutencao agendaManutencao;

    @ManyToOne()
    @JoinColumn(name = "idRepresentante")
    private Representante representante; //Tentar Iterable

    @ManyToOne()
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa; //Tentar Iterable

   
    @OneToMany()
    private List<FotoCheckin> fotoCheckin; //Tentar Iterable
    
    //Getters e Setters
    public Long getIdCheckin() {
        return idCheckin;
    }

    public void setIdCheckin(Long idCheckin) {
        this.idCheckin = idCheckin;
    }

    public LocalDateTime getDataHoraCheckin() {
        return dataHoraCheckin;
    }

    public void setDataHoraCheckin(LocalDateTime dataHoraCheckin) {
        this.dataHoraCheckin = dataHoraCheckin;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }

    public void setEnderecoCompleto(String enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public AgendaProspeccao getAgendaProspeccao() {
        return agendaProspeccao;
    }

    public void setAgendaProspeccao(AgendaProspeccao agendaProspeccao) {
        this.agendaProspeccao = agendaProspeccao;
    }

    public AgendaManutencao getAgendaManutencao() {
        return agendaManutencao;
    }

    public void setAgendaManutencao(AgendaManutencao agendaManutencao) {
        this.agendaManutencao = agendaManutencao;
    }

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getDataFormatada() {
        return dataFormatada;
    }

    public void setDataFormatada(String dataFormatada) {
        this.dataFormatada = dataFormatada;
    }

    public List<FotoCheckin> getFotoCheckin() {
        return fotoCheckin;
    }

    public void setFotoCheckin(List<FotoCheckin> fotoCheckin) {
        this.fotoCheckin = fotoCheckin;
    }

   

    @Override
    public String toString() {
        return "Checkin{" + "idCheckin=" + idCheckin + ", dataFormatada=" + dataFormatada + ", latitude=" + latitude + ", longitude=" + longitude + ", enderecoCompleto=" + enderecoCompleto + ", nomeEmpresa=" + nomeEmpresa + '}';
    }

}
