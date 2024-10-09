package com.comercio.app.security.filters;

import com.comercio.app.entities.Usuario;
import com.comercio.app.security.TokenJwtConfig;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;
import java.util.*;

@Log4j2
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authManager;

    public JwtAuthenticationFilter(AuthenticationManager authManager){
        this.authManager = authManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Usuario usuario = null;
        String username = null;
        String password = null;

        try {
            usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            username = usuario.getUsername();
            password = usuario.getPassword();
        } catch (StreamReadException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        log.info("passw: "+ usuario.getPassword());
        log.info("user: "+ usuario.getUsername());
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User usuario = (User)authResult.getPrincipal();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("username", usuario.getUsername())
                .build();

        String token = Jwts.builder()
                .subject(usuario.getUsername())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .issuedAt(new Date())
                .claims(claims)
                .signWith(TokenJwtConfig.SECRET_KEY)
                .compact();

        response.addHeader(TokenJwtConfig.HEADER_AUTHORIZATION,  TokenJwtConfig.PREFIX_TOKEN.concat(token));

        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", usuario.getUsername());
        body.put("message", String.format("Hola %s has iniciado sesión con exito.", usuario.getUsername()));
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Credenciales invalidos!.");
        body.put("error", failed.getMessage());
        failed.printStackTrace();
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
