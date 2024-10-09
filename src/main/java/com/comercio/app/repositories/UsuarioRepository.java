package com.comercio.app.repositories;

import com.comercio.app.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM Usuario u WHERE u.username = ?1")
    Usuario findByUsername(String username);
}
