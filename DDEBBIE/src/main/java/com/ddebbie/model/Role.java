package com.ddebbie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends AbstractObject {

	private static final long serialVersionUID = -1966810337791950551L;

	@Column(name = "userid")
	private long userId;

	@Column(name = "type")
	private int type;

	public static final String LABEL_USER_ID = "userId";
	public static final String LABEL_ADMIN_TYPE = "adminType";

	public static final String LABEL_MEMBER = "ROLE_MEMBER";
	public static final String LABEL_ROLE_ADMIN = "ROLE_ADMIN";

	// Different type of adminType in role Table
	public static final int ROLE_ADMIN = 1;
	public static final int ROLE_MEMBER = 10;

	public String getRoleValue() {
		return getType() == ROLE_MEMBER ? LABEL_MEMBER : getType() == ROLE_ADMIN ? LABEL_ROLE_ADMIN : "";

	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
