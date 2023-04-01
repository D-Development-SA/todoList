package com.d_development.todoList.Services.Implements;

import com.d_development.todoList.DAO.UserDao;
import com.d_development.todoList.Entity.User;
import com.d_development.todoList.Services.Contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserImpl extends GenericImpl<User, UserDao> implements UserService {
    @Autowired
    public UserImpl(UserDao dao) {
        super(dao);
    }
}
