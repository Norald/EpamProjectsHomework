package com.epam.demo.repo;

import com.epam.demo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserById(int id);

    List<User> findAllByUserRoleId(int userRoleId);
}
