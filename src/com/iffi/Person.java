package com.iffi;

import java.util.List;

/**
 * This is our person class, which models a person that holds an account within our system
 *
 * @author Martin Herz, Michael Endacott
 *
 */


public class Person extends Address{

	private String personCode;
	private String lastName;
	private String firstName;
	private List<String> emails;
	//private Address address;



	public Person(String personCode,String lastName,String firstName,Address address, List<String> emails) {
			super(address.getAddress(),address.getCity(),address.getState(),address.getZip(),address.getCountry()); 
			this.personCode = personCode;
			this.lastName = lastName;
			this.firstName = firstName;
			this.emails = emails;
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
		
	public List<String> getEmail(){
			return emails;
		}

	public String getFullAddress() {
		return String.format("%s %s %s %s %s", this.getAddress(), this.getCity(), this.getCountry(), this.getState(), this.getZip());
	}


}

