package com.d_development.todoList.Services.Implements;

import com.d_development.todoList.Controller.Exception.BDExcepcion.NotExistException;
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
    protected final D dao;

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
    @Transactional(readOnly = true)
    public List<E> findAll() {
        return (List<E>) dao.findAll();
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
        if(id == 0) throw new NotExistException(id, String.valueOf(id));

        E e = dao.findById(id).orElse(null);

        if(e == null) throw new NotExistException(id, String.valueOf(id));

        return e;
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
    public void deleteAll(List<E> entities) {
        dao.deleteAll(entities);
    }

    @Override
    @Transactional
    public void deleteAllByIds(List<Long> ids) {
        dao.deleteAllById(ids);
    }
}
