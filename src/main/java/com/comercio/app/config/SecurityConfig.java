package com.comercio.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.requiresChannel(channel -> channel.anyRequest().requiresSecure())

                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/favicon.ico","/cart/add","/create_preference", "/inicio", "/css/**", "/js/**", "/img/**", "/WEB-INF/views/**").permitAll()
                        .requestMatchers("/h2-console/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/")
                        .authenticated()
                        .requestMatchers("/cursos")
                        .permitAll()
                        .requestMatchers("/cursos/**")
                        .permitAll()
                        .requestMatchers("/curso/**")
                        .permitAll() //cambiar a authorizado con rol estudiante
                        .requestMatchers("/crear-cuenta")
                        .permitAll() //cambiar a authorizado con rol estudiante
                        .requestMatchers("/perfil")
                        .hasAnyRole("ESTUDIANTE", "ADMIN") //cambiar a authorizado con rol estudiante
                        .requestMatchers("/todosLosEstudiantes")
                        .hasAnyRole("ADMIN")
                        .anyRequest().authenticated())

                .headers(header -> header.frameOptions(options -> options.disable()))
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .defaultSuccessUrl("/perfil", true)
                                .usernameParameter("email")
                                .permitAll() // Permitir acceso público a la página de inicio de sesión
                )
                .logout(logout -> logout.permitAll() // Permitir acceso público al proceso de cierre de sesión
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedPage("/error_403")
                                .accessDeniedHandler(customAccessDeniedHandler)// Página de acceso denegado personalizada
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Política de creación de sesión según sea necesario
                );

        return http.build();
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
