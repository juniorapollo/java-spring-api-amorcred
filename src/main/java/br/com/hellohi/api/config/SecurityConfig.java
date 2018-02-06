package br.com.hellohi.api.config;

import br.com.hellohi.api.security.JWTAuthenticationFilter;
import br.com.hellohi.api.security.JWTAuthorizationFilter;
import br.com.hellohi.api.security.JWTUtil;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *
 * @author junior
 */
//@Order(SecurityProperties.IGNORED_ORDER + 2)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    UserDetailsService uSerDetailService;

    @Autowired
    JWTUtil jwtUtil;


    private static final HttpMethod GET = HttpMethod.GET;
    private static final HttpMethod POST = HttpMethod.POST;
//    private static final HttpMethod PUT = HttpMethod.PUT;
//    private static final HttpMethod DELETE = HttpMethod.DELETE;
    private static final HttpMethod OPTIONS = HttpMethod.OPTIONS;
//    private static final HttpMethod PATCH = HttpMethod.PATCH;
//    private static final HttpMethod HEAD = HttpMethod.HEAD;
//    private static final HttpMethod TRACE = HttpMethod.TRACE;
//
//    private static final String PERFIL_SYSTEM_ADMIN = Perfil.SYSTEM_ADMIN.toString();
//    private static final String PERFIL_OPERADOR = Perfil.OPERADOR.toString();
//    private static final String PERFIL_ADMINISTRADOR = Perfil.ADMINISTRADOR.toString();
//    private static final String PERFIL_SUPERVISOR = Perfil.SUPERVISOR.toString();
//
//    private static final String[] OPERADOR_MATCHERS_GET = {
//        "/sistema/",
//        "/sistema/cadastro/representante/",
//        "/sistema/cadastro/cliente/",
//        
//    };
//
//    private static final String[] SUPERVISOR_MATCHERS_GET = {
//        "/sistema/cadastro/usuario/"
//
//    };
//
//    private static final String[] SUPERVISOR_MATCHERS_POST = {
//        "/hellohi/api/usuario/**",
//        "/hellohi/api/representante/**",
//        "/hellohi/api/cliente/**",};
//
//    private static final String[] SUPERVISOR_MATCHERS_PUT = {
//        "/hellohi/api/usuario/**",
//        "/hellohi/api/representante/**",
//        "/hellohi/api/cliente/**"
//    };
//
//    private static final String[] SYSTEM_ADMIN_MATCHERS_GET = {
//        "/hellohi/api/usuario/**",
//        "/hellohi/api/representante/**",
//        "/hellohi/api/cliente/**"
//    };
//
//    private static final String[] ADMINISTRADOR_MATCHERS_DELETE = {
//        "/hellohi/api/usuario/**",
//        "/hellohi/api/representante/**",
//        "/hellohi/api/cliente/**"
//    };
//
////    private static final String[] SYSTEM_ADMIN_MATCHERS_GET = {
////       "/hellohi/api/**"    
////    };

    private static final String[] PUBLIC_MATCHERS = {
        "/sistema/"
    };

//    //Método para o Spring não criar Sessão se o Usuário não Logar
//    @Bean(autowire = Autowire.BY_TYPE)
//    public SimpleUrlAuthenticationFailureHandler createFailureHandler() {
//        SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
//        failureHandler.setAllowSessionCreation(false);
//        return failureHandler;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
//        http.formLogin().failureHandler(createFailureHandler());
      
        
        
//        //Configurações para Usuários
//

        http.
//                /*(SYSTEM_ADMIN) Administrador Geral do Sistema  */
                authorizeRequests()
                .antMatchers("/css/**", "/img/**", "/js/**").permitAll()
                .antMatchers(POST, "/login/**").permitAll() // libera esse endPoint sem autenticacao
                .antMatchers(POST,"/hellohi/api/notificacao/**").permitAll()                
//                .antMatchers(GET,"/hellohi/api/cliente/**").permitAll()
                .antMatchers(OPTIONS,"/hellohi/api/notificacao/**").permitAll()
                ////                .antMatchers(POST, "/login/").permitAll()  
                ////                
                ////                /*(OPERADOR)*/
                ////                .antMatchers(GET, OPERADOR_MATCHERS_GET).hasAnyRole(PERFIL_OPERADOR)
                ////                /*(SUPERVISOR)*/
                ////                .antMatchers(GET, SUPERVISOR_MATCHERS_GET).hasAnyRole(PERFIL_SUPERVISOR)
                ////                .antMatchers(POST, SUPERVISOR_MATCHERS_POST).hasAnyRole(PERFIL_SUPERVISOR)
                ////                .antMatchers(PUT, SUPERVISOR_MATCHERS_PUT).hasAnyRole(PERFIL_SUPERVISOR)
                ////                /*(ADMINISTRADOR)*/
                ////                .antMatchers(DELETE, ADMINISTRADOR_MATCHERS_DELETE).hasAnyRole(PERFIL_ADMINISTRADOR)
                ////                .antMatchers(GET, SYSTEM_ADMIN_MATCHERS_GET).hasAnyRole(PERFIL_SYSTEM_ADMIN)
                ////                
                //                        
                ////                .antMatchers(POST, SUPERVISOR_MATCHERS_POST).hasAnyRole(PERFIL_SUPERVISOR)
                ////                //                .antMatchers(PUT, SUPERVISOR_MATCHERS_PUT).hasAnyRole(PERFIL_SUPERVISOR)
                ////                //                .antMatchers(DELETE, SYSTEM_ADMIN_MATCHERS).hasAnyRole(PERFIL_SYSTEM_ADMIN)
                ////                //                .antMatchers(HEAD, SYSTEM_ADMIN_MATCHERS).hasAnyRole(PERFIL_SYSTEM_ADMIN)
                ////                //                .antMatchers(OPTIONS, SYSTEM_ADMIN_MATCHERS).hasAnyRole(PERFIL_SYSTEM_ADMIN)
                ////                //                .antMatchers(PATCH, SYSTEM_ADMIN_MATCHERS).hasAnyRole(PERFIL_SYSTEM_ADMIN)
                ////                //                .antMatchers(POST, SYSTEM_ADMIN_MATCHERS).hasAnyRole(PERFIL_SYSTEM_ADMIN)
                ////                //                .antMatchers(PUT, SYSTEM_ADMIN_MATCHERS).hasAnyRole(PERFIL_SYSTEM_ADMIN)
                ////                //                .antMatchers(TRACE, SYSTEM_ADMIN_MATCHERS).hasAnyRole(PERFIL_SYSTEM_ADMIN)
                ////                //                
                ////
                ////                //                .antMatchers(POST, OPERADOR_MATCHERS_GET).hasAnyRole(PERFIL_OPERADOR)
                //// 
                ////                //                
                ////                //                .antMatchers(HttpMethod.PUT, SYSTEM_ADMIN_MATCHERS).hasAnyRole(PERFIL_SYSTEM_ADMIN)
                ////                //                .antMatchers(HttpMethod.DELETE, SYSTEM_ADMIN_MATCHERS).hasAnyRole(PERFIL_SYSTEM_ADMIN)
                ////                //                
                ////                //                .antMatchers(HttpMethod.POST, SUPERVISOR_MATCHERS).hasAnyRole(PERFIL_ADMINISTRADOR)
                ////
                ////                //                .antMatchers(HttpMethod.GET, OPERADOR_MATCHERS).hasAnyRole(PERFIL_OPERADOR)                
                .anyRequest() // para todo o resto 
                .authenticated() // vc exige autenticacao
                .and()
                .formLogin()
                .loginPage("/entrar") 
                .successHandler(loginSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/entrar?logout")
                .permitAll();
//
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, uSerDetailService));
}

    //Gerar Criptografia nas senhas 
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(uSerDetailService).passwordEncoder(bCryptPasswordEncoder());
    }

    // Configuracao para aceitar requisao de varios lugares CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



   //Redireciona para página apos o login
    @Component
    public class LoginSuccessHandler implements AuthenticationSuccessHandler {

        RedirectStrategy redirect = new DefaultRedirectStrategy();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                HttpServletResponse response, Authentication auth)
                throws IOException, ServletException {
            redirect.sendRedirect(request, response, "/sistema");   // <------        
        }
    }
}
