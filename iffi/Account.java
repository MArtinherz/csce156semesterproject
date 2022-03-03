package com.iffi;

import java.util.List;

public class Account extends Person{

	private String accountCode;
	private String accountType;
	private String beneCode;
	private List<Asset> assets;
	
	public Account(String personCode, String lastName, String firstName, String address, String city, String state,
			String zip, String country, String accountType, String beneCode) {
		super(personCode, lastName, firstName, address, city, state, zip, country);
		this.accountType = accountType;
		this.accountCode = accountCode;
		this.beneCode = beneCode;
		this.assets = new ;
		// TODO Auto-generated constructor stub
	}
	/**
	 * We need account type, works off a person type since only one person has an account
	 * 
	 * We need a list of for our assets so we can calculate for each person, also need a fee calculator
	 * 
	 * 
	 */
	

}
