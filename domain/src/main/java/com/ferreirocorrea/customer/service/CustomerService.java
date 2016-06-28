package com.ferreirocorrea.customer.service;

import java.io.Serializable;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ferreirocorrea.customer.Customer;
import com.ferreirocorrea.customer.repository.CustomerRepository;

@Service
public class CustomerService implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5765171737080109266L;

	@Autowired
	private CustomerRepository customerRepository;
	
	public CustomerRepository getAll(){
		return customerRepository;
	}
	
	@Transactional
	public Customer update(Customer customerChanges){
		if(customerChanges == null || customerChanges.getId() == null) return null;
	
		Customer customerFromDataBase = customerRepository.findOne(customerChanges.getId());
		if(customerFromDataBase == null) return null;
		
		customerFromDataBase.setName(customerChanges.getName());
		customerFromDataBase.setBirthDay(customerChanges.getBirthDay());
		return customerRepository.save(customerFromDataBase);
	}
	
	@Transactional
	public void delete(Long customerId){
		Customer customer = customerRepository.findOne(customerId);
		if(customer == null) return;
		
		customerRepository.delete(customer);
	}

}
