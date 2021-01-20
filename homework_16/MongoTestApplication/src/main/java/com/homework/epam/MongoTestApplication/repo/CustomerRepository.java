package com.homework.epam.MongoTestApplication.repo;

import com.homework.epam.MongoTestApplication.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, Long>, CustomerRepositoryCustom {
    Customer findCustomerById(Long id);
    Customer findCustomerByFirstNameAndLastName(String firstName, String lastName);
}
