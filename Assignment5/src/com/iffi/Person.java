package com.iffi;

import java.util.List;

/**
 * This is our person class, which models a person that holds an account within our system
 *
 * @author Martin Herz, Michael Endacott
 *
 */


public class Person {

	private String personCode;
	private String lastName;
	private String firstName;
	private List<String> email;
	private Address addy;



	public Person(String personCode,String lastName,String firstName, Address addy, List<String> email) {
			this.personCode = personCode;
			this.lastName = lastName;
			this.firstName = firstName;
			this.email = email;
			this.addy = addy;
		}

	public String getPersonCode() {
			return this.personCode;
		}
	public String getLastName() {
			return this.lastName;
	}
	public String getFirstName() {
			return this.firstName;
			}
		
	public List<String> getEmail(){
			return this.email;
		}


	public Address getAddy() {
			return this.addy;
	}
	
	public String getAddyString() {
		return this.addy.toString();
}

	@Override
	public String toString() {
		return "Person [personCode=" + personCode + ", lastName=" + lastName + ", firstName=" + firstName + ", email="
				+ email + ", addy=" + addy + "]";
	}
	


	}

