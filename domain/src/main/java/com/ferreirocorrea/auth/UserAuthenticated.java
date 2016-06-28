package com.ferreirocorrea.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * Especialização da Classe User fornecida pelo Spring
 *
 */

public class UserAuthenticated extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	private static final boolean USER_ENABLED = true;

	private static final boolean ACCOUNT_NOT_EXPIRED = true;

	private static final boolean CREDENTIONS_NOT_EXPIRED = true;

	private static final boolean ACCOUNT_NOT_LOCKED = true;

	private User user;

	public UserAuthenticated(String login, Collection<? extends GrantedAuthority> authorities) {
		super(login, "", USER_ENABLED, ACCOUNT_NOT_EXPIRED, CREDENTIONS_NOT_EXPIRED, ACCOUNT_NOT_LOCKED, authorities);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAuthenticated other = (UserAuthenticated) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserAuthenticated [user=").append(user).append("]");
		return builder.toString();
	}

}