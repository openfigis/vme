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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FishingHistory other = (FishingHistory) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
