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
import javax.persistence.OneToOne;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author junior
 */
@Entity
public class Checkout implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCheckout;

    private LocalDateTime dataHoraCheckout;

    @NotBlank()
    private String latitude;

    @NotBlank()
    private String longitude;

    @NotBlank()
    private String foto;

    @OneToOne
    @JoinColumn(name = "idCheckin")
    private Checkin checkin;

    //Getters e Setters
    public Long getIdCheckout() {
        return idCheckout;
    }

    public void setIdCheckout(Long idCheckout) {
        this.idCheckout = idCheckout;
    }

    public LocalDateTime getDataHoraCheckout() {
        return dataHoraCheckout;
    }

    public void setDataHoraCheckout(LocalDateTime dataHoraCheckout) {
        this.dataHoraCheckout = dataHoraCheckout;
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

    public Checkin getCheckin() {
        return checkin;
    }

    public void setCheckin(Checkin checkin) {
        this.checkin = checkin;
    }

}
