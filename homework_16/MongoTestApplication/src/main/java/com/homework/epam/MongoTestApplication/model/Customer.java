package com.homework.epam.MongoTestApplication.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "customers")
public class Customer {

    @Id
    private long id;

    private String firstName;

    private String lastName;

    @Field(value = "addresses")
    private List<Address> addresses = new ArrayList<>();

    @Field(value = "accounts")
    private List<Account> accounts = new ArrayList<>();

    public void addAddress(Address address){
        addresses.add(address);
    }

    public void addAccount(Account account){
        accounts.add(account);
    }

    public void deleteAddress(Address address){
        addresses.remove(address);
    }

    public void deleteAccount(Account account){
        accounts.remove(account);
    }

}
