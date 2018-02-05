package com.ddebbie.model;

public class CookieToken{
	private static final long serialVersionUID = -8394717698349704984L;
	private String token = "";
	private long userId;
	
	
	public CookieToken(String token, long userId) {
		super();
		this.token = token;
		this.userId = userId;
	}

	public CookieToken(String cookieToken){
		this.token	=	cookieToken;
	}
	
	public CookieToken(){
		
	}
	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
}
