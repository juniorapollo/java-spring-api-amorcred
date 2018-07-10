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
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${baseUrl}")
    String baseUrl;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    UserDetailsService uSerDetailService;

    @Autowired
    JWTUtil jwtUtil;

    private static final HttpMethod GET = HttpMethod.GET;
    private static final HttpMethod POST = HttpMethod.POST;
    private static final HttpMethod OPTIONS = HttpMethod.OPTIONS;

    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        //Configurações para Usuários
        http.
                authorizeRequests()
                //.antMatchers("/css/**", "/images/** " ,"/armorcred/** " , "/js/** ", "/assets/** ", "/fonts/** ").permitAll()
                .antMatchers("/assets/**", "/css/**", "/js/**", "/images/**", "/fonts/**").permitAll()
                .antMatchers(POST, baseUrl + "/login/**").permitAll() // libera esse endPoint sem autenticacao
                .antMatchers(POST, baseUrl + "/hellohi/api/notificacao/**").permitAll()
                .antMatchers(OPTIONS, baseUrl + "/hellohi/api/notificacao/**").permitAll()
//              .antMatchers(POST, baseUrl + "/hellohi/api/empresa/criarDev").permitAll()
                .antMatchers(GET, baseUrl + "/hellohi/api/checkin/fotoCheckin/**").permitAll()
                .anyRequest() // para todo o resto 
                .authenticated()// vc exige autenticacao
                .and()
                .formLogin()
                .loginPage(baseUrl + "/entrar")
                .successHandler(loginSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/entrar?logout")
                .permitAll();

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
            redirect.sendRedirect(request, response, baseUrl + "/sistema");   // <------        
        }
    }
}
