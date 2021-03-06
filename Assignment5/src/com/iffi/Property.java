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
	private Integer assetId;

	public Property(Integer assetId, String assetCode, String label,  double appraisalValue) {
		super(assetId, assetCode, label, appraisalValue);
	}
	
	public Property(String assetCode, String label, double appraisalValue) {
		this(null, assetCode, label, appraisalValue);
	}
	
	public Property(Property property, LocalDate purchaseDate, double purchasePrise) {
		super(property.getAssetId(), property.getAssetCode(), property.getLabel(),property.getCurrentValue());
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
		return (this.getValue() - this.getCostBasis());
	}

	
	public String toString() {
		return String.format("%s (Property)\n"
				+ "Cost Basis: purchased @ %.2f on %s\n" 
				+ "Value Basis: appraised @ %.2f on TBD\n" 
				+ "Gain: %.2f\n"
				+ "Return Percent: %.2f ", this.getLabel(), this.getCostBasis(), this.getPurchaseDate().toString(), this.getValue(), this.getGain(), this.getReturnPercent());

	}

	public double getCostBasis() {
		return purchasePrise;
	}

	public double getValue() {
		return this.getCurrentValue();
	}

	public double getReturnPercent() {
		return this.getGain()/this.getCostBasis() * 100;
	}



}
