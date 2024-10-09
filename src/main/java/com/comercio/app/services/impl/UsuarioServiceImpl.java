package com.comercio.app.services.impl;

import com.comercio.app.entities.Role;
import com.comercio.app.entities.Usuario;
import com.comercio.app.repositories.RoleRespository;
import com.comercio.app.repositories.UsuarioRepository;
import com.comercio.app.services.UsuarioService;
import com.comercio.app.utils.validations.Validator;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

@Log4j2
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private RoleRespository roleRespository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @Override
    @Transactional
    public ResponseEntity<?> save(@Valid Usuario u, BindingResult result) {
        if(result.hasErrors()){
            return Validator.validarUsuario(result);
        }

        Role role = roleRespository.findByName("ROLE_USER").orElse(null);
        List<Role> roles = List.of(role);
        u.setRoles(roles);
        u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
        u.setEnabled(Boolean.TRUE);
        log.info(u);
        Usuario newUsuario = repository.save(u);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUsuario);
    }

    @Override
    public Boolean existsByUsername(String nombre) {
        return repository.existsByUsername(nombre);
    }
}
