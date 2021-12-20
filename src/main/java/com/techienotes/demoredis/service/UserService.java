package com.techienotes.demoredis.service;

import com.techienotes.demoredis.model.User;

import java.util.List;

public interface UserService {
    User getUser(long id);

    List<User> getAll();

    void delete(long id);

    User update(User user);

    User create(User user);
}
