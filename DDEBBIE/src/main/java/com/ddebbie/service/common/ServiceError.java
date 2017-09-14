package com.ddebbie.service.common;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This Error Object that will be sent to UI in case of any Exceptions (Business or System) 
 * 
 * @author Ram
 * 13-Sep-2017
 */
public class ServiceError {

	private int code;
	private String message;
	
	@JsonProperty
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	@JsonProperty
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
