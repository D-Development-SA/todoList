package com.d_development.todoList.Services.Implements;

import com.d_development.todoList.DAO.TagDao;
import com.d_development.todoList.Entity.Tag;
import com.d_development.todoList.Services.Contract.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagImpl extends GenericImpl<Tag, TagDao> implements TagService {
    @Autowired
    public TagImpl(TagDao dao) {
        super(dao);
    }
}
