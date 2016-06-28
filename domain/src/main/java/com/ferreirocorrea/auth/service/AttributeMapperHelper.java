package com.ferreirocorrea.auth.service;

import java.io.Serializable;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public class AttributeMapperHelper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6656162573634001651L;
	private Attributes attributes;

	public AttributeMapperHelper(Attributes attributes) {
		this.attributes = attributes;
	}

	public String get(String attribute) throws NamingException {
		return (attributes.get(attribute) != null) ? (String) attributes.get(attribute).get() : "";
	}

	
	public Attributes getAttributes() {
		return attributes;
	}

}
