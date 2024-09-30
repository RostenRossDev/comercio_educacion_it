package com.comercio.app.repositories;

import com.comercio.app.entities.Producto;
import com.comercio.app.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

}
