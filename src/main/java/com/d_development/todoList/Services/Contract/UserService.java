package com.d_development.todoList.Services.Contract;

import com.d_development.todoList.Entity.User;

import java.util.List;

public interface UserService extends GenericService<User>{
    List<User> findUsersByNameContains(String name);
}
