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
	
	public double getCurrentPrice() {
		return (this.getCurrentValue() * this.getTotalCoins() * (1 - this.getExchangeFeeRate()));
	}
	
	public double getGain() {
		return this.getCurrentPrice() - this.getOrigPrice();
	}
	
	/**
	 * 
	 * 
	 *  price of the asset when bought
	 */
	
	public double getOrigPrice() {
		return (this.getExchangeOldRate() * this.getTotalCoins());
	}
 
 
	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}
 
	public double getReturnPercent() {
		return ((this.getGain())/this.getOrigPrice()) * 100;
	}
 
	public String toString() {
		 return String.format("Old value of %s: %f total coins at %f rate for $%f   \nCurrent value of %s: %f total coins at %f rate for $%f \n"
				+ "Total Value of Investment: %f percent", this.getLabel(),this.getTotalCoins(),this.getExchangeOldRate(), this.getOrigPrice(),
				this.getLabel(), this.getTotalCoins(), this.getCurrentValue(), this.getCurrentPrice(), this.getReturnPercent());
	}
 
	public double getExchangeRate() {
		return exchangeRate;
	}
	
	
	
}
