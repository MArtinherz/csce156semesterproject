package com.iffi;

import java.time.LocalDate;

/**
 * This is our Property class.
 * This stores in the data for any type of Property asset
 * And extends off the Asset class
 * 
 * @author Martin Herz 
 * @author Michael Endacott
 *
 */
public class Property extends Asset implements Value{
	
	private LocalDate purchaseDate;
	private double purchasePrise;
	private double appraisalValue;

	public Property(String assetCode, String label,  double appraisalValue) {
		super(assetCode, label, appraisalValue);

	}
	
	public Property(Property property, LocalDate purchaseDate, double purchasePrise) {
		super(property.getAssetCode(), property.getLabel(),property.getCurrentValue());
		this.purchaseDate = purchaseDate;
		this.purchasePrise = purchasePrise;
	}


//	public String getCode() {
//		return assetCode;
//	}


	public String getType() {
		return "Property";
	}


	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}
	
	public double getGain() {
		return (this.getCurrentValue() - this.getOrigPrice());
	}

	public double getReturnPercent() {
		return (this.getGain()/this.getOrigPrice()) * 100;
	}

	public double getOrigPrice() {
		return purchasePrise;
	}
	
	public String toString() {
//		return String.format("Old value of %s: $%f   \nCurrent value of %s: $%f   \n"
//				+ "Total Value of Investment: %f percent", this.getLabel(), this.getOrigPrice(),  this.getLabel(),
//				this.getCurrentValue(), this.getReturnPercent());
		return String.format("%s  %s  %s\n"
				+ 			"Cost Basis:  Purchased @ $%f on %s\n"
				+ 			"Value Basis: Appraised @ $%f    "
				+ 			"      %f   $    %f",this.getAssetCode(),this.getLabel(),this.getType(),this.getOrigPrice(),this.getPurchaseDate().toString(),
				this.getCurrentValue(),this.getReturnPercent(),this.getCurrentValue());
	}

}

