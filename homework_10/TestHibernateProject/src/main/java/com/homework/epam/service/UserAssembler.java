package com.homework.epam.service;

import com.homework.epam.dto.UserDto;
import com.homework.epam.entity.User;

public interface UserAssembler {
    User assemble(UserDto dto);
    UserDto assemble (User entity);
}
