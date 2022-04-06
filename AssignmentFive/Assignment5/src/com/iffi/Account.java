package com.iffi;

import java.util.ArrayList;
import java.util.List;



public class Account{

	private String accountCode;
	private String accountType;
	private Person beneficiary;
	private Person person;
	private Person manager;
	private List<Asset> assets;
	private Double cost = 0.0;
	private Double value = 0.0;	
	
	public Account(Person person, String accountCode,  String accountType, Person manager, Person beneficiary) {
		this.person = person;
		this.accountType = accountType;
		this.accountCode = accountCode;
		this.beneficiary = beneficiary;
		this.manager = manager;
		this.assets = new ArrayList<Asset>();
	}
	

	public String getAccountCode() {
		return accountCode;
	}

	public String getAccountType() {
		return accountType;
	}

	public String getBeneficiry() {
		return this.beneficiary.toString();
	}

	public String getOwner() {
		return this.person.toString();
	}

	public String getManager() {
		return this.manager.toString();
	}

	public List<? extends Asset> getAssets() {
		return assets;
	}

	public void addAsset(Asset t) {
		this.assets.add(t);
	}
	/**
	 * 
	 * 
	 * This returns our gain
	 */

	public double getValue() {
		for(int i = 0; i<this.assets.size();i++) {
			value += assets.get(i).getValue();
		}
		return value;
	}
		
	/**
	 * 
	 * 
	 * This returns our cost
	 */




	public double getCostBasis() {
		for(int i = 0; i<this.assets.size();i++) {
			cost += assets.get(i).getCostBasis();
		}
		return cost;
	}
	
	/**
	 * 
	 * This is our our Fee calculator, returns total Fees for an account instance
	 */
	
	public double FeeCalc() {
		double Fee = 0.0;
		for(int i = 0; i<this.assets.size(); i++) {
			Asset asset = this.assets.get(i);
			if (asset.getType().equals("Property")) {
				Fee += 100;
			}
			else if (asset.getType().equals("Crypto")) {
				Fee += 10;
			}

		}
		if(this.getAccountType().equals("P")) {
			return Fee * .75;
		}
		return Fee;
	}
	
	
	
	public String toString(){
		return String.format("Account Number: %s\nName: %s %s\nFees: $%f\nReturn Percent: %f percent\nTotal Value: $ %f\nTotal Gain: $%f\n",  this.getAccountCode(), this.getFirstName(), this.getLastName(),
				this.FeeCalc(),this.getValue(),this.getOrigPrice(), this.getGain());
	}

	public String toIndividualInfo() {
		return String.format("Account Number: %s\nName: %s  "
				+ "%s\nEmails/Contact Info: %s",
				this.getAccountCode(), this.getFirstName(), this.getLastName(), this.getEmail());
		
		
	}

	public Person getPerson() {
		return person;
	}





	

	

}
