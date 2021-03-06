package com.iffi;

import java.util.ArrayList;
import java.util.List;



/**
 * 
 * 
 * 
 * @author Martin Herz, Michael Endacott
 * 
 * This is our Account class, which stores information only related to account and models a generic Account in our financial database.
 *
 */

public class Account{

	private String accountCode;
	private String accountType;
	private Person beneficiary;
	private Person person;
	private Person manager;
	private List<Asset> assets;
	private Integer AccountId;
	
	public Account(Integer AccountId, Person person, String accountCode,  String accountType, Person manager, Person beneficiary) {
		super();
		this.AccountId = AccountId;
		this.person = person;
		this.accountType = accountType;
		this.accountCode = accountCode;
		this.beneficiary = beneficiary;
		this.manager = manager;
		this.assets = new ArrayList<Asset>();
	}
	
	public Account(Person person, String accountCode,  String accountType, Person manager, Person beneficiary) {
		this(null, person, accountCode, accountType, manager, beneficiary);
	}
	

	public String getAccountCode() {
		return accountCode;
	}

	private String getAccountType() {
		return accountType;
	}

	public String getBeneficiry() {
		return this.beneficiary.toString();
	}

	public String getOwner() {
		return this.person.toString();
	}

	public Person getManager() {
		return manager;
	}
	
	public Person getBenef() {
		return beneficiary;
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
	 * This returns our Account Value
	 */

	public double getValue() {
		double value = 0.0;
		for(int i = 0; i<this.assets.size();i++) {
			value += assets.get(i).getValue();
		}
		return value;
	}
		
	/**
	 * 
	 * 
	 * This returns our Account Cost Basis
	 */
	public double getCostBasis() {
		double cost = 0.0;
		for(int i = 0; i<this.assets.size();i++) {
			cost += assets.get(i).getCostBasis();
		}
		return cost;
	}
	
	/**
	 * 
	 * @returns our Account gain
	 */
	
	public double getGain() {
		return this.getValue() - this.getCostBasis();
	}
	
	/**
	 * 
	 * @returns our Account Return Percent
	 */
	
	public double getReturnPercent() {
		return (this.getGain()/this.getCostBasis()) * 100;
	}
	
	/**
	 * 
	 * This is our our Fee calculator, returns total Fees for an account instance
	 */
	
	public double FeeCalc() {
		double Fee = 0.0;
		for(int i = 0; i<this.assets.size(); i++) {
			Asset asset = this.assets.get(i);
			
			//TODO: Change to instance instead of equals
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
		return String.format("%s %s, %s %s, %s $ %.2f, $ %.2f, %.2f percent, $ %.2f",  
				this.getAccountCode(), this.getOwnerr().getLastName(), this.getOwnerr().getFirstName(), this.getManager().getLastName(), this.getManager().getFirstName(),
				this.FeeCalc(),this.getGain(), this.getReturnPercent(),this.getValue());
	}
	
 
	public String toIndividualInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("==============================\n" + "|| " + this.getAccountTypeFull() + " Account " + this.getAccountCode() + " ||\n" + "==============================\n");
		sb.append("+----------------+\n"
								+ "|   Owner       |\n"
								+ "+----------------+\n");
		sb.append(this.getOwnerr().getLastName() + " , " + this.getOwnerr().getFirstName() + "\n");
		sb.append(this.getOwnerr().getEmail() + "\n");
		sb.append(this.getOwnerr().getAddyString() + "\n");
		
		sb.append("+----------------+\n"
				+ "|   Manager       |\n"
				+ "+----------------+\n");
		sb.append(this.getManager().getLastName() + " , " + this.getManager().getFirstName() + "\n");
		sb.append(this.getManager().getEmail() + "\n");
		sb.append(this.getManager().getAddyString() + "\n");
		
		
		if(this.getBenef() == null) {
			sb.append("+----------------+\n"
					+ "|   Beneficiary    |\n"
					+ "+----------------+\n");
			sb.append("No Beneficiary!");
			return sb.toString();
		}
		sb.append("+----------------+\n"
				+ "|   Beneficiary       |\n"
				+ "+----------------+\n");
		sb.append(this.getBenef().getLastName() + " , " + this.getBenef().getFirstName() + "\n");
		sb.append(this.getBenef().getEmail() + "\n");
		sb.append(this.getBenef().getAddyString());
		
		return sb.toString();
		
		
	}
	

	
	public String totalInfo() {
		return String.format("+----------------+\n"
								+ "|   Totals       |\n"
								+ "+----------------+\n"
				+ "Total Value:        $%.2f \nCost Basis:         $ %.2f\nTotal Account Fees: $ %.2f\n"
				+ "Total Return:       $ %.2f\nTotal Return Percent: %.2f\n",this.getValue(), this.getCostBasis(),
				this.FeeCalc(),this.getGain(),this.getReturnPercent());
	}
	
	public String AssetInformation() {
		
		StringBuilder sb = new StringBuilder(); 
		sb.append("+----------------+\n"
								+ "|   Assets       |\n"
								+ "+----------------+\n");
		for(Asset asset : this.getAssets()) {
			sb.append('\n');
			sb.append(asset.toString());
			sb.append('\n');
		}
		return sb.toString();
	}
	
	public String getAccountTypeFull() {
		if(this.getAccountType().equals("P")) {
			return "Pro";
		}
		return "Noob";
	}
	
	
	private Person getOwnerr() {
		return person;
	}

	public Integer getAccountId() {
		return AccountId;
	}







	

	

}
