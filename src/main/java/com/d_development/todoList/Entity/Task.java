package com.d_development.todoList.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    private String title;

    @NotNull(message = "The content of the task cannot be null, insert something content")
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String dateTime;

    private boolean done;

    @PrePersist
    private void generateDateTime(){
        dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
    }

}
