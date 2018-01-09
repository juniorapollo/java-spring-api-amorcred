/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author junior
 */
@Entity
public class Checkin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCheckin;

    private LocalDateTime dataHoraCheckin;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataD;
            
    @NotNull
    private double latitude;

    @NotNull
    private double  longitude;
    
    private String enderecoCompleto;
    
    @Lob
    @NotBlank()
    private String foto;

    @ManyToOne
    @JoinColumn(name = "idAgendaProspeccao")
    private AgendaProspeccao agendaProspeccao;

    @ManyToOne
    @JoinColumn(name = "idAgendaManutencao")
    private AgendaManutencao agendaManutencao;
    
    
    @ManyToOne()
    @JoinColumn(name = "idRepresentante")
    private Representante representante; //Tentar Iterable

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

    public Date getDataD() {
        return dataD;
    }

    public void setDataD(Date dataD) {
        this.dataD = dataD;
    }

    public Representante getRepresentante() {
        return representante;
    }

    public void setRepresentante(Representante representante) {
        this.representante = representante;
    }

    
    
}
