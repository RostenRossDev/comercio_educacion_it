package com.comercio.app.controllers;

import com.comercio.app.entities.Usuario;
import com.comercio.app.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {


    @Autowired
    private UsuarioService service;

    @GetMapping
    public ResponseEntity<?> list(){
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<?>  create(@Valid @RequestBody Usuario u, BindingResult result){
        return service.save(u, result);
    }
}
