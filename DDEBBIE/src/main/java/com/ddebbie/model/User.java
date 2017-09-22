package com.ddebbie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Ram
 * @TableDescription user
 */
@Entity
@Table(name = "user")
public class User extends AbstractObject {

	private static final long serialVersionUID = -8394716698349704984L;
	public static final String LABEL_EMAIL = "email";
	public static final String LABEL_NAME = "name";
	public static final String LABEL_PASSWORD = "password";
	public static final String LABEL_PROFILE_IMAGE_FILE_ID = "profileImageFileId";
	public static final String LABEL_SIGNUPTOKEN = "confirmationToken";
	public static final String LABEL_VERIFICATIONCODE = "verificationCode";
	
	
	public static final int STATUS_ACTIVE = 1;
	public static final int STATUS_INACTIVE = 2;
	public static final int STATUS_NOT_VERIFIED = 3;

	@Column(name = "name")
	private String name;

	@Column
	private String password;

	@Column
	private String email;
	
	@Column(name="displayname")
	private String displayName;

	@Column
	private String mobile = "";

	@Column(name = "profileimagefileid")
	private long profileImageFileId;
	
	@Column(name = "active")
	private boolean active;
	
	@Column(name = "signuptoken")
	private String confirmationToken;
	
	@Column(name = "verificationcode")
	private String verificationCode;
	

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getMobile() {
		return mobile;
	}

	public long getProfileImageFileId() {
		return profileImageFileId;
	}

	public boolean isActive() {
		return active;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setProfileImageFileId(long profileImageFileId) {
		this.profileImageFileId = profileImageFileId;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
	

	
}