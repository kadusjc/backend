package com.ferreirocorrea.customer.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferreirocorrea.WebApplication;
import com.ferreirocorrea.customer.Customer;
import com.ferreirocorrea.customer.service.CustomerService;
import com.ferreirocorrea.rest.CustomerController;

/**
 * Integration test for RESTful Customer Endpoint Created by carlos.correa
 * 20/09/2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebApplication.class)
@WebAppConfiguration
@IntegrationTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerControllerIntegrationTest {

	@Autowired
	private CustomerController customerController;

	@Autowired
	private CustomerService customerService;

	private final String endPoint = "/api/customer";

	private MockMvc mockMvc;

	private ObjectMapper jsonMapper;

	private Customer customer;

	private static Long CUSTOMER_SAVED_ID;

	@Before
	public void setup() {

		this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
		this.jsonMapper = new ObjectMapper();
		customer = new Customer("Carlos Eduardo Ferreiro Correa");
	}

	@Test
	public void test1SaveInvalidCustomer() throws Exception {
		StringWriter customerJson = new StringWriter();
		jsonMapper.writeValue(customerJson, customer);
		mockMvc
				.perform(
						MockMvcRequestBuilders.post(endPoint)
								.header("Content-Type", "application/json")
								.header("login", "carlos.correa")
								.content(customerJson.toString().getBytes())
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void test2SaveValidCustomer() throws Exception {

		customer.setBirthDay(new Date());
		StringWriter customerJson = new StringWriter();
		jsonMapper.writeValue(customerJson, customer);

		MvcResult mvcResult =  mockMvc
				.perform(
						MockMvcRequestBuilders.post(endPoint)
								.header("Content-Type", "application/json")
								.header("login", "carlos.correa")
								.content(customerJson.toString().getBytes())
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		Customer customerSaved = jsonMapper.readValue(mvcResult.getResponse().getContentAsString(), Customer.class);
		CUSTOMER_SAVED_ID = customerSaved.getId();

		assertEquals(customerSaved.getName(), this.customer.getName());
		assertEquals(customerSaved.getBirthDay(), this.customer.getBirthDay());
		assertTrue(customerSaved.getId() > 0);
	}

	@Test
	public void test3GetAll() throws Exception {

		MvcResult mvcResult = mockMvc
				.perform(
						MockMvcRequestBuilders.get(endPoint)
								.header("Content-Type", "application/json")
								.header("login", "carlos.correa")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String responseContent = mvcResult.getResponse().getContentAsString();
		@SuppressWarnings("unchecked")
		List<Customer> customers = (List<Customer>) jsonMapper.readValue(responseContent, List.class);
		assertEquals(customers.size(), 1);
	}

	@Test
	public void test4FindById() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(
						MockMvcRequestBuilders.get(endPoint+"/{id}", CUSTOMER_SAVED_ID)
								.header("Content-Type", "application/json")
								.header("login", "carlos.correa")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String responseContent = mvcResult.getResponse().getContentAsString();
		Customer customerFound = jsonMapper.readValue(responseContent, Customer.class);
		assertEquals(customerFound.getName(), this.customer.getName());
		assertNotNull(customerFound.getBirthDay());
		assertTrue(customerFound.getId() > 0);
	}

	@Test
	public void test5UpdateCustomer() throws Exception {

		Date birthDay = new Date();
		customer.setName("Carlos E. F. Correa");
		customer.setBirthDay(birthDay);
		customer.setId(CUSTOMER_SAVED_ID);

		StringWriter customerJson = new StringWriter();
		jsonMapper.writeValue(customerJson, customer);

		MvcResult mvcResult =  mockMvc
				.perform(
						MockMvcRequestBuilders.put(endPoint)
								.header("Content-Type", "application/json")
								.header("login", "carlos.correa")
								.header("encoding", "ISO-8859-1")
								.content(customerJson.toString().getBytes())
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();


		String responseContent = mvcResult.getResponse().getContentAsString();
		Customer customerUpdated = jsonMapper.readValue(responseContent, Customer.class);
		assertEquals(customerUpdated.getName(), "Carlos E. F. Correa");
		assertEquals(customerUpdated.getBirthDay(), birthDay);
		assertTrue(customerUpdated.getId() > 0);
	}

	@Test
	public void test6DeleteCustomer() throws Exception {


		StringWriter customerJson = new StringWriter();
		jsonMapper.writeValue(customerJson, customer);

		mockMvc
				.perform(
						MockMvcRequestBuilders.delete(endPoint+"/{id}", CUSTOMER_SAVED_ID)
								.header("Content-Type", "application/json")
								.header("login", "carlos.correa")
								.content(customerJson.toString().getBytes())
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}
}
