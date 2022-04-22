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

public class Stock extends Asset{
	
	public String symbol;
	private LocalDate purchaseDate;
	private double sharePrice;
	private double originalPrice;
	private double shareTotal;
	private double dividend;
	private Integer assetId;

	public Stock(Integer assetId, String assetCode, String label, double sharePrice) {
		super(assetId, assetCode, label, sharePrice);
	}
	
	public Stock(String assetCode, String label, double sharePrice) {
		this(null, assetCode, label, sharePrice);
	}
	
	public Stock(Stock stock, LocalDate purchaseDate, String symbol, double originalPrice, double shareTotal, double dividend) {
		super(stock.getAssetId(), stock.getAssetCode(), stock.getLabel(), stock.getCurrentValue());
		this.symbol = symbol;
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

	public String getType() {
		return "Stock";
	}
	
	

	

	
	public double getGain() {
		return ((this.getCurrentValue() * this.shareTotal) + this.dividend) - this.getCostBasis();
	}

	public double getOriginalPrice() {
		return originalPrice;
	}

	public double getShareTotal() {
		return shareTotal;
	}

	public double getDividend() {
		return dividend;
	}

	public double getValue() {
		return (this.getCurrentValue() * this.shareTotal) + this.dividend;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}


	public String toString() {
		return String.format("%s   %s   %s(%s)\n"
				+ "Cost Basis:  %.2f shares @ %.2f on %s\n"
				+ "Value Basis (including dividend @ %.2f) : %.2f shares @ %.2f on TBD\n"
				+ "        %.2f      $      %.2f",this.getAssetCode(),this.getLabel(),this.getSymbol(),this.getType(),this.getShareTotal(),this.getOriginalPrice(), this.getPurchaseDate().toString(),
				this.getDividend(), this.getShareTotal(),this.getCurrentValue(),this.getReturnPercent(),this.getGain());
	}

	public double getCostBasis() {
		return (this.getOriginalPrice() * this.shareTotal);
	}

	public double getReturnPercent() {
		return this.getGain()/this.getCostBasis() * 100;
	}
}
