package com.iffi;

import java.time.LocalDate;

public class Call extends Option{

	public Call(Stock stock, LocalDate purchaseDate, double strikePricePerShare, int shareLimit,
			double premiumPerShare, LocalDate strikeDate) {
		super(stock, purchaseDate, strikePricePerShare, shareLimit, premiumPerShare, strikeDate);
	}

	@Override
	
	
	public double getCostBasis() {
		return this.getPremiumPerShare() * this.getShareLimit();
	}
	// Value - cost is gain
	public double getValue() {
		if(this.getCurrentValue() < this.getStrikePricePerShare()) {
			return 0;
		}
		double value = (this.getCurrentValue() - this.getStrikePricePerShare()) * this.getShareLimit();
		return value;
		
	}
	
	public double getGain() {
		
		if (this.getCurrentValue() < this.getStrikePricePerShare()) {				
			return -(this.getCostBasis());
		}
		else if (this.getCurrentValue() >= this.getStrikePricePerShare()) {
			double value = (this.getCurrentValue() - this.getStrikePricePerShare() + this.getPremiumPerShare()) * this.getShareLimit();
			double gain = value - this.getCostBasis();
			return gain;
		}
		return 0.0;
	}

	@Override
	public String toString() {
		// TODO: Generate string for call and put, just like the test cases
		return null;
	}

	@Override
	public String getType() {
		if(this.getValue() == -(this.getCostBasis())) {
			return "Long Call";
		}
		else if(this.getValue() == 0.0) {
			return "";
		}
		return "Short Call";
		
	}



	
	
	

}
