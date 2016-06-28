package com.ferreirocorrea.auth.service;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ferreirocorrea.WebApplication;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebApplication.class)
@WebAppConfiguration
@IntegrationTest
public class LdapUserServiceIntegrationTest {

	@Autowired
	private LdapUserService ldapUserService;

	@Before
	public void setUp() {
		assertTrue(ldapUserService!=null);
	}

	@Test
	public void findUserByLogin() {
		String login = "carlos.correa";
		ldapUserService.findUserByLogin(login);
	}

}
