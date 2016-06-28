package com.ferreirocorrea.auth.service;

import static org.junit.Assert.assertTrue;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.ferreirocorrea.auth.User;

/**
 * @author Carlos Eduardo F. Corrêa
 */

@RunWith(MockitoJUnitRunner.class)
public class LdapUserMapperTest {

	@InjectMocks
	private LdapUserMapper ldapUserMapper;

	@Mock
	private Attributes attributes;

	@Before
	public void setUp() throws Exception {
		ldapUserMapper = new LdapUserMapper();

		Attribute loginAttribute = createMockAndSetRule("cecorre");
		Mockito.when(attributes.get("login")).thenReturn(loginAttribute);

		Attribute emailAttribute = createMockAndSetRule("kadusjc@yahoo.com.br");
		Mockito.when(attributes.get("mail")).thenReturn(emailAttribute);

		Attribute departmentAttribute = createMockAndSetRule("IT");
		Mockito.when(attributes.get("department")).thenReturn(departmentAttribute);

		Attribute nameAttribute = createMockAndSetRule("Carlos Eduardo");
		Mockito.when(attributes.get("name")).thenReturn(nameAttribute);

		Attribute lastNameAttribute = createMockAndSetRule("Ferreiro Corrêa");
		Mockito.when(attributes.get("lastname")).thenReturn(lastNameAttribute);

		Attribute fullNameAttribute = createMockAndSetRule("Carlos Eduardo Ferreiro Corrêa");
		Mockito.when(attributes.get("fullname")).thenReturn(fullNameAttribute);

		Attribute telephoneAttribute = createMockAndSetRule(null);
		Mockito.when(attributes.get("telephonenum")).thenReturn(telephoneAttribute);
	}

	@Test
	public void testMapFromAttributes() throws NamingException {

		User user = ldapUserMapper.mapFromAttributes(attributes);
		assertTrue(user.getLogin().equals("cecorre"));
		assertTrue(user.getEmail().equals("kadusjc@yahoo.com.br"));
		assertTrue(user.getDepartment().equals("IT"));
		assertTrue(user.getName().equals("Carlos Eduardo"));
		assertTrue(user.getLastName().equals("Ferreiro Corrêa"));
		assertTrue(user.getFullName().equals("Carlos Eduardo Ferreiro Corrêa"));
		assertTrue(user.getPhone()==null);
	}


	private Attribute createMockAndSetRule(String returnedString) throws NamingException {
		Attribute attributeInstance = Mockito.mock(Attribute.class);
		Mockito.when(attributeInstance.get()).thenReturn(returnedString);
		return attributeInstance;
	}

}