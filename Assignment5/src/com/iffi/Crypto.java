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
	private Integer cryptoAssetId;
	private Integer assetId;
	
	public Crypto(Integer assetId, String assetCode, String label,  double exchangeRate, double exchangeFeeRate) {
		super(assetId, assetCode, label, exchangeRate);
		this.exchangeFeeRate = exchangeFeeRate;
	}
	
	public Crypto(String assetCode, String label,  double exchangeRate, double exchangeFeeRate) {
		this(null, assetCode, label, exchangeRate, exchangeFeeRate);
		
	}
	
	public Crypto(Crypto c, double exchangeOldRate, double totalCoins, LocalDate purchaseDate) {
		super(c.getAssetId(), c.getAssetCode(), c.getLabel(), c.getCurrentValue());
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

	
	public double getGain() {
		return this.getValue() - this.getCostBasis();
	}
	

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public double getValue() {
		return ( this.getCurrentValue() * this.getTotalCoins() * ( 1 - this.getExchangeFeeRate()));
	}
	public double getReturnPercent() {
		return (this.getGain())/(this.getCostBasis()) * 100;
	}

	public String toString() {
		return String.format("%s  %s  %s\n"
				+ 			"Cost Basis:  %.2f Coins @ $%f on %s\n"
				+ 			"Value Basis: %f Coins @ $%f less TBD "
				+ 			"      %.2f   $    %.2f", this.getAssetCode(),this.getLabel() ,this.getType(), this.getTotalCoins(),this.getExchangeOldRate(),
				this.getPurchaseDate().toString(),this.getTotalCoins(),this.getCurrentValue(), this.getReturnPercent(),this.getValue());
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	@Override
	public double getCostBasis() {
		return (this.getExchangeOldRate() * this.getTotalCoins());
	}
	
	
	
}
