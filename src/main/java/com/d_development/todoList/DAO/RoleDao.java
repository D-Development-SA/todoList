package com.d_development.todoList.DAO;

import com.d_development.todoList.Entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleDao extends CrudRepository<Role, Long>, PagingAndSortingRepository<Role, Long> {
}
