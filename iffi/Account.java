package com.iffi;

import java.util.ArrayList;
import java.util.List;



public class Account extends Person implements Value{

	private String accountCode;
	private String accountType;
	private String beneCode;
	private final String owner;
	private Person person;
	private String manager;
	private List<Asset> assets;
	private Double gain = 0.0;
	private Double value = 0.0;
	//private Map<Double, Double> individualParts;
	
	
	public Account(Person person, String accountCode,  String accountType, String owner, String manager, String beneCode) {
		super(person.getPersonCode(), person.getFirstName(), person.getLastName(), person.getAddy(), person.getEmail());
		this.accountType = accountType;
		this.accountCode = accountCode;
		this.beneCode = beneCode;
		this.owner = owner;
		this.manager = manager;
		this.assets = new ArrayList<Asset>();
	}
	
	public String getAccountCode() {
		return accountCode;
	}

	public String getAccountType() {
		return accountType;
	}

	public String getBeneCode() {
		return beneCode;
	}

	public String getOwner() {
		return owner;
	}

	public String getManager() {
		return manager;
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

	public double getGain() {
		return gain;
	}
		
	/**
	 * 
	 * 
	 * This returns our cost
	 */

	public double getOrigPrice() {
		return value;
	}
	
	
	/**
	 * This gets the total return % of an account's assets
	 * 
	 * Also lets us return an account's gain and cost
	 * 
	 *
	 */

	
	public double getValue() {
		for(int i = 0; i < this.assets.size(); i++) {
			Asset asset = this.assets.get(i);
			if (asset.getType().equals("Stock")) {
				Stock stock = (Stock) asset;
				value += stock.getOrigPrice();
				gain += stock.getGain();
	
			}
			else if (asset.getType().equals("Crypto")) {
				Crypto c = (Crypto) asset;
				value += c.getOrigPrice();
				gain +=  (c.getGain());
				
			}
			else if(asset.getType().equals("Property")){
				Property prop = (Property) asset;
				value += prop.getOrigPrice();
				gain += (prop.getGain());
				
			}
			else if(asset.getType().equals("Put")){
				Option put = (Option) asset;
				gain += (put.getGain());
				value += put.getOrigPrice();
				
			}
			else if(asset.getType().equals("Call")){
				Option call = (Option) asset;
				gain += (call.getGain());
				value += (call.getOrigPrice());
			}
			
			
			
		}

		if(value == 0) {
			return 0.0;
		}

		
		return ((gain)/value) * 100;
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





	

	

}
