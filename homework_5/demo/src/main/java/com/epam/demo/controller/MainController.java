package com.epam.demo.controller;

import com.epam.demo.model.User;
import com.epam.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/users")
    public List<User> getAllUsers() {
        List<User> list = userRepository.findAllByUserRoleId(1);
        return list;
    }

    @GetMapping(value = "/users/{userId}")
    public User getUserById(@PathVariable int userId) {
        User user = userRepository.findUserById(userId);
        return user;
    }
}
