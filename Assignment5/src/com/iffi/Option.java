package com.iffi;

import java.time.LocalDate;

/**
 * 
 * @author Martin Herz, Michael Endacott
 *
 * This is our Option class. It's an extension of Stock and is abstract since we have multiple types and unique features that are common
 * only in Calls and Puts.
 *
 */

abstract class Option extends Stock{

	private LocalDate purchaseDate;
	private LocalDate strikeDate;
	protected Stock stock;
	protected double strikePricePerShare;	
	protected int shareLimit;
	protected double premiumPerShare;
	
	public Option(Stock stock, LocalDate purchaseDate, double strikePricePerShare, int shareLimit, double premiumPerShare, LocalDate strikeDate) {
		super(stock.getAssetId(),stock.getAssetCode(), stock.getLabel(), stock.getCurrentValue());
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
	
	
	public abstract double getGain();
		

	public abstract double getReturnPercent();
	
	public abstract double getValue();

	
	public abstract String toString();
	
}


