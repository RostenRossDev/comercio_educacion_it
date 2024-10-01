package com.comercio.app.controllers;

import com.comercio.app.entities.Producto;
import com.comercio.app.services.impl.ProductoServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Log4j2
@RequestMapping("/producto")
@RestController
public class ProductoCotroller {

    @Autowired
    private ProductoServiceImpl productoService;


    @RequestMapping("/buscar_por_id/{idProcuto}")
    public ResponseEntity<?> buscarProductoPorIdConPathVariable(@PathVariable(name = "idProcuto") Long id){
        Producto producto = productoService.buscarPorId(id);

        //ResponseEntity response = new ResponseEntity(producto, HttpStatus.OK);
        //new ResponseEntity(producto, HttpStatus.OK);
        return ResponseEntity.ok().body(producto);
    }

    @RequestMapping("/buscar_por_id")
    public ResponseEntity<?> buscarProductoPorIdConParametros(@RequestParam(name = "producto", required = false) Long id){
        if (id != null) {
            Producto producto = productoService.buscarPorId(id);
            return ResponseEntity.ok().body(producto);
        }
        return  ResponseEntity.internalServerError().body("Sucedio un error inesperado");
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto){
        log.info("producto recivido: " + producto);
        Map<String, Object> response = new HashMap<>();
        Producto newProducto = productoService.guardarActualizar(producto);
        if(newProducto != null ){
            response.put("producto", producto);
            response.put("message", "Se guardo el proxuto con exito.");

            return  ResponseEntity.ok().body(response);
        }

        return  ResponseEntity.internalServerError().body("Sucedio un error inesperado al intetnar guardar el producto");
    }
}
