package com.homework.epam.MongoTestApplication.service;

import com.homework.epam.MongoTestApplication.model.Account;
import com.homework.epam.MongoTestApplication.model.Address;
import com.homework.epam.MongoTestApplication.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer findById(Long id);
    Customer findByFirstNameAndLastName(String firstName, String lastName);
    long insertCustomer(Customer customer);
    long updateCustomer(String id, String firstName, String lastName, List<Address> addressesList, List<Account> accountsList);
    List<Customer> findByAddress(Address address);
    List<Customer> findByCardNumber(int cardNumber);
    List<Customer> findByExpiredCards();
}
