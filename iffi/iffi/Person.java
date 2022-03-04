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
//	private String address;
//	private String city;
//	private String state;
//	private String zip;
//	private String country;
	private List<String> email;
	private Address addy;
		
	

	public Person(String personCode,String lastName,String firstName,Address addy, List<String> email) {
			this.personCode = personCode;
			this.lastName = lastName;
			this.firstName = firstName;
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
		public List<String> getEmail(){
			return email;
		}
	
		
	public Address getAddy() {
			return addy;
	}

	
	}

