package com.comercio.app.services;

import com.comercio.app.entities.Producto;

import java.util.List;

public interface ProductoService {

    Producto buscarPorId(Long id);
    Producto buscarPorNombre(String nombre);
    List<Producto> buscarTodos();
    Producto guardarActualizar(Producto producto);
    void borrarPorId(Long id);
    void borrarPorNombre(String nombre);
}
