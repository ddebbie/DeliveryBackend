package com.ddebbie.business;

public class BusinessParcel {
	public static final String LABEL_LENGTH ="length";
	public static final String LABEL_WIDTH ="width";
	public static final String LABEL_HEIGHT = "height";
	public static final String LABEL_DISTANCEUNIT = "distanceUnit";
	public static final String LABEL_WEIGHT = "weight";
	public static final String LABEL_MASSUNIT = "massUnit";

	private String length;
	private String width;
	private String height;
	private String distanceUnit;
	private String weight;
	private String massUnit;

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getDistanceUnit() {
		return distanceUnit;
	}

	public void setDistanceUnit(String distanceUnit) {
		this.distanceUnit = distanceUnit;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getMassUnit() {
		return massUnit;
	}

	public void setMassUnit(String massUnit) {
		this.massUnit = massUnit;
	}
}
