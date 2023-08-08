package com.d_development.todoList.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    @NotNull(message = "The name cannot be null")
    @Size(message = "The number of characters is incorrect", min = 2, max = 15)
    @Column(unique = true, nullable = false, length = 15)
    private String name;

    @NotNull(message = "It have that exist a password")
    @Size(message = "The number of characters is incorrect", min = 10, max = 15)
    @Pattern(regexp = "[A-Za-z0-9]+", message = "The password does not meet the requirements")
    @Column(unique = true, nullable = false)
    private String password;

    private String imageName;

    private boolean enabled = true;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "roles_id"})
    )
    private List<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Tag> tags;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Priority priorities;
}
