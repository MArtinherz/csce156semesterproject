package com.iffi;

import java.time.LocalDate;

/**
 * 
 * @author Martin Herz, Michael Endacott
 * 
 * This is our Put class, which represents one Put and calculates gain and value of put along with string output
 *
 */





public class Put extends Option{

	public Put(Stock stock, LocalDate purchaseDate, double strikePricePerShare, int shareLimit,
			double premiumPerShare, LocalDate strikeDate) {
		super(stock, purchaseDate, strikePricePerShare, shareLimit, premiumPerShare, strikeDate);
		// TODO Auto-generated constructor stub
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
		callbuild.append(this.getReturnPercent() + "% " + " Total Gain: $ " + this.getGain());
		
		return callbuild.toString();
	}

	public String getType() {
		if(this.getValue() == (this.getPremiumPerShare() * this.getShareLimit())) {
			return "Long Put";
		}
		return "Short Put";
	}

	@Override
	public double getCostBasis() {
		return 0;
	}


	public double getGain() {
		return (this.getValue() - this.getCostBasis());
	}


	@Override
	public double getValue() {
		double costTotal = this.getPremiumPerShare() * this.getShareLimit();
		if(this.getCurrentValue() < this.getStrikePricePerShare()) {
			return costTotal;
		}
		double value = ((this.getStrikePricePerShare() - this.getCurrentValue()) * this.getShareLimit()) + costTotal;
		return value;
		
	
	}






	@Override
	public double getReturnPercent() {
		// TODO Auto-generated method stub
		if(this.getValue() > 0) {
			return 100.00;
		}
		else if(this.getValue() < 0) {
			return -100.00;
		}
		
		return 0.0;
	}

}
