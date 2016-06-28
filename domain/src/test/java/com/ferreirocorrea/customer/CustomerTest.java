package com.ferreirocorrea.customer;

import static com.ferreirocorrea.BeanValidatorFactory.newLocalValidatorFactoryBean;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.ferreirocorrea.customer.builder.CustomerBuilder;

public class CustomerTest {

	private LocalValidatorFactoryBean validator;

	public CustomerTest() {
		validator = newLocalValidatorFactoryBean();
	}


	@Test
	public void whenAllRequiredFieldsFilledNoShowValidationViolation() {
		Customer customer = new CustomerBuilder()
										.withName("Carlos E. F. Corrêa")//NotNull
										.withBirthDay(new Date())//NotNull
										.build();

		Set<ConstraintViolation<Customer>> errors = this.validator.validate(customer);
		assertTrue("Foram encontrados erros ", errors.isEmpty());
	}

	@Test
	public void whenAllRequiredFieldsMissingShowValidationViolation() {
		Customer customer = new CustomerBuilder().build();

		Set<ConstraintViolation<Customer>> errors = this.validator.validate(customer);
		assertTrue("Foram encontrados erros ", 2 == errors.size());
	}

}
