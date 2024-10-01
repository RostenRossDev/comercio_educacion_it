package com.comercio.app.repositories;

import com.comercio.app.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Producto findByNombreProducto(String nombreProducto);
    void deleteByNombreProducto(String nombreProducto);
}
