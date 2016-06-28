package com.ferreirocorrea.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ferreirocorrea.auth.User;
import com.ferreirocorrea.auth.UserAuthenticated;

@Service
public class AuthenticatedUserService {

	public UserAuthenticated getAuthenticated() {

		if (!this.containsUserAuthenticated()) {
			return null;
		}
		return (UserAuthenticated) this.getAuthentication().getPrincipal();

	}

	private boolean containsUserAuthenticated() {
		Authentication authentication = this.getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return false;
		}

		return !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
	}

	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static AuthenticatedUserService getInstance() {
		return new AuthenticatedUserService();
	}

	public User getAuthenticatedUser() {
		return this.getAuthenticated().getUser();
	}

}
