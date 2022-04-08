package com.iffi;

import java.time.LocalDate;

abstract class Option extends Asset{

	private LocalDate purchaseDate;
	private LocalDate strikeDate;
	protected Stock stock;
	protected double strikePricePerShare;	
	protected int shareLimit;
	protected double premiumPerShare;
	//private String optionType;
	
	public Option(Stock stock, LocalDate purchaseDate, double strikePricePerShare, int shareLimit, double premiumPerShare, LocalDate strikeDate) {
		super(stock.getAssetCode(), stock.getLabel(), stock.getCurrentValue());
		this.purchaseDate = purchaseDate;
		this.strikeDate = strikeDate;
		this.strikePricePerShare = strikePricePerShare;
		this.shareLimit = shareLimit;
		this.premiumPerShare = premiumPerShare;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public LocalDate getStrikeDate() {
		return strikeDate;
	}

	public double getStrikePricePerShare() {
		return strikePricePerShare;
	}

	public double getShareLimit() {
		return shareLimit;
	}

	public double getPremiumPerShare() {
		return premiumPerShare;
	}
	public Stock getStock() {
		return stock;
	}
	
	/**
	 * 
	 * 
	 * This gets the original price of the option, no matter if it's a call or a put
	 * 
	 * 
	 * 
	 */
	
//	public double getOrigPrice() {
//		
//		return this.getPremiumPerShare() * this.getShareLimit();
//		
//	}
	
	/**
	 * 
	 * 
	 * This calculates the total gain/loss of the option
	 * 
	 * 
	 */
	
	public abstract double getGain();
		

	public abstract double getReturnPercent();
	
	public abstract double getValue();

	
	public abstract String toString();
	
}


