/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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

    @NotBlank()
    private String latitude;

    @NotBlank()
    private String longitude;
    
    @Lob
    @NotBlank()
    private String foto;

    @ManyToOne
    @JoinColumn(name = "idAgendaProspeccao")
    private AgendaProspeccao agendaProspeccao;

    @ManyToOne
    @JoinColumn(name = "idAgendaManutencao")
    private AgendaManutencao agendaManutencao;

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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

}
