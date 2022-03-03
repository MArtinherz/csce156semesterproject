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

	public double getAppraisalValue() {
		return this.getCurrentValue();
	}
	
	public double getPurchasePrise() {
		return this.purchasePrise;
	}

	public String getType() {
		return "Property";
	}


	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}
	
	public double getTotalValue() {
		return (this.getCurrentValue() - this.getPurchasePrise());
	}

	public double getValue() {
		return (this.getTotalValue()/this.getPurchasePrise()) * 100;
	}

}
