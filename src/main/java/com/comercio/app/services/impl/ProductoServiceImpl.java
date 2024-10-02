package com.comercio.app.services.impl;

import com.comercio.app.entities.Producto;
import com.comercio.app.repositories.ProductoRepository;
import com.comercio.app.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto buscarPorNombre(String nombre) {
        return productoRepository.findByNombreProducto(nombre);
    }

    @Override
    public List<Producto> buscarTodos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto guardarActualizar(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void borrarPorId(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public void borrarPorNombre(String nombre) {
        productoRepository.deleteByNombreProducto(nombre);
    }

    @Override
    public List<Producto> busquedaCompleja1(String producto1, String producto2) {
        return productoRepository.busquedaComplejaPorDescuentoStockYPrecio1(producto1, producto2);
    }

    @Override
    public List<Producto> busquedaCompleja2(String producto1) {
        return productoRepository.busquedaComplejaPorDescuentoStockYPrecio2(producto1);
    }
}
