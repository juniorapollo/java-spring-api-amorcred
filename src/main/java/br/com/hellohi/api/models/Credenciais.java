/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.models;

import java.io.Serializable;

/**
 *
 * @author junior
 */
public class Credenciais implements Serializable {
    private static final Long serialVersionUID = 1L;
    
    private String login;
    private String senha;

    public Credenciais() {
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
    
    
    
}
