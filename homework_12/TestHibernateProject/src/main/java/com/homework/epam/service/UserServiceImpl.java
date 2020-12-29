package com.homework.epam.service;

import com.homework.epam.dao.UserDao;
import com.homework.epam.dao.UserResultDao;
import com.homework.epam.dto.UserDto;
import com.homework.epam.entity.User;
import com.homework.epam.entity.UserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserResultDao userResultDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserAssembler userAssembler;

    @Override
    public UserDto create(UserDto dto) {
        User user = userAssembler.assemble(dto);
        System.out.println(user);
        userDao.save(user);
        UserDto result = userAssembler.assemble(user);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto get(Integer userId) {
        User user = userDao.get(userId);
        return userAssembler.assemble(user);
    }

    @Override
    public UserDto update(UserDto dto) {
        User entity = userDao.get(dto.getId());
        User updated = userAssembler.assemble(dto);
        performUpdate(entity, updated);
        return userAssembler.assemble(entity);
    }

    private void performUpdate(User persistentEntity, User newEntity) {
        persistentEntity.setEmail(newEntity.getEmail());
        persistentEntity.setPassword(newEntity.getPassword());
        persistentEntity.setIdn(newEntity.getIdn());
        persistentEntity.setUserRoleId(newEntity.getUserRoleId());
        persistentEntity.setBlocked(newEntity.isBlocked());
        persistentEntity.setUserResults(newEntity.getUserResults());
    }


    @Override
    @Transactional
    public void delete(Integer userId) {
        userResultDao.delete(userId);
        userDao.delete(userId);
    }
}
