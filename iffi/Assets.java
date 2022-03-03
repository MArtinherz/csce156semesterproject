package com.iffi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is our List of Asset class. This class stores in a list of assets.
 * 
 * As of now, the list stores just objects but will change to only Assets in the future for storage purposes
 * 
 * @author Martin Herz and Michael Endacott
 *
 */

public class Assets {
	
	private Map<String, Double> assetCurrentPrices;
	
	public Assets() {
		this.assetCurrentPrices = new HashMap<String, Double>();
	}

	public Map<String, Double> getAssetList() {
		return Map.copyOf(this.assetCurrentPrices);
	}
	
	public void addAsset(String assetCode, double currentPrice) {
		this.assetCurrentPrices.put(assetCode, currentPrice);
	}


}
