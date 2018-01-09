/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hellohi.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author junior
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

   
        @Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder
			.inMemoryAuthentication()
			.withUser("admin").password("admin123").roles("PG_PROJETOS", "PG_REL_CUSTOS", "PG_REL_EQUIPE");
	}
    
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().antMatchers("/**").hasAnyRole("PG_PROJETOS")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/entrar")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/entrar?logout")
                .permitAll(); 

    }
}
