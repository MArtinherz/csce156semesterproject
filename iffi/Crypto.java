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

public class Crypto extends Asset implements Value{
	
	
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
		return exchangeFeeRate;
	}
	public double getTotalCoins() {
		return totalCoins;
	}



	
	
	public String getType() {
		return "Crypto";
	}
	
	public double newValue() {
		return this.getCurrentValue() * this.totalCoins * (1 - this.getExchangeFeeRate());
	}
	
	public double oldValue() {
		return this.exchangeOldRate * this.totalCoins;
	}


	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public double getValue() {
		return (this.newValue() - this.oldValue())/this.oldValue();
	}
	
	
	
}
