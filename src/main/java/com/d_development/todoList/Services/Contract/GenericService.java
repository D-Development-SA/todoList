package com.d_development.todoList.Services.Contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface GenericService <E>{
    Page<E> findAll(Pageable pageable);
    List<E> findAll(Sort sort);
    List<E> findAll();
    E save(E entity);
    List<E> saveAll(List<E> entity);
    E findById(long id);
    void deleteById(long id);
    void deleteAll();
    void deleteAll(List<E> entities);
    void deleteAllByIds(List<Long> ids);
}
