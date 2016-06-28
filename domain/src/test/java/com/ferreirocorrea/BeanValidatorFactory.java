package com.ferreirocorrea;

import org.hibernate.validator.HibernateValidator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class BeanValidatorFactory {

	public static LocalValidatorFactoryBean newLocalValidatorFactoryBean() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setProviderClass(HibernateValidator.class);
		validator.afterPropertiesSet();
		return validator;

	}
}
