package com.d_development.todoList.Services.Implements;

import com.d_development.todoList.DAO.TaskDao;
import com.d_development.todoList.Entity.Task;
import com.d_development.todoList.Services.Contract.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskImpl extends GenericImpl<Task, TaskDao> implements TaskService {

    @Autowired
    public TaskImpl(TaskDao dao) {
        super(dao);
    }
}
