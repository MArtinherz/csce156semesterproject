package com.iffi;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Property")
public class Property extends Asset{
	private double appraisalValue;

	public Property(String assetCode, String label,  double appraisalValue) {
		super(assetCode, label);
		this.appraisalValue = appraisalValue;
	}

	public double getAppraisalValue() {
		return appraisalValue;
	}

}
