package com.d_development.todoList.Security;

import lombok.Data;

import java.util.List;

@Data
public class AuthCredentials {
    private String name;
    private String password;
    private List<String> authorities;
}
