package com.epam.demo.controller;

import com.epam.demo.model.User;
import com.epam.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    @ResponseBody
    @RequestMapping("/")
    public String home() {
        String html = "";
        html += "<ul>";
        html += " <li><a href='/getUsers'>Get users</a></li>";
        html += " <li><a href='/getUserById?userId=1'>Get user with id 1</a></li>";
        html += " <li><a href='http://localhost:8090/actuator'>Actuator</a></li>";

        return html;
    }

    @ResponseBody
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public String getAllUsers() {
        List<User> list = userRepository.findAllByUserRoleId(1);
        return "List of users: " + list;
    }

    @ResponseBody
    @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
    public String getUserById(@RequestParam(name = "userId") int id) {
        User user = userRepository.findUserById(id);
        return "User: " + user;
    }
}
