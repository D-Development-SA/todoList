package com.d_development.todoList.Services.Implements;

import com.d_development.todoList.Services.Contract.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class GenericImpl<E, D extends PagingAndSortingRepository<E, Long> & CrudRepository<E, Long>> implements GenericService<E> {
    @Autowired
    private final D dao;

    public GenericImpl(D dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<E> findAll(Pageable pageable) {
        return dao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<E> findAll(Sort sort) {
        return (List<E>) dao.findAll(sort);
    }

    @Override
    @Transactional
    public E save(E entity) {
        return dao.save(entity);
    }

    @Override
    @Transactional
    public List<E> saveAll(List<E> entity) {
        return (List<E>) dao.saveAll(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public E findById(long id) {
        return dao.findById(id).get();
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        dao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        dao.deleteAll();
    }

    @Override
    @Transactional
    public void deleteAll(List<E> entitys) {
        dao.deleteAll(entitys);
    }
}
