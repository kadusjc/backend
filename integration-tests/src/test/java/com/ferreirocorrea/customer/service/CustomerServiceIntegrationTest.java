package com.ferreirocorrea.customer.service;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ferreirocorrea.WebApplication;
import com.ferreirocorrea.customer.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebApplication.class)
@WebAppConfiguration
@IntegrationTest
public class CustomerServiceIntegrationTest {

	@Autowired
	private CustomerService customerService;


	@Before
	public void setUp() throws Exception {
		assertTrue(customerService != null);
		assertTrue(customerService.getAll() != null);
	}

	@Test
	public void crudTest(){
		Customer customer = save();
		customer = findOne(customer);
		
		Customer anotherCustomer = save();
		anotherCustomer = findOne(anotherCustomer);
		
		findAll(2);
		
		deleteCustomer(customer);
		
	}
	
	private void deleteCustomer(Customer customer){
		customerService.delete(customer.getId());
		List<Customer> customers = findAll(1);
	}
	

	private List<Customer> findAll(int expectedSizeOfAssertion) {
		List<Customer> customers = customerService.getAll().findAll();
		assertTrue(customers.size() == expectedSizeOfAssertion);
		return customers;
	}

	private Customer findOne(Customer customer) {
		customer = customerService.getAll().findOne(customer.getId());
		assertTrue(customer.getId()!=null);
		return customer;
	}

	private Customer save() {
		Customer customer = new Customer("Customer");
		customer.setBirthDay(new Date());
		customer = customerService.getAll().save(customer);
		assertTrue(customer.getId()!=null);
		return customer;
	}
	
		
	
	@After
	public void tearDown() {
	}

}
