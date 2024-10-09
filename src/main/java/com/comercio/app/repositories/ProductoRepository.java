package com.comercio.app.repositories;

import com.comercio.app.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Producto findByNombreProducto(String nombreProducto);  //respetar orden de los parametros en el que aparecen en al consulta
    void deleteByNombreProducto(String nombreProducto);

   // @Query("select p from Producto p where p.stock > 20 and p.descuento > 19 and p.nombreProducto like %?1%")   //no importa el orden de los parametros
    //List<Producto> busquedaComplejaPorDescuentoStockYPrecio1(String nombreProducto, String nombre2); //El segundo parametro no se usa, esta solo de meustra.

    //@Query("select p from Producto p where p.stock > 0 and p.descuento > 0 and  p.nombreProducto like CONCAT('%', :nombreProducto, '%')")  //no importa el orden de los parametros
    //@Query("select p from Producto p where p.stock > 0 and p.descuento > 0 and  p.nombreProducto like %:nombreProducto%")  //no importa el orden de los parametros
   // List<Producto> busquedaComplejaPorDescuentoStockYPrecio2(@Param("nombreProducto") String nombreProducto);
}