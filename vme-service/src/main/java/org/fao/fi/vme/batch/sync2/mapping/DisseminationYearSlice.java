package org.fao.fi.vme.batch.sync2.mapping;

import java.util.List;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.History;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;

/**
 * 
 * Hold the slice of information for one disseminationYear.
 * 
 * This information can come from Period objects or from Year objects.
 * 
 * 
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class DisseminationYearSlice {

	// period object
	private int year;

	// period object
	private Vme vme;

	// period object
	private GeneralMeasure generalMeasure;

	// period object
	private SpecificMeasure specificMeasure;

	// year object
	private Profile profile;

	// year object
	private History fisheryAreasHistory;

	// year object
	private History vmesHistory;

	// year object
	private GeoRef geoRef;

	// year object
	private List<InformationSource> informationSourceList;

	public List<InformationSource> getInformationSourceList() {
		return informationSourceList;
	}

	public void setInformationSourceList(List<InformationSource> informationSourceList) {
		this.informationSourceList = informationSourceList;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Vme getVme() {
		return vme;
	}

	public void setVme(Vme vme) {
		this.vme = vme;
	}

	public GeneralMeasure getGeneralMeasure() {
		return generalMeasure;
	}

	public void setGeneralMeasure(GeneralMeasure generalMeasure) {
		this.generalMeasure = generalMeasure;
	}

	public SpecificMeasure getSpecificMeasure() {
		return specificMeasure;
	}

	public void setSpecificMeasure(SpecificMeasure specificMeasure) {
		this.specificMeasure = specificMeasure;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public History getFisheryAreasHistory() {
		return fisheryAreasHistory;
	}

	public void setFisheryAreasHistory(History fisheryAreasHistory) {
		this.fisheryAreasHistory = fisheryAreasHistory;
	}

	public GeoRef getGeoRef() {
		return geoRef;
	}

	public void setGeoRef(GeoRef geoRef) {
		this.geoRef = geoRef;
	}

	public History getVmesHistory() {
		return vmesHistory;
	}

	public void setVmesHistory(History vmesHistory) {
		this.vmesHistory = vmesHistory;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fisheryAreasHistory == null) ? 0 : fisheryAreasHistory.hashCode());
		result = prime * result + ((generalMeasure == null) ? 0 : generalMeasure.hashCode());
		result = prime * result + ((geoRef == null) ? 0 : geoRef.hashCode());
		result = prime * result + ((informationSourceList == null) ? 0 : informationSourceList.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		result = prime * result + ((specificMeasure == null) ? 0 : specificMeasure.hashCode());
		result = prime * result + ((vme == null) ? 0 : vme.hashCode());
		result = prime * result + ((vmesHistory == null) ? 0 : vmesHistory.hashCode());
		return result;
	}

	/**
	 * Note that this equals does not take into account the year! This is done
	 * in order to get the SliceDuplicateFilter to work correctly.
	 * 
	 * Problem with History and Vme, they do not implement the hash or equals
	 * correctly.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DisseminationYearSlice other = (DisseminationYearSlice) obj;
		if (fisheryAreasHistory == null) {
			if (other.fisheryAreasHistory != null)
				return false;
		} else if (!fisheryAreasHistory.equals(other.fisheryAreasHistory))
			return false;
		if (generalMeasure == null) {
			if (other.generalMeasure != null)
				return false;
		} else if (!generalMeasure.equals(other.generalMeasure))
			return false;
		if (geoRef == null) {
			if (other.geoRef != null)
				return false;
		} else if (!geoRef.equals(other.geoRef))
			return false;
		if (informationSourceList == null) {
			if (other.informationSourceList != null)
				return false;
		} else if (!informationSourceList.equals(other.informationSourceList))
			return false;
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		if (specificMeasure == null) {
			if (other.specificMeasure != null)
				return false;
		} else if (!specificMeasure.equals(other.specificMeasure))
			return false;
		if (vme == null) {
			if (other.vme != null)
				return false;
		} else if (!vme.equals(other.vme))
			return false;
		if (vmesHistory == null) {
			if (other.vmesHistory != null)
				return false;
		} else if (!vmesHistory.equals(other.vmesHistory))
			return false;
		return true;
	}

}
