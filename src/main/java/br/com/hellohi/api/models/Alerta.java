/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author junior
 */
@Entity
@Table(name = "alerta")
public class Alerta implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Id
    private Long idAlerta;

    private boolean alerta = false;

    /**
     * Getters e Setters
     *
     * @return
     */
    public Long getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Long idAlerta) {
        this.idAlerta = idAlerta;
    }

    public boolean isAlerta() {
        return alerta;
    }

    public void setAlerta(boolean alerta) {
        this.alerta = alerta;
    }

}

