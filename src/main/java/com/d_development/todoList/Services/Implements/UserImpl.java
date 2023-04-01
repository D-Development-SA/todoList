package com.d_development.todoList.Services.Implements;

import com.d_development.todoList.DAO.UserDao;
import com.d_development.todoList.Entity.User;
import com.d_development.todoList.Services.Contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserImpl extends GenericImpl<User, UserDao> implements UserService {
    @Autowired
    public UserImpl(UserDao dao) {
        super(dao);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUsersByNameContains(String name) {
        return dao.findUsersByNameContains(name);
    }
}
