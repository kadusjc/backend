package com.ferreirocorrea.auth.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ferreirocorrea.auth.Role;
import com.ferreirocorrea.auth.User;
import com.ferreirocorrea.auth.UserAuthenticated;

@Service
public class UserDetailsService {

	@Autowired
	private LdapUserService ldapUserService;


	private static final Logger logger = Logger.getLogger(UserDetailsService.class);

	@Transactional
	public UserDetails loadUserByUser(Authentication authentication) throws UsernameNotFoundException {

		logger.debug("........Autenticating " + authentication);

		checkAuthenticationNotNull(authentication);

		User user = this.loadUser(authentication.getName());

		//For while the project has no roles
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(Role.SYS_USER.name()));

		UserAuthenticated userAuthenticated = new UserAuthenticated(authentication.getName(), authorities);
		userAuthenticated.setUser(user);
		return userAuthenticated;
	}

	private User loadUserFromLdap(String login) throws UsernameNotFoundException {
		User userInLdap = null;
		try {
			userInLdap = ldapUserService.findUserByLogin(login);
			if (userInLdap != null) {
				userInLdap.setLogin(userInLdap.getLogin().toUpperCase());
			}

			if (userInLdap == null) {
				logger.info("........ User " + login + " unauthorized to access backend ");
				throw new UsernameNotFoundException("User [" + login + "] is not authorized ");
			}


		} catch (Exception e) {
			logger.error("Ldap server is out of office", e);
		}

		logger.info("........ User found in ldap: " + userInLdap);
		return userInLdap;
	}


	private void checkAuthenticationNotNull(Authentication authentication) {
		if (authentication.getName() == null || authentication.getCredentials() == null) {
			throw new UsernameNotFoundException("User Not Found, or not Informed");
		}
	}

	private User loadUser(String login) {

		logger.info("........ Check Credentials into ldap " + login);

		User userInLdap = loadUserFromLdap(login);

		if (userInLdap == null) {
			logger.info("........ User "+login+" not found in Ldap");
		} else {
			logger.info("........ User authorized: " + userInLdap);
		}

		return userInLdap;
	}


}
