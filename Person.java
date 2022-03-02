package com.iffi;

import java.util.List;

/**
 * This is our Person class.
 * this is a person constructor class 
 * 
 * @author Martin Herz 
 * @author Michael Endacott
 * Date: 2022/02/19
 *
 */
public class Person {
	
	private String personCode;
	private String lastName;
	private String firstName;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String country;
	private List<String> email;
	
	public Person(String personCode,String lastName,String firstName,String address,String city,String state,String zip,String country,List<String> email) {
		this.personCode = personCode;
		this.lastName = lastName;
		this.firstName = firstName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
		this.email = email;
	}
	
	public String getPersonCode() {
		return personCode;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFirstName() {
		return firstName;
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
	public List<String> getEmail(){
		return email;
	}
}