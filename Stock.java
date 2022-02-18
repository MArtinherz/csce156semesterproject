package com.iffi;

public class Stock extends Asset{
	
	private String symbol;
	private double sharePrice;

	public Stock(String assetCode, String label, String symbol, double sharePrice) {
		super(assetCode, label);
		this.symbol = symbol;
		this.sharePrice = sharePrice;
		
	}

	public String getSymbol() {
		return symbol;
	}

	public double getSharePrice() {
		return sharePrice;
	}
	

}
