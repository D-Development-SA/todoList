package com.d_development.todoList.DAO;

import com.d_development.todoList.Entity.Label;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LabelDao extends PagingAndSortingRepository<Label, Long>, CrudRepository<Label, Long> {
}
