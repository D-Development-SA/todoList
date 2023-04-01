package com.d_development.todoList.Services.Implements;

import com.d_development.todoList.DAO.PriorityDao;
import com.d_development.todoList.Entity.Priority;
import com.d_development.todoList.Services.Contract.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriorityImpl extends GenericImpl<Priority, PriorityDao> implements PriorityService {
    @Autowired
    public PriorityImpl(PriorityDao dao) {
        super(dao);
    }
}
