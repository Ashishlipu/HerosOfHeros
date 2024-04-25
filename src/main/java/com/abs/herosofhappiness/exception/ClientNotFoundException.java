package com.abs.herosofhappiness.exception;

import lombok.Data;

@Data
public class ClientNotFoundException extends RuntimeException {
	private String entity;
	private String attribute;
	private String value;
	private String msg ="Admin not found with the given details";
	
	public ClientNotFoundException(String entity, String attribute, String value) {
		super();
		this.entity = entity;
		this.attribute = attribute;
		this.value = value;
	}

	public ClientNotFoundException(String msg) {
		super();
		this.msg = msg;
	}
}
