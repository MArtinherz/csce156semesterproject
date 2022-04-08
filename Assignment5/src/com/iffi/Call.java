package com.iffi;

import java.time.LocalDate;

/**
 * 
 * @author Martin Herz, Michael Endacott
 * 
 * This is our Call class, which represents one Call and calculates gain and value of put along with string output
 *
 */




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
		return (this.getValue() - this.getCostBasis());
	}



	public String getType() {
		if(this.getValue() == -(this.getCostBasis())) {
			return "Long Call (Not Executed)";
		}
		else if(this.getGain() == 0.0) {
			return "";
		}
		return "Short Call";
		
	}
	
	public String toString() {
		StringBuilder callbuild = new StringBuilder();
		callbuild.append(this.getAssetCode() + " " + this.getLabel());
		callbuild.append("\n");

		callbuild.append("Buy upto " + this.getShareLimit() + " @" + this.getStrikePricePerShare() + " until " + this.getStrikeDate().toString());
		callbuild.append("\n");

		callbuild.append("Premium of " + this.getPremiumPerShare() + " per share " + "(" + this.getCostBasis() + ")");
		callbuild.append("\n");

		callbuild.append(this.getType() + " Value: " +  this.getShareLimit() + " shares");
		callbuild.append("\n");

		
		callbuild.append("@ " + this.getCurrentValue() + " - " + this.getStrikePricePerShare() + " - " + this.getPremiumPerShare() + " = " + this.getValue() );
		callbuild.append("\n");
		callbuild.append(String.format(" %.2f percent  Total Gain: $ %.2f", this.getReturnPercent(), this.getGain()));
		
		return callbuild.toString();
		
	}

	public Stock getStock() {
		return this.stock;
	}
	
	public double getReturnPercent() {
		return (this.getGain()/this.getCostBasis()) * 100;
	}



	
	
	

}
