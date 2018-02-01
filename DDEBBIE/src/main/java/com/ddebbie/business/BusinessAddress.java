package com.ddebbie.business;

public class BusinessAddress {

	public static final String LABEL_NAME ="name";
	public static final String LABEL_COMPANY ="company";
	public static final String LABEL_STREET1 = "street1";
	public static final String LABEL_CITY ="city";
	public static final String LABEL_STATE ="state";
	public static final String LABEL_ZIP = "zip";
	public static final String LABEL_COUNTRY ="country";
	public static final String LABEL_PHONE ="phone";
	public static final String LABEL_EMAIL ="email";
	public static final String LABEL_METADATA ="metadata";

	private String name;
	private String company;
	private String street1;
	private String city;
	private String state;
	private String zip;
	private String country;
	private String phone;
	private String email;
	private String metadata;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
}
