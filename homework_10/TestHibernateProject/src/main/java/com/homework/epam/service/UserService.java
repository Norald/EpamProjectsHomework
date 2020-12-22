package com.homework.epam.service;

import com.homework.epam.dto.UserDto;

public interface UserService {
    UserDto create(UserDto dto);
    UserDto get(Integer userId);
    UserDto update(UserDto dto);
    void delete(Integer userId);
}
