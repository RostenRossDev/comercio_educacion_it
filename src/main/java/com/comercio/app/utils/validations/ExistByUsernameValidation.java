package com.comercio.app.utils.validations;

import com.comercio.app.entities.Usuario;
import com.comercio.app.repositories.UsuarioRepository;
import com.comercio.app.services.UsuarioService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExistByUsernameValidation implements ConstraintValidator<ExistByUsername, String> {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        return !repository.existsByUsername(s);
    }

    @PostConstruct
    public void init() {
        System.out.println("UsuarioService inyectado: " + (repository != null));
    }


}


