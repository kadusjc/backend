package com.ferreirocorrea.auth;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5675657075005235376L;

	private String login;

	private String email;

	private String department;

	private String name;

	private String lastName;

	private String fullName;

	private String phone;

	public User(){}

	public User(String login){
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", email=" + email + ", department="
				+ department + ", name=" + name + ", lastName=" + lastName
				+ ", fullName=" + fullName + ", phone=" + phone + "]";
	}

}
