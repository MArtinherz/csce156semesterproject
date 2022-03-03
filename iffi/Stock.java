package com.iffi;

import java.time.LocalDate;

/**
 * This is our Stock class.
 * This stores in the data for any type of Stock asset
 * And extends off the Asset class
 * 
 * @author Martin Herz 
 * @author Michael Endacott
 *
 */

public class Stock extends Asset implements Value{
	
	private String symbol;
	private LocalDate purchaseDate;
	private double sharePrice;
	private double originalPrice;
	private double shareTotal;
	private double dividend;

	public Stock(String assetCode, String label, String symbol, double sharePrice) {
		super(assetCode, label, sharePrice);
		this.symbol = symbol;		
	}
	
	public Stock(Stock stock, LocalDate purchaseDate, double originalPrice, int shareTotal, double dividend) {
		super(stock.getAssetCode(), stock.getLabel(), stock.getCurrentValue());
		this.originalPrice = originalPrice;
		this.shareTotal = shareTotal;
		this.dividend = dividend;
		this.purchaseDate = purchaseDate;
		
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
	
	public double getcurrentStockValue() {
		return (this.getCurrentValue() * this.shareTotal) + this.dividend;
	}
	
	public double getOrStockValue() {
		return (this.originalPrice * this.shareTotal);
	}

	public double getValue() {
		return ((this.getcurrentStockValue() - this.getOrStockValue())/this.getOrStockValue()) * 100;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}
	

}
