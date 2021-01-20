package com.homework.epam.MongoTestApplication;

import com.homework.epam.MongoTestApplication.model.Account;
import com.homework.epam.MongoTestApplication.model.Address;
import com.homework.epam.MongoTestApplication.model.Customer;
import com.homework.epam.MongoTestApplication.repo.CustomerRepository;
import com.homework.epam.MongoTestApplication.service.CustomerService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Date;
import java.util.List;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MongoTestApplicationTests {

	@Autowired
	private CustomerRepository repo;

	@Autowired
	private CustomerService service;

	@Test
	public void addCustomerTest(){
		Customer customer = new Customer();
		customer.setId(121l);
		customer.setFirstName("121");
		customer.setLastName("121");
		service.insertCustomer(customer);

		Customer customerFromBd = repo.findCustomerById(121l);
		Assert.assertEquals(customer.getFirstName(), customerFromBd.getFirstName());
	}

	@Test
	public void getCustomerByNamesTest(){
		Customer customer = new Customer();
		customer.setId(122l);
		customer.setFirstName("firstname");
		customer.setLastName("lastname");
		service.insertCustomer(customer);

		Customer customerFromBd = service.findCustomerByFirstNameAndLastName("firstname","lastname");
		Assert.assertEquals(customer.getFirstName(), customerFromBd.getFirstName());
	}


	@Test
	public void getCustomerByAddressTest(){
		Customer customer = new Customer();
		customer.setId(123l);
		customer.setFirstName("firstname");
		customer.setLastName("lastname");

		Address address = new Address();
		address.setLine1("line1");
		address.setLine2("line2");
		address.setCountryCode(49000);

		customer.addAddress(address);

		service.insertCustomer(customer);

		List<Customer> customerDBList = service.findCustomerByAddress(address);
		Assert.assertEquals(customer.getFirstName(), customerDBList.get(0).getFirstName());
	}

	@Test
	public void getCustomerByCardTest(){
		Customer customer = new Customer();
		customer.setId(124l);
		customer.setFirstName("1234");
		customer.setLastName("lastname");

		Account account = new Account();
		account.setNameOnAccount("1234");
		account.setCardNumber(112233);
		account.setExpirationDate(Date.from(Instant.now()));

		customer.addAccount(account);

		service.insertCustomer(customer);

		List<Customer> customerDBList = service.findCustomerByCardNumber(112233);
		Assert.assertEquals(customer.getFirstName(), customerDBList.get(0).getFirstName());
	}

	@Test
	public void getCustomerByExpiredCardTest(){
		Customer customer = new Customer();
		customer.setId(125l);
		customer.setFirstName("125");
		customer.setLastName("lastname");

		Account account = new Account();
		account.setNameOnAccount("125");
		account.setCardNumber(445566);
		account.setExpirationDate(Date.from(Instant.now()));

		customer.addAccount(account);

		service.insertCustomer(customer);

		List<Customer> customerDBList = service.findCustomerByExpiredCards();
		Assert.assertEquals(customer.getFirstName(), customerDBList.get(0).getFirstName());
	}
}
