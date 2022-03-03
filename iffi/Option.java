package com.iffi;

import java.time.LocalDate;

public class Option extends Stock implements Value{

	private LocalDate purchaseDate;
	private LocalDate strikeDate;
	protected double strikePricePerShare;	
	protected int shareLimit;
	protected double premiumPerShare;
	private String optionType;
	
	public Option(Stock stock, String optionType, LocalDate purchaseDate, double strikePricePerShare, int shareLimit, double premiumPerShare, LocalDate strikeDate) {
		super(stock.getAssetCode(), stock.getLabel(), stock.getSymbol(), stock.getCurrentValue());
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
	
	public String getOptionType() {
		return optionType;
	}
	
	public double getOptionAmount() {
		return this.getPremiumPerShare() * this.getShareLimit();
	}
		
	public double getValue() {
		if (this.getOptionType().equals("C")) {
			if (this.getCurrentValue() < this.getStrikePricePerShare()) {
				return -100.00;
			}
			else if (this.getCurrentValue() > this.getStrikePricePerShare()) {
				double value = (this.getCurrentValue() - this.getStrikePricePerShare()) * this.getShareLimit();
				double gain = value - (this.getPremiumPerShare() * this.getShareLimit());
				return (gain/value);
			}
		}
		
		else if (this.getOptionType().equals("P")) {
			if (this.getCurrentValue() < this.getStrikePricePerShare()) {
				return 100.00;
			}
			else if (this.getCurrentValue() > this.getStrikePricePerShare()) {
				double value = ((this.getStrikePricePerShare() - this.getCurrentValue()) * this.getShareLimit()) + this.getOptionAmount();
				if(value > 0) {
					return 100.00;
				}
				else if(value == 0) {
					return 0.0;
				}
				else {
					return -100.00;
				}
			}
			
		}
		return 0.0;

				
	}

	
}


