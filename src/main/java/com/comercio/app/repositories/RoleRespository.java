package com.comercio.app.repositories;

import com.comercio.app.entities.Role;
import com.comercio.app.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRespository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
