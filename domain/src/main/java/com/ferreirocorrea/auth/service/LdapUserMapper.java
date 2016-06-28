package com.ferreirocorrea.auth.service;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.stereotype.Component;

import com.ferreirocorrea.auth.User;
@Component
public class LdapUserMapper implements AttributesMapper {


	@Override
	public User mapFromAttributes(Attributes attributes) throws NamingException {

		AttributeMapperHelper mapper = new AttributeMapperHelper(attributes);

		User user = new User();

		user.setLogin(mapper.get("login"));
		user.setEmail(mapper.get("mail"));
		user.setDepartment(mapper.get("department"));
		user.setName(mapper.get("name"));
		user.setLastName(mapper.get("lastname"));
		user.setFullName(mapper.get("fullname"));
		user.setPhone(mapper.get("telephonenum"));

		//to get all roles from user inside ldap
		//List<Role> roles = this.ldapUserRoleSearch.searchForRolesInAttribute(attributes.get("memberOf"));
		//user.setRoles(roles);

		return user;
	}


}
