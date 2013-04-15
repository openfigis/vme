package org.fao.fi.vme.domain;

public class FishingHistory {

	private int year;
	private String rfbFishingAreaGeneralText;
	private String rfbVmeGeneralText;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getRfbFishingAreaGeneralText() {
		return rfbFishingAreaGeneralText;
	}

	public void setRfbFishingAreaGeneralText(String rfbFishingAreaGeneralText) {
		this.rfbFishingAreaGeneralText = rfbFishingAreaGeneralText;
	}

	public String getRfbVmeGeneralText() {
		return rfbVmeGeneralText;
	}

	public void setRfbVmeGeneralText(String rfbVmeGeneralText) {
		this.rfbVmeGeneralText = rfbVmeGeneralText;
	}

}
