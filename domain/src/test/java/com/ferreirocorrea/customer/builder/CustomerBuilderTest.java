package com.ferreirocorrea.customer.builder;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.ferreirocorrea.customer.Customer;
import com.ferreirocorrea.customer.builder.CustomerBuilder;

public class CustomerBuilderTest {

	private CustomerBuilder customerBuilder;

	@Before
	public void setUp() {
		customerBuilder = new CustomerBuilder();
	}

	@Test
	public void buildCustomerEmpty(){
		Customer customer = customerBuilder.build();
		assertTrue(customer.getId() == null);
		assertTrue(customer.getName() == null);
		assertTrue(customer.getBirthDay() == null);
	}
	
	@Test
	public void buildCustomerFull(){
		
		Customer customer = customerBuilder
								.withId(7l)
									.withBirthDay(new Date())
										.withName("Name")
											.build();
		
		assertTrue(customer.getId().equals(7l));
		assertTrue(customer.getName().equals("Name"));
		assertTrue(customer.getBirthDay() != null);
	}
}
