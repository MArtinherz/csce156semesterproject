package com.iffi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is our List of Asset class. This class stores in a list of assets.
 * 
 * As of now, the list stores just objects but will change to only Assets in the future for storage purposes
 * 
 * @author Martin Herz and Michael Endacott
 *
 */

public class Assets {
	
	private List<Object> assets;

	public Assets() {
		this.assets = new ArrayList<Object>();
	}

	public List<Object> getAssetList() {
		return Collections.unmodifiableList(this.assets);
	}
	
	public void addAsset(Object asset) {
        this.assets.add(asset);
    }

}
