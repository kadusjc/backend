package com.ferreirocorrea.customer.builder;

import java.util.Date;

import com.ferreirocorrea.customer.Customer;

public class CustomerBuilder {

	public final Customer customer = new Customer();
	
	public CustomerBuilder withId(Long id){
		this.customer.setId(id);
		return this;
	}
	
	public CustomerBuilder withName(String name){
		this.customer.setName(name);
		return this;
	}
	
	public CustomerBuilder withBirthDay(Date birthDay){
		this.customer.setBirthDay(birthDay);
		return this;
	}
	
	public Customer build(){
		return this.customer;
	}
	
}
