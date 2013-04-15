package org.fao.fi.vme.domain;

public class SpecificMeasures {

	private ValidityPeriod specificMeasureValidity;
	private String specificMeasure;

	public ValidityPeriod getSpecificMeasureValidity() {
		return specificMeasureValidity;
	}

	public void setSpecificMeasureValidity(ValidityPeriod specificMeasureValidity) {
		this.specificMeasureValidity = specificMeasureValidity;
	}

	public String getSpecificMeasure() {
		return specificMeasure;
	}

	public void setSpecificMeasure(String specificMeasure) {
		this.specificMeasure = specificMeasure;
	}

}
