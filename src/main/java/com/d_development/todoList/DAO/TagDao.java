package com.d_development.todoList.DAO;

import com.d_development.todoList.Entity.Priority;
import com.d_development.todoList.Entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagDao extends PagingAndSortingRepository<Tag, Long>, CrudRepository<Tag, Long> {
}
