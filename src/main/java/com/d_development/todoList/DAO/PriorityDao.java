package com.d_development.todoList.DAO;

import com.d_development.todoList.Entity.Priority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PriorityDao extends PagingAndSortingRepository<Priority, Long>, CrudRepository<Priority, Long> {
}
