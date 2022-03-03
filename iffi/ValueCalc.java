package com.iffi;

public class ValueCalc {
	
	public static double value(double purcPrise, double appraisPrice) {
		return (purcPrise - appraisPrice)/purcPrise;
	}
	
	public static double value(double totalCoins, double priceForOne, double exchangeRate, double originalPrice, double originalCoin) {
		double currentP = totalCoins * priceForOne * (1 - exchangeRate);
		double gain = currentP - (originalCoin * originalPrice);
		
		return gain/originalPrice;
	}
	
	public static double value(double totalShares, double originalPrice, double dividend, double currentstPrice) {
		double currentValue = (totalShares * currentstPrice) + dividend;
		double originalValue = (totalShares * originalPrice);
		return (currentValue/originalValue) * 100;
	}
	
	//public static double value() {
	//	
	//}

}
