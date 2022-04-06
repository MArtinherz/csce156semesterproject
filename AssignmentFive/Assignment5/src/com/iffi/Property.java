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
public class Property extends Asset{
	
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


	


	public String getType() {
		return "Property";
	}


	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}
	
	public double getGain() {
		return (this.getCurrentValue() - this.getCostBasis());
	}

	
	public String toString() {
		return "";
//		return String.format("Old value of %s, purchased on %s: $%f   \nCurrent value of %s: $%f   \n"
//				+ "Total Value of Investment: %f percent", this.getLabel(), this.getPurchaseDate().toString(), this.getOrigPrice(),  this.getLabel(),
//				this.getCurrentValue(), this.getValue());
	}

	@Override
	public double getCostBasis() {
		// TODO Auto-generated method stub
		return purchasePrise;
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return appraisalValue;
	}

}
