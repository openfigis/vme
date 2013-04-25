package org.fao.fi.vme.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class FishingHistory {

	/** 
	 * 
	 */
	@Id
	private int id;

	private int updatedInYear;

	/** */
	private String rfbFishingAreaGeneralText;

	/** */
	private String vmeGeneralText;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUpdatedInYear() {
		return updatedInYear;
	}

	public void setUpdatedInYear(int updatedInYear) {
		this.updatedInYear = updatedInYear;
	}

	public String getRfbFishingAreaGeneralText() {
		return rfbFishingAreaGeneralText;
	}

	public void setRfbFishingAreaGeneralText(String rfbFishingAreaGeneralText) {
		this.rfbFishingAreaGeneralText = rfbFishingAreaGeneralText;
	}

	public String getVmeGeneralText() {
		return vmeGeneralText;
	}

	public void setVmeGeneralText(String vmeGeneralText) {
		this.vmeGeneralText = vmeGeneralText;
	}

}
