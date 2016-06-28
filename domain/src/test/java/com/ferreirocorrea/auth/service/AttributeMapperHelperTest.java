package com.ferreirocorrea.auth.service;

import static org.junit.Assert.assertNull;

import javax.naming.NamingException;

import org.junit.Test;

public class AttributeMapperHelperTest {

	@Test(expected = NullPointerException.class)
	public void nullPointer() throws NamingException {
		AttributeMapperHelper helper = new AttributeMapperHelper(null);
		helper.get(null);
	}
	
	@Test
	public void getAttributes() throws NamingException {
		AttributeMapperHelper helper = new AttributeMapperHelper(null);
		
		assertNull(helper.getAttributes());
	}
}
