package com.ferreirocorrea.auth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.stereotype.Service;

import com.ferreirocorrea.auth.User;

@Service
public class LdapUserService {

	@Autowired
	private LdapTemplate ldapTemplate;

	@Autowired
	private LdapUserMapper ldapUserMapper;

	public User findUserByLogin( String login ) {
    return findUserByFilter( new OrFilter()
    .or( new EqualsFilter("login", login)) );
		//new User("carlos.correa"); //for tests when without ldap server is unavailable
	}

	private User findUserByFilter (final OrFilter filter) {
		List<User> users = searchDistinguishedName(filter);

		if (users.isEmpty()) return null;
		return users.get(0);
	}

	@SuppressWarnings("unchecked")
	private List<User> searchDistinguishedName(final OrFilter filter) {

		return getLdapTemplate().search(DistinguishedName.EMPTY_PATH
											, filter.encode()
											, ldapUserMapper);

	}


	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}


	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}



}
