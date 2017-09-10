package com.ddebbie.service;

import com.ddebbie.model.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);
}