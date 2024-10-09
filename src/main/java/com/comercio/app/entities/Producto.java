package com.comercio.app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "productos")
@Entity
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @Column(name = "nombre_producto")
    private String nombreProducto;

    private Double precio;

    private Integer descuento;

    private Boolean vendido;

    @ManyToOne
    @JoinColumn(name ="proveedor_id")
    private Proveedor proveedor;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
         name = "producto_categoria",
         joinColumns = @JoinColumn(name = "producto_id"),
         inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;
}