package com.iffi;

import java.time.LocalDate;

/**
 * This is our Crypto class.
 * This stores in the data for any form of a cryptocurrency
 * 
 * @author Martin Herz 
 * @author Michael Endacott
 *
 */

public class Crypto extends Asset{
	
	
	private double exchangeRate;
	private double exchangeFeeRate;
	private double exchangeOldRate;
	private double totalCoins;
	private LocalDate purchaseDate;
	
	public Crypto(String assetCode, String label,  double exchangeRate, double exchangeFeeRate) {
		super(assetCode, label, exchangeRate);
		this.exchangeFeeRate = exchangeFeeRate;
	}
	
	public Crypto(Crypto c, double exchangeOldRate, double totalCoins, LocalDate purchaseDate) {
		super(c.getAssetCode(), c.getLabel(), c.getCurrentValue());
		this.exchangeOldRate = exchangeOldRate;
		this.totalCoins = totalCoins;
		this.purchaseDate = purchaseDate;
		
	}


	public double getExchangeFeeRate() {
		return (exchangeFeeRate/100);
	}
	public double getTotalCoins() {
		return totalCoins;
	}

	public double getExchangeOldRate() {
		return exchangeOldRate;
	}

	
	
	
	public String getType() {
		return "Crypto";
	}
	
	/**
	 * 
	 * Total gain of the product, which is price where bought crypto at minus current price
	 * 
	 */
	

	
	public double getGain() {
		return this.getValue() - this.getCostBasis();
	}
	

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public double getValue() {
		return ( this.getCurrentValue() * this.getTotalCoins());
	}

	public String toString() {
		 return String.format("Old value of %s, purchased on %s: %f total coins at %f rate for $%f   \nCurrent value of %s: %f total coins at %f rate for $%f \n"
				+ "Total Value of Investment: %f percent", this.getLabel(),this.purchaseDate.toString(), this.getTotalCoins(),this.getExchangeOldRate(), this.getCostBasis(),
				this.getLabel(), this.getTotalCoins(), this.getCurrentValue(), this.getValue(), this.getCostBasis());
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	@Override
	public double getCostBasis() {
		return (this.getExchangeOldRate() * this.getTotalCoins());
	}
	
	
	
}
