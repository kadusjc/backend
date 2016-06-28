package com.ferreirocorrea.customer.rest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ferreirocorrea.customer.Customer;
import com.ferreirocorrea.customer.repository.CustomerRepository;
import com.ferreirocorrea.customer.service.CustomerService;
import com.ferreirocorrea.rest.CustomerController;

/**
 * SpringMVC Mocked test for RESTful Customer Endpoint Created by carlos.correa
 * 20/09/2015
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

	@InjectMocks
	private CustomerController customerController;

	@Mock
	private CustomerService customerService;

	@Mock
	private CustomerRepository customerRepository;

	private final String endPoint = "/api/customer";

	private MockMvc mockMvc;

	private ObjectMapper jsonMapper;

	private Customer customer;

	private Customer[] customers;

	@Before
	public void setup() {

		this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
		this.jsonMapper = new ObjectMapper();

		customers = new Customer[] { new Customer("Kadu"), new Customer("Guilherme"), new Customer("Cauã") };
		customer = new Customer("Carlos Eduardo Ferreiro Correa");

		Mockito.when(customerService.getAll()).thenReturn(customerRepository);
	}

	@Test
	public void getAllTest() throws Exception {

		Mockito.when(customerRepository.findAll()).thenReturn(Arrays.asList(customers));

		StringWriter customerJson = new StringWriter();
		jsonMapper.writeValue(customerJson, customer);

		Writer responseJson = new StringWriter();
		jsonMapper.writeValue(responseJson, Arrays.asList(customers));

		MvcResult mvcResult = mockMvc
				.perform(
						MockMvcRequestBuilders.get(endPoint)
								.header("Content-Type", "application/json")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String responseContent = mvcResult.getResponse().getContentAsString();
		assertEquals(responseContent, responseJson.toString());

	}

	@Test
	public void getOneTest() throws Exception {

		Mockito.when(customerRepository.findOne(Mockito.anyLong())).thenReturn(customer);

		Writer responseJson = new StringWriter();
		jsonMapper.writeValue(responseJson, customer);

		MvcResult mvcResult = mockMvc
				.perform(
						MockMvcRequestBuilders.get(endPoint + "/{id}", 1l)
								.header("Content-Type", "application/json")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String responseContent = mvcResult.getResponse().getContentAsString();
		assertEquals(responseContent, responseJson.toString());

	}


	@Test
	public void saveInvalidCustomerTest() throws Exception {
		StringWriter customerJson = new StringWriter();
		jsonMapper.writeValue(customerJson, customer);

		mockMvc
				.perform(
						MockMvcRequestBuilders.post(endPoint)
								.header("Content-Type", "application/json")
								.content(customerJson.toString().getBytes())
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void saveValidCustomerTest() throws Exception {

		customer.setBirthDay(new Date());
		Mockito.when(customerRepository.save(customer)).thenReturn(customer);

		StringWriter customerJson = new StringWriter();
		jsonMapper.writeValue(customerJson, customer);

		MvcResult mvcResult =  mockMvc
				.perform(
						MockMvcRequestBuilders.post(endPoint)
								.header("Content-Type", "application/json")
								.content(customerJson.toString().getBytes())
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String responseContent = mvcResult.getResponse().getContentAsString();
		assertEquals(responseContent, customerJson.toString());
	}


	@Test
	public void updateInvalidCustomerTest() throws Exception {
		StringWriter customerJson = new StringWriter();
		jsonMapper.writeValue(customerJson, customer);

		mockMvc
				.perform(
						MockMvcRequestBuilders.put(endPoint)
								.header("Content-Type", "application/json")
								.content(customerJson.toString().getBytes())
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void updateValidCustomerTest() throws Exception {

		customer.setBirthDay(new Date());
		Mockito.when(customerService.update(customer)).thenReturn(customer);

		StringWriter customerJson = new StringWriter();
		jsonMapper.writeValue(customerJson, customer);

		MvcResult mvcResult =  mockMvc
				.perform(
						MockMvcRequestBuilders.put(endPoint)
								.header("Content-Type", "application/json")
								.content(customerJson.toString().getBytes())
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String responseContent = mvcResult.getResponse().getContentAsString();
		assertEquals(responseContent, customerJson.toString());
	}

	@Test
	public void deleteByPK() throws Exception {

		Writer responseJson = new StringWriter();
		jsonMapper.writeValue(responseJson, customer);

		 mockMvc
				.perform(
						MockMvcRequestBuilders.delete(endPoint + "/{id}", 1l)
								.header("Content-Type", "application/json")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

}
