package com.homework.epam.MongoTestApplication.controller;

import com.homework.epam.MongoTestApplication.model.Address;
import com.homework.epam.MongoTestApplication.model.Customer;
import com.homework.epam.MongoTestApplication.repo.CustomerRepository;
import com.homework.epam.MongoTestApplication.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    CustomerService service;

    @GetMapping(value = "/customer/byId/{customerId}")
    public Customer getUserById(@PathVariable long customerId) {
        Customer customer = service.findCustomerById(customerId);
        return customer;
    }

    @GetMapping(value = "/customer/byFirstAndLastName")
    public Customer getUserByFirstNameAndLastName(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        Customer customer = service.findCustomerByFirstNameAndLastName(firstName, lastName);
        return customer;
    }

    @PostMapping(value = "/customer/byAddress")
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getUserByAddress(@RequestBody Address address) {
        List<Customer> customerList = service.findCustomerByAddress(address);
        return customerList;
    }

    @GetMapping(value = "/customer/byCardNumber")
    public List<Customer> getUserByCardNumber(@RequestParam(name = "cardNumber") int cardNumber) {
        List<Customer> customerList = service.findCustomerByCardNumber(cardNumber);
        return customerList;
    }

    @GetMapping(value = "/customer/byExpiredCard")
    public List<Customer> getUserByExpiredCard() {
        List<Customer> customerList = service.findCustomerByExpiredCards();
        return customerList;
    }

    @PostMapping(value = "/addCustomer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getUserById(@RequestBody Customer customer) {
        service.insertCustomer(customer);
        return ResponseEntity.ok().body("{\"status\":\"ok\"}");
    }

    @PostMapping(value = "/updateCustomer")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateUserById(@RequestBody Customer customer) {
        service.insertCustomer(customer);
        return ResponseEntity.ok().body("{\"status\":\"ok\"}");
    }
}
