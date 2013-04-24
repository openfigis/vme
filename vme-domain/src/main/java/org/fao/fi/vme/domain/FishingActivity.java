package org.fao.fi.vme.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity
public class FishingActivity {

	/** 
	 * 
	 */
	@Id
	private int id;

	private Date updatedInYear;

	/** */
	private String rfbFishingAreaGeneralText;

	/** */
	private String vmeGeneralText;

	public int getFishingActivityId() {
		return id;
	}

	public void setFishingActivityId(int fishingActivityId) {
		this.id = fishingActivityId;
	}

	public Date getUpdatedInYear() {
		return updatedInYear;
	}

	public void setUpdatedInYear(Date updatedInYear) {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
