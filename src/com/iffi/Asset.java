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
	
	public Asset(String assetCode, String label, double currentValue) {
		super();
		this.assetCode = assetCode;
		this.label = label;
		this.currentValue = currentValue;
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

	public double getCurrentValue() {
		return currentValue;
	}
	
	public abstract String toString();
	
	

}

