package com.d_development.todoList.Services.Implements;

import com.d_development.todoList.DAO.RoleDao;
import com.d_development.todoList.Entity.Role;
import com.d_development.todoList.Services.Contract.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleImpl extends GenericImpl<Role, RoleDao> implements RoleService {
    @Autowired
    public RoleImpl(RoleDao dao) {
        super(dao);
    }
}
