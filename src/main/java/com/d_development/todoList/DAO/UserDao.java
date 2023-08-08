package com.d_development.todoList.DAO;

import com.d_development.todoList.Entity.Priority;
import com.d_development.todoList.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserDao extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {
    List<User> findUsersByNameContains(String name);
    List<User> findUsersByNameLike(String name);
}
