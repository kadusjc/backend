package com.ferreirocorrea.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ferreirocorrea.customer.Customer;
import com.ferreirocorrea.customer.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Customer> getAll() {
		return customerService.getAll().findAll();
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public @ResponseBody Customer loadCustomer(@PathVariable @Valid Long id) {
		return customerService.getAll().findOne(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Customer save(@RequestBody @Valid Customer customer) {
		return customerService.getAll().save(customer);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public @ResponseBody Customer update(@RequestBody @Valid Customer customer) {
		return customerService.update(customer);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable @Valid Long id) {
		customerService.delete(id);
	}
	
	/*@ExceptionHandler(Exception.class) //For debug purposes
	public ModelAndView handleException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
		e.printStackTrace();
		return null;
	}*/
	
}
