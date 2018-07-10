/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author junior
 */
@Entity
@Table(name = "fotoCheckin")
public class FotoCheckin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idFotoCheckin;

    @Lob
    @NotBlank()
    private String foto;
    
    @ManyToOne()
    @JoinColumn(name = "idCheckin")
    private Checkin checkin;

    public FotoCheckin() {
    }
    
    
    
    public Long getIdFotoCheckin() {
        return idFotoCheckin;
    }

    public void setIdFotoCheckin(Long idFotoCheckin) {
        this.idFotoCheckin = idFotoCheckin;
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
