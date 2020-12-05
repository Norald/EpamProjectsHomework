package com.epam.homework.repo;

import com.epam.homework.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByEmail(String email);
}
