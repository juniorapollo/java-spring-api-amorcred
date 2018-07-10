
package br.com.hellohi.api.security;

import br.com.hellohi.api.models.Credenciais;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author junior
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

//    AUTENTICAÇÃO;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

        try {
            //Pegando o Login e Senha da pagina de Login
            Credenciais creds = new ObjectMapper().readValue(req.getInputStream(), Credenciais.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getLogin(), creds.getSenha(), new ArrayList<>());

            Authentication auth = authenticationManager.authenticate(authToken);

            return auth;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
            FilterChain chain, Authentication auth) throws IOException, ServletException {
        String perfil = "";
        String login =""; 
        int count = 0;
        try {

            do {
                try {
                 
                    login = ((UserSS) auth.getPrincipal()).getUsername();
                    perfil = ((UserSS) auth.getPrincipal()).getNivelUsuario();
                    System.out.println("User PERFIL: " + perfil);
                } catch (Exception e) {                   
                    System.out.println(e);
                }

                try {
                    login = ((UserRepresentanteSS) auth.getPrincipal()).getUsername();
                    perfil = ((UserRepresentanteSS) auth.getPrincipal()).getAuthorities().toString();
                    System.out.println("Representante PERFIL: " + perfil);
                    
                } catch (Exception e) {
                    System.out.println(e);
                }

                try {
                    login = ((UserClienteSS) auth.getPrincipal()).getUsername();
                    perfil = ((UserClienteSS) auth.getPrincipal()).getAuthorities().toString();
                    System.out.println("Cliente PERFIL: " + perfil);
                    System.out.println("TRY 3");
                } catch (Exception e) {
                    System.out.println(e);
                }
                count++;

            } while (perfil.equals("") && count < 3);

        } catch (Exception e) {
            System.out.println("SAIU DO LOOP ");
        }

        String tokenLogin = jwtUtil.generateToken(login);
        String tokenPerfil = jwtUtil.generateToken(perfil);
        System.out.println("TOKENUsuario :" + tokenLogin);
        System.out.println("Token Perfil " + tokenPerfil);

        res.addHeader("Authorization","Bearer "+tokenLogin);
        res.addHeader("access-control-expose-headers", "Authorization");
        res.addHeader("Perfil",tokenPerfil);
        res.addHeader("access-control-expose-headers", "Perfil");
        
        

    }

}
