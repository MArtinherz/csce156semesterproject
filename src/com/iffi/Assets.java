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

public class Assets <Asset>{
	
	private List clientAssets;
	
	public Assets() {
		this.clientAssets = new ArrayList();
	}


	
	public void addAsset(Asset t) {
		this.clientAssets.add(t);
	}


}
