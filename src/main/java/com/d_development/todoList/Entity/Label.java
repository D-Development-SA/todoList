package com.d_development.todoList.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "labels")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @NotNull(message = "The name cannot be null")
    @Size(message = "The number of characters is incorrect", min = 2, max = 15)
    @Column(unique = true, nullable = false, length = 15)
    private String nameLabel;

    @Pattern(
            regexp = "(rgb\\s\\([0-2]{1,3},[0-9]{1,3},[0-9]{1,3}\\)|^[A-Z][a-z0-9]*)?",
            message = "Is not a color"
    )
    private String color;

    @Pattern(
            regexp = "[A-Za-z]*",
            message = "Not exist an icon with that name"
    )
    private String iconName;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Task> tasks;
}
