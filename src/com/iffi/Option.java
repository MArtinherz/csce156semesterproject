package com.iffi;

import java.time.LocalDate;

public class Option extends Asset implements Value{

	private LocalDate purchaseDate;
	private LocalDate strikeDate;
	private Stock stock;
	protected double strikePricePerShare;	
	protected int shareLimit;
	protected double premiumPerShare;
	private String optionType;
	
	public Option(Stock stock, String optionType, LocalDate purchaseDate, double strikePricePerShare, int shareLimit, double premiumPerShare, LocalDate strikeDate) {
		super(stock.getAssetCode(), stock.getLabel(), stock.getCurrentValue());
		this.purchaseDate = purchaseDate;
		this.strikeDate = strikeDate;
		this.strikePricePerShare = strikePricePerShare;
		this.shareLimit = shareLimit;
		this.premiumPerShare = premiumPerShare;
		this.optionType = optionType;
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
	
	/**
	 * 
	 * 
	 * This gets the original price of the option, no matter if it's a call or a put
	 * 
	 * 
	 * 
	 */
	
	public double getOrigPrice() {
		
		return this.getPremiumPerShare() * this.getShareLimit();
		
	}
	
	/**
	 * 
	 * 
	 * This calculates the total gain/loss of the option
	 * 
	 * 
	 */
	
	public double getGain() {
		
		if (this.getType().equals("Call")) {
			if (this.getCurrentValue() < this.getStrikePricePerShare()) {				
				return -(this.getOrigPrice());
			}
			else if (this.getCurrentValue() >= this.getStrikePricePerShare()) {
				double value = (this.getCurrentValue() - this.getStrikePricePerShare()) * this.getShareLimit();
				double gain = value - this.getOrigPrice();
				return gain;
			}
		}
		
		else if (this.getType().equals("Put")) {
			if (this.getCurrentValue() < this.getStrikePricePerShare()) {
				double value = 0.0;
				double gain = value;
				return gain;
			}
			else if (this.getCurrentValue() > this.getStrikePricePerShare()) {
				double value = ((this.getStrikePricePerShare() - this.getCurrentValue()) * this.getShareLimit());
				if(value > 0) {
					return this.getOrigPrice();
				}
				else if(value == 0) {
					return 0.0;
				}
				else {
					return -(this.getOrigPrice());
				}
			}
			
		}
		return 0.0;
	}
		
	public double getReturnPercent() {
		
		return this.getGain()/this.getOrigPrice() * 100;
			

				
	}

	public String getType() {
		if (this.optionType.equals("P")){
			return "Put";
		}
		return "Call";
	}
	
	public String toString() {
		return String.format("Old value of %s %s option, purchased on %s: $%f for %f stocks at strike price: %f \n"
				+ "Current Share Price: %f \n"
				+ "Date to Expiration: %s\n"
				+ "Total Value of Investment: %f percent", this.getLabel(), this.getType(), this.getPurchaseDate().toString(),
				this.getPremiumPerShare(), this.getShareLimit(), this.getStrikePricePerShare(), this.getCurrentValue(),
				this.getStrikeDate(), this.getReturnPercent());
	}

	
}


