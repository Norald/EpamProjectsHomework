package com.homework.epam.dao;

import com.homework.epam.entity.User;

import java.util.List;

public interface UserDao {
    User create(String email, Long idn, boolean blocked, Integer userRoleId, String password);
    void save(User user);
    User get(Integer userId);
    User get(String password);
    void delete(Integer id);
    List<User> getAll();
}
