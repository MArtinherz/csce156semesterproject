package com.iffi;

import java.time.LocalDate;

public class Put extends Option{

	public Put(Stock stock, LocalDate purchaseDate, double strikePricePerShare, int shareLimit,
			double premiumPerShare, LocalDate strikeDate) {
		super(stock, purchaseDate, strikePricePerShare, shareLimit, premiumPerShare, strikeDate);
		// TODO Auto-generated constructor stub
	}

	

	
	@Override
	public double getGain() {
		
		if (this.getCurrentValue() < this.getStrikePricePerShare()) {				
			return -(this.getCostBasis());
		}
		else if (this.getCurrentValue() >= this.getStrikePricePerShare()) {
			double value = (this.getStrikePricePerShare() - this.getCurrentValue() + this.getPremiumPerShare()) * this.getShareLimit();
			double stockTotal = this.getCurrentValue() * this.getShareLimit();
			return (stockTotal);
		}
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getType() {
		if(this.getValue() == (this.getPremiumPerShare() * this.getShareLimit())) {
			return "Long Put";
		}
		// TODO Auto-generated method stub
		return "Short Put";
	}

	@Override
	public double getCostBasis() {
		return 0;
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

}
