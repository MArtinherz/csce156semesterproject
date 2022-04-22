package com.iffi;

/**
 * This is our Asset class.
 * This is an abstract since multiple things can be an asset, therefore its higher up in
 * our class hierarchy
 * 
 * @author Martin Herz
 * @author Michael Endacott
 *
 */

abstract class Asset{
	
	private String assetCode;
	private String label;
	private double currentValue;
	private Integer assetId;
	
	public Asset(Integer assetId, String assetCode, String label, double currentValue) {
		super();
		this.assetCode = assetCode;
		this.label = label;
		this.currentValue = currentValue;
		this.assetId = assetId;
	}
	
	
	public Asset(Asset asset) {
		this.assetCode = asset.assetCode;
		this.label = asset.label;
		this.currentValue = asset.currentValue;
	}


	public String getAssetCode() {
		return assetCode;
	}

	public String getLabel() {
		return label;
	}

	public abstract String getType();
	
	public abstract double getGain();

	public abstract double getCostBasis();
	
	public abstract double getValue();
	
	public double getCurrentValue() {
		return currentValue;
	}
	public abstract double getReturnPercent();
	
	
	public abstract String toString();

	public Integer getAssetId() {
		return assetId;
	}
	
	

}
