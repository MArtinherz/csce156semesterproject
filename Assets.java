package com.iffi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
