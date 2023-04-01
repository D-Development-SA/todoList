package com.d_development.todoList.Services.Implements;

import com.d_development.todoList.DAO.LabelDao;
import com.d_development.todoList.Entity.Label;
import com.d_development.todoList.Services.Contract.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelImpl extends GenericImpl<Label, LabelDao> implements LabelService {
    @Autowired
    public LabelImpl(LabelDao dao) {
        super(dao);
    }
}
