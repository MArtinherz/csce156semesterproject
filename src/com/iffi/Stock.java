package com.iffi;

/**
 * This is our Stock class.
 * This stores in the data for any type of Stock asset
 * And extends off the Asset class
 * 
 * @author Martin Herz 
 * @author Michael Endacott
 *
 */

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

	@Override
	public String getType() {
		return "Stock";
	}
	

}
