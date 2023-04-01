package com.d_development.todoList.DAO;

import com.d_development.todoList.Entity.Priority;
import com.d_development.todoList.Entity.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaskDao extends PagingAndSortingRepository<Task, Long>, CrudRepository<Task, Long> {
}
