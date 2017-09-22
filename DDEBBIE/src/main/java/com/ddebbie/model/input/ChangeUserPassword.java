package com.ddebbie.model.input;

import com.ddebbie.model.AbstractObject;
import com.ddebbie.model.CookieToken;

public class ChangeUserPassword extends AbstractObject{	

	private static final long serialVersionUID = 1L;
	private String currentPassword;
	private String newPassword;
	private String retypePassword;
	private CookieToken cookieToken;
	
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getRetypePassword() {
		return retypePassword;
	}
	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}
	public CookieToken getCookieToken() {
		return cookieToken;
	}
	public void setCookieToken(CookieToken cookieToken) {
		this.cookieToken = cookieToken;
	}
	
}
