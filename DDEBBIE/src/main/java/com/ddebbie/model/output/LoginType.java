package com.ddebbie.model.output;

public class LoginType {
	/**
	 * indicate role type of login user, if login user as member then it will redirect
	 * home page or it redirect logged user to admin page, it depends on user role 
	 */
	private int roleType;
	/*login status is indicates login success or failure.*/
	private boolean loginStatus=false;

	
	public int getRoleType() {
		return roleType;
	}

	public boolean isLoginStatus() {
		return loginStatus;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}


}
