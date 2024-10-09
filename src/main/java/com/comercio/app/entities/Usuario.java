package com.comercio.app.entities;

import com.comercio.app.utils.validations.ExistByUsername;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
@Entity
public class Usuario  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, max = 30, message = "El nombre debe tener entre 6 y 20 caracteres")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    //@ExistByUsername
    @Column(unique = true)
    @Size(min = 6, max = 20, message = "El nombre de usuario debe tener entre 6 y 20 caracteres")
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@JsonIgnore
    @NotBlank(message = "La contraseña no puede estar vacío")
    private String password;

    @Column(unique = true)
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un email válido")
    private String email;

    private LocalDateTime creado;

    private Boolean enabled;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    @JsonIgnoreProperties({"usuarios", "handler", "hibernateLazyInitializer"})
    //@JsonIgnoreProperties("usuarios")
    @ManyToMany
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id" , "role_id"})}
    )
    private List<Role> roles;

    @PrePersist
    public void setDates(){
        this.creado = LocalDateTime.now();
        this.ultimaActualizacion = LocalDateTime.now();
        this.enabled = Boolean.TRUE;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "ultimaActualizacion=" + ultimaActualizacion +
                ", enabled=" + enabled +
                ", creado=" + creado +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", nombre='" + nombre + '\'' +
                ", id=" + id +
                '}';
    }
}
