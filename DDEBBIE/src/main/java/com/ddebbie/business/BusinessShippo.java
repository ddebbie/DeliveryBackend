package com.ddebbie.business;

import com.ddebbie.model.CookieToken;

public class BusinessShippo extends CookieToken {
	private BusinessAddress toAddress;
	private BusinessAddress fromAddress;
	private BusinessParcel parcel;

	public BusinessAddress getToAddress() {
		return toAddress;
	}

	public void setToAddress(BusinessAddress toAddress) {
		this.toAddress = toAddress;
	}

	public BusinessAddress getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(BusinessAddress fromAddress) {
		this.fromAddress = fromAddress;
	}

	public BusinessParcel getParcel() {
		return parcel;
	}

	public void setParcel(BusinessParcel parcel) {
		this.parcel = parcel;
	}
}
