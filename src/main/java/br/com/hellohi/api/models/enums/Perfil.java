/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.models.enums;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author junior
 */

public enum Perfil {
    SYSTEM_ADMIN(1,"ROLE_SYSTEM_ADMIN"),
    ADMINISTRADOR(2,"ROLE_ADMINISTRADOR"),
    SUPERVISOR(3,"ROLE_SUPERVISOR"),
    OPERADOR(4,"ROLE_OPERADOR"),
    REPRESENTANTE(5,"ROLE_REPRESENTANTE"),
    CLIENTE(6,"ROLE_CLIENTE"), 
    SYSTEM_ADMIN_ALL(7,"ROLE_SYSTEM_ADMIN_ALL");
    
    
    private int cod;
    private String descricao;

    private Perfil(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }   
    
    
    public static Perfil toEnum(Integer cod){
        
        if( cod == null){
            return null;
        }
        
        for(Perfil x : Perfil.values()){
            if(cod.equals(x.getCod())){
                return x;
            }
        }
        
        throw new IllegalArgumentException("Id Inválido: " + cod);
    }
    
}
