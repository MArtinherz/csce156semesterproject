package com.iffi;

public class Address {
	
	private String address;
	private String city;
	private String state;
	private String zip;
	private String country;
	
	public Address(String address, String city, String state, String zip, String country) {
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getCountry() {
		return country;
	}
	
	public String toString() {
		return String.format("%s %s %s %s %s", this.getAddress(), this.getCity(), this.getCountry(), this.getState(), this.getZip());
	}
	
	


}
