package com.homework.epam.MongoTestApplication.service;

import com.homework.epam.MongoTestApplication.model.Account;
import com.homework.epam.MongoTestApplication.model.Address;
import com.homework.epam.MongoTestApplication.model.Customer;
import com.homework.epam.MongoTestApplication.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository repository;

    @Override
    public Customer findById(Long id) {
        return repository.findCustomerById(id);
    }

    @Override
    public Customer findByFirstNameAndLastName(String firstName, String lastName) {
        return repository.findCustomerByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public long insertCustomer(Customer customer) {
        return repository.insertCustomer(customer);
    }

    @Override
    public long updateCustomer(String id, String firstName, String lastName, List<Address> addressesList, List<Account> accountsList) {
        return repository.updateCustomer(id, firstName, lastName, addressesList, accountsList);
    }

    @Override
    public List<Customer> findByAddress(Address address) {
        return repository.findCustomerByAddress(address);
    }

    @Override
    public List<Customer> findByCardNumber(int cardNumber) {
        return repository.findCustomerByCardNumber(cardNumber);
    }

    @Override
    public List<Customer> findByExpiredCards() {
        return repository.findCustomerByExpiredCards();
    }
}
