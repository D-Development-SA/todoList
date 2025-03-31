package com.d_development.todoList.Services.Implements;

import com.d_development.todoList.Controller.Exception.GeneralExceptionAndControllerAdvice.ListEmptyException;
import com.d_development.todoList.DAO.UserDao;
import com.d_development.todoList.Entity.User;
import com.d_development.todoList.Services.Contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserImpl extends GenericImpl<User, UserDao> implements UserService, UserDetailsService {
    private static UserDao userDao;

    @Autowired
    public UserImpl(UserDao dao) {
        super(dao);
        userDao = dao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findUsersByNameContains(String name) {
        List<User> users = dao.findUsersByNameContains(name);
        validList(users);
        return users;
    }

    @Transactional(readOnly = true)
    public static User getUser(String name){
        List<User> users = userDao.findUsersByNameLike(name);
        validList(users);
        return users.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUser(username);

        if(user == null)
            throw new UsernameNotFoundException("Error login: Not exist user whit name '"+username+"' in the system");

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getRole()))
                .peek(authority -> System.out.println("Role: " + authority.getAuthority()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), user.isEnabled(),
                true, true, true, authorities);
    }

    private static void validList(List<User> users) {
        if(users.isEmpty()) throw new ListEmptyException("users", "Search all users");
    }
}
