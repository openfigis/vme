package org.fao.fi.vme.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FishingHistory {

	@Id
	@GeneratedValue
	private int fishingHistoryId;
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
