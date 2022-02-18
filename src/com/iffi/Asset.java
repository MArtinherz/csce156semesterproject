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

abstract class Asset {
	
	private String assetCode;
	private String label;
	
	public Asset(String assetCode, String label) {
		super();
		this.assetCode = assetCode;
		this.label = label;
	}


	public String getAssetCode() {
		return assetCode;
	}

	public String getLabel() {
		return label;
	}

	public abstract String getType();
	
	

}
