package com.d_development.todoList.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rols")
public class Rol implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "The role cannot be empty")
    @NotNull(message = "No puede ser nulo el rol")
    @Column(nullable = false, unique = true, length = 20)
    private String rol;

    public Rol(String rol) {
        this.rol = rol;
    }
}
