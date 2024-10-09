package com.comercio.app.services.impl;

import com.comercio.app.entities.Role;
import com.comercio.app.entities.Usuario;
import com.comercio.app.repositories.UsuarioRepository;
import com.comercio.app.services.UsuarioService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username; " + username);

        Usuario u = usuarioRepository.findByUsername(username);
        log.info("Usuario; " + u);

        if(u == null){
            log.info("acaaaaaa");
            throw new UsernameNotFoundException(String.format("El nombre de u '%s' no existe en el sistema.", username));
        }


        List<GrantedAuthority> authorities = u.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        log.info(u);
        User user = new User(u.getUsername(),
                u.getPassword(),
                u.getEnabled(),
                true,
                true,
                true,
                authorities);
        log.info(user);
        return user;
    }
}
