package com.ferreirocorrea.customer.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.runners.MockitoJUnitRunner;

import com.ferreirocorrea.customer.Customer;
import com.ferreirocorrea.customer.builder.CustomerBuilder;
import com.ferreirocorrea.customer.repository.CustomerRepository;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

	@InjectMocks
	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepository;

	@Before
	public void setUp() throws Exception {
		assertTrue(customerService != null);
		assertTrue(customerService.getAll() != null);
	}

	@Test
	public void updateNullTest() {
		Customer customer = customerService.update(null);
		assertNull(customer);

		customer = customerService.update(new Customer());

		assertNull(customer);
		Mockito.verify(customerRepository, VerificationModeFactory.noMoreInteractions()).save(Mockito.any(Customer.class));
	}

	@Test
	public void updateButDontFindInDatabaseTest() {
		Customer customerToUpdate = new CustomerBuilder()
		.withId(10l)
		.withName("João da Silva").build();

		Mockito.when(customerRepository.findOne(10l)).thenReturn(null);

		customerToUpdate = customerService.update(customerToUpdate);

		assertNull(customerToUpdate);
		Mockito.verify(customerRepository, VerificationModeFactory.times(0)).save(Mockito.any(Customer.class));
	}

	@Test
	public void updateTest() {
		Customer customerFromDataBase = new CustomerBuilder()
										.withId(10l)
										.withName("Jão da Silva").build();

		Customer customerToUpdate = new CustomerBuilder()
										.withId(10l)
										.withName("João da Silva").build();

		Mockito.when(customerRepository.findOne(10l)).thenReturn(customerFromDataBase);
		Mockito.when(customerRepository.save(customerToUpdate)).thenReturn(customerFromDataBase);

		customerService.update(customerToUpdate);
		assertTrue(customerFromDataBase.getName().equals("João da Silva"));
	}


	@Test
	public void deletingButDontFindInDatabaseTest() {
		Mockito.when(customerRepository.findOne(10l)).thenReturn(null);

		customerService.delete(10l);

		Mockito.verify(customerRepository, VerificationModeFactory.times(0)).delete(10L);
	}

	@Test
	public void deletingSuccessfullyTest() {
		Customer customerFound = new CustomerBuilder()
					.withId(10l)
						.withName("João da Silva").build();

		Mockito.when(customerRepository.findOne(10l)).thenReturn(customerFound);

		customerService.delete(10l);

		Mockito.verify(customerRepository, VerificationModeFactory.atMost(1)).delete(customerFound);
	}


	@After
	public void tearDown() {
	}

}
