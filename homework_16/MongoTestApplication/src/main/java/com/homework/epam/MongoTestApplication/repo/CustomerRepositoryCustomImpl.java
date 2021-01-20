package com.homework.epam.MongoTestApplication.repo;

import com.homework.epam.MongoTestApplication.model.Account;
import com.homework.epam.MongoTestApplication.model.Address;
import com.homework.epam.MongoTestApplication.model.Customer;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Repository
public class CustomerRepositoryCustomImpl implements CustomerRepositoryCustom{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public long insertCustomer(Customer customer) {
        Customer customerSaved = mongoTemplate.save(customer);
        return customerSaved.getId();
    }

    @Override
    public long updateCustomer(String id, String firstName, String lastName, List<Address> addressesList, List<Account> accountsList) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("firstName", firstName);
        update.set("lastName", lastName);
        update.set("addresses", addressesList);
        update.set("accounts", accountsList);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Customer.class);
        if (result != null) {
            return result.getModifiedCount();
        }
        return 0;
    }

    @Override
    public List<Customer> findCustomerByAddress(Address address) {
        List<Customer> resultCustomersList = mongoTemplate.find(Query.query(Criteria.where("addresses").in(address)), Customer.class);
        if(resultCustomersList.isEmpty()){
            return null;
        }else {
            return resultCustomersList;
        }
    }

    @Override
    public List<Customer> findCustomerByCardNumber(int cardNumber) {
        List<Customer> resultCustomersList = mongoTemplate.find(Query.query(Criteria.where("accounts.cardNumber").in(cardNumber)), Customer.class);
        if(resultCustomersList.isEmpty()){
            return null;
        }else {
            return resultCustomersList;
        }
    }

    @Override
    public List<Customer> findCustomerByExpiredCards() {
        List<Customer> resultCustomersList = mongoTemplate.find(Query.query(Criteria.where("accounts.expirationDate").lt(Date.from(Instant.now()))), Customer.class);
        if(resultCustomersList.isEmpty()){
            return null;
        }else {
            return resultCustomersList;
        }
    }
}
