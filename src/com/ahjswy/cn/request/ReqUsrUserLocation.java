package com.ahjswy.cn.request;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class ReqUsrUserLocation {

	@JsonProperty("address")
	private String address;

	@JsonProperty("id")
	private String id;

	@JsonProperty("latitude")
	private double latitude;

	@JsonProperty("longitude")
	private double longitude;

	@JsonProperty("name")
	private String name;

	public ReqUsrUserLocation() {
	}

	public ReqUsrUserLocation(String id, String name, double longitude, double latitude, String address) {
		this.id = id;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.address = address;
	}

	@JsonIgnore
	public String getAddress() {
		return this.address;
	}

	@JsonIgnore
	public String getId() {
		return this.id;
	}

	@JsonIgnore
	public double getLatitude() {
		return this.latitude;
	}

	@JsonIgnore
	public double getLongitude() {
		return this.longitude;
	}

	@JsonIgnore
	public String getName() {
		return this.name;
	}

	@JsonIgnore
	public void setAddress(String address) {
		this.address = address;
	}

	@JsonIgnore
	public void setId(String id) {
		this.id = id;
	}

	@JsonIgnore
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@JsonIgnore
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@JsonIgnore
	public void setName(String name) {
		this.name = name;
	}
}