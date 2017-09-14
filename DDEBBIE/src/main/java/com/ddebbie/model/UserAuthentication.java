package com.ddebbie.model;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Ram
 * 13-Sep-2017
 */
public class UserAuthentication implements  Serializable, Authentication, UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String password;
	
	private String username;
	
	private boolean accountNonExpired;
	
	private boolean accountNonLocked;
	
	private boolean credentialsNonExpired;
	
	private boolean enabled;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	private Object credentials;
	
	private String details;
	
	private Object principal;
	
	private boolean authenticated;//cre, deta, prin
	
	private long userId;
	
	private int status;
	
	

	public UserAuthentication(String name, String password, String userName,
			boolean accountNonExpired, boolean accountNonLocked,
			boolean credentialsNonExpired, boolean enabled,
			Collection<? extends GrantedAuthority> authorities,
			Object credentials, String details, Object principal,
			boolean authenticated) {
		super();
		this.name = name;
		this.password = password;
		this.username = userName;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.authorities = authorities;
		this.credentials = credentials;
		this.details = details;
		this.principal = principal;
		this.authenticated = authenticated;
	}
	
	

	public UserAuthentication() {
		super();
		// TODO Auto-generated constructor stub
	}



	public UserAuthentication(User userByName, Collection<SimpleGrantedAuthority> authorities) {
		// TODO Auto-generated constructor stub
		this.name = userByName.getName();
		this.password = userByName.getPassword();
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
		this.authorities = authorities;
		this.credentials = userByName.getPassword();
		this.details = userByName.getEmail();
		this.principal = userByName.getName();
		this.authenticated = true;
		this.userId = userByName.getId();
	}




	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return credentials;
	}

	@Override
	public Object getDetails() {
		// TODO Auto-generated method stub
		return details;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return principal;
	}

	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		this.authenticated = isAuthenticated;
		
	}



	public long getUserId() {
		return userId;
	}



	public void setUserId(long userId) {
		this.userId = userId;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
