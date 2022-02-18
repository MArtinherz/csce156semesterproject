package com.iffi;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Property")
/**
 * This is our Property class.
 * This stores in the data for any type of Property asset
 * And extends off the Asset class
 * 
 * @author Martin Herz 
 * @author Michael Endacott
 *
 */
public class Property extends Asset{
	private double appraisalValue;

	public Property(String assetCode, String label,  double appraisalValue) {
		super(assetCode, label);
		this.appraisalValue = appraisalValue;
	}

	public double getAppraisalValue() {
		return appraisalValue;
	}

	@Override
	public String getType() {
		return "Property";
	}

}
