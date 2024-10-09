package com.comercio.app.services;

import com.comercio.app.entities.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface UsuarioService {

    ResponseEntity<?> findAll();
    ResponseEntity<?> save(Usuario u, BindingResult result);
    Boolean existsByUsername(String username);
}
