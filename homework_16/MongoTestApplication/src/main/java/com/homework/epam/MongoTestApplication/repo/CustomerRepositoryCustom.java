package com.homework.epam.MongoTestApplication.repo;

import com.homework.epam.MongoTestApplication.model.Account;
import com.homework.epam.MongoTestApplication.model.Address;
import com.homework.epam.MongoTestApplication.model.Customer;
import java.util.List;

public interface CustomerRepositoryCustom {

    long insertCustomer(Customer customer);

    long updateCustomer(String id,String firstName, String lastName, List<Address> addressesList, List<Account> accountsList);

    List<Customer> findCustomerByAddress(Address address);

    List<Customer> findCustomerByCardNumber(int cardNumber);

    List<Customer> findCustomerByExpiredCards();
}
