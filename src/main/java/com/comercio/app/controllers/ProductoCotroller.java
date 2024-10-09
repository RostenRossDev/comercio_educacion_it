package com.comercio.app.controllers;

import com.comercio.app.entities.Producto;
import com.comercio.app.repositories.ProductoRepository;
import com.comercio.app.services.ProductoService;
import com.comercio.app.services.impl.ProductoServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Log4j2
@RequestMapping("/producto")
@RestController
public class ProductoCotroller {

    @Autowired
    private ProductoService productoService;



    @GetMapping("/buscar_por_id/{idProcuto}")
    public ResponseEntity<?> buscarProductoPorIdConPathVariable(@PathVariable(name = "idProcuto") Long id){
        Producto producto = productoService.buscarPorId(id);

        return ResponseEntity.ok().body(producto);
    }

    @GetMapping("/buscar_por_id")
    public ResponseEntity<?> buscarProductoPorIdConParametros(@RequestParam(name = "producto", required = false) Long id){
        if (id != null) {
            Producto producto = productoService.buscarPorId(id);
            return ResponseEntity.ok().body(producto);
        }
        return  ResponseEntity.internalServerError().body("Sucedio un error inesperado");
    }

    @PostMapping("")
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto){
        log.info("producto recivido: " + producto);
        Map<String, Object> response = new HashMap<>();
        Producto newProducto = productoService.guardarActualizar(producto);
        if(newProducto != null ){
            response.put("producto", producto);
            response.put("message", "Se guardo el producto con exito.");

            return  ResponseEntity.ok().body(response);
        }

        return  ResponseEntity.internalServerError().body("Sucedio un error inesperado al intetnar guardar el producto");
    }

    @PutMapping("")
    public ResponseEntity<?>actualizarProducto(@RequestBody Producto producto){
        log.info("producto recivido: " + producto);
        Map<String, Object> response = new HashMap<>();
        Producto updateProducto = productoService.guardarActualizar(producto);
        if(updateProducto != null ){
            response.put("producto", producto);
            response.put("message", "Se actualizo el producto con exito.");

            return  ResponseEntity.ok().body(response);
        }

        return  ResponseEntity.internalServerError().body("Sucedio un error inesperado al intetnar actualizar el producto");
    }

    @GetMapping("/buscarCompleja1")
    public ResponseEntity<?> buscarCompleja1(@RequestParam(name = "nombre") String nombre, @RequestParam(name = "nombre2") String nombre2){

        List<Producto> producto = productoService.busquedaCompleja1(nombre, nombre2);
        return ResponseEntity.ok().body(producto);
    }

    @GetMapping("/buscarCompleja2")
    public ResponseEntity<?> buscarCompleja2(@RequestParam(name = "nombre") String nombre){

        List<Producto> producto = productoService.busquedaCompleja2(nombre);
        return ResponseEntity.ok().body(producto);
    }
}
