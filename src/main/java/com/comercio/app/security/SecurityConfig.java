package com.comercio.app.security;

import com.comercio.app.security.filters.JwtAuthenticationFilter;
import com.comercio.app.security.filters.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private AuthenticationConfiguration authConfig;

      @Bean
    public AuthenticationManager authManager() throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/usuarios", "/login").permitAll()
                    .anyRequest()
                    .authenticated())
                    .addFilter(new JwtAuthenticationFilter(authManager()))
                    .addFilter(new JwtValidationFilter(authManager()))
                    .csrf(csrf -> csrf.disable()) //Es una proteccion cuando se usan vistas, osea cuando trabajamos con front en nuestra aplicacion pero con REST trae problemas.
                    .sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(header -> header.frameOptions(options -> options.disable())); //Por defecto el session cration policy guarda en el estado de conexion en la session HTTP, con STATELESS no se guarda ya que la manejamos en el token

        return http.build();
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:8080, http://localhost:8080, https://localhost:8082, http://localhost:8082") // Reemplaza con el origen de tu cliente
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

}
