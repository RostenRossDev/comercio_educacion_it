package com.comercio.app.security.filters;

import com.comercio.app.entities.Usuario;
import com.comercio.app.security.SimpleGrantedAuthorityJsonCreator;
import com.comercio.app.security.TokenJwtConfig;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION);

        if(header == null || !header.startsWith(TokenJwtConfig.PREFIX_TOKEN)){
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(TokenJwtConfig.PREFIX_TOKEN, "");
        try {
            Claims claims = Jwts.parser().verifyWith(TokenJwtConfig.SECRET_KEY).build().parseSignedClaims(token).getPayload();
            String username = claims.getSubject();
            username = (String) claims.get("username");
            //String email = (String) claims.get("email");
            Object authoritiesClaims =  claims.get("authorities");

            Collection<? extends GrantedAuthority> authorities = Arrays
                    .asList(new ObjectMapper()
                            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
                            .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class)
                    );

            UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);
            chain.doFilter(request, response);
        }catch (JwtException e){
            e.printStackTrace();
            Map<String, String> body = new HashMap<>();
            body.put("error", e.getMessage());
            body.put("message", "El token no es valido");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        }

    }
}
