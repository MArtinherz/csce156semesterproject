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
	
	public String getSymbol() {
		return symbol;
	}
	
	public Stock(Stock stock, LocalDate purchaseDate, double originalPrice, int shareTotal, double dividend) {
		super(stock.getAssetCode(), stock.getLabel(),stock.getCurrentValue());
		this.originalPrice = originalPrice;
		this.shareTotal = shareTotal;
		this.dividend = dividend;
		this.purchaseDate = purchaseDate;
		
	}
 
	public double getSharePrice() {
		return sharePrice;
	}
 
	public String getType() {
		return "Stock";
	}
	
	
 
	
 
	
	public double getGain() {
		return ((this.getCurrentValue() * this.shareTotal) + this.dividend);
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
 
	public double getReturnPercent() {
		return ((this.getGain() - this.getOrigPrice())/this.getOrigPrice()) * 100;
	}
 
	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}
 
	public double getOrigPrice() {
		return (this.getOriginalPrice() * this.shareTotal);
	}
 
//	public String toString() {
//		return String.format("Old value of %s: $%f for %f stocks \nCurrent value, including dividend of %s: $%f for %f stocks \n"
//				+ "Total Value of Investment: %f percent", this.getLabel(), this.getOriginalPrice(), this.getShareTotal(), this.getLabel(),
//				this.getGain(), this.getShareTotal(), this.getValue());
//	}
	public String toString() {
		return String.format("%s   %s   %s(%s)\n"
				+ "Cost Basis:  %f shares @ %f on TBD\n"
				+ "Value Basis: %f shares @ %f"
				+ "        %f      $      %f",this.getAssetCode(),this.getLabel(),this.getSymbol(),this.getType(),this.getShareTotal(),this.getOriginalPrice(),
				this.getShareTotal(),this.getCurrentValue(),this.getReturnPercent(),this.getGain());
	}
}
