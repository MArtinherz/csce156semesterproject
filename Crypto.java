package com.iffi;


public class Crypto {
	
	private String assetCode;
	private String label;
	private double exchangeRate;
	private double exchangeFeeRate;

	public Crypto(String assetCode, String label,  double exchangeRate, double exchangeFeeRate) {
		this.assetCode = assetCode;
		this.label = label;
		this.exchangeRate = exchangeRate;
		this.exchangeFeeRate = exchangeFeeRate;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public double getExchangeFeeRate() {
		return exchangeFeeRate;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public String getLabel() {
		return label;
	}
	
}
