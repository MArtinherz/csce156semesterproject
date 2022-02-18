package com.iffi;

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

	public String getType() {
		return type;
	}
	
	

}
