package org.fao.fi.vme.sync2.mapping;

import java.util.List;

import org.fao.fi.vme.domain.GeneralMeasure;
import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.domain.History;
import org.fao.fi.vme.domain.InformationSource;
import org.fao.fi.vme.domain.Profile;
import org.fao.fi.vme.domain.SpecificMeasure;
import org.fao.fi.vme.domain.Vme;

/**
 * 
 * Hold the slice of information for one disseminationYear.
 * 
 * This information can come from Period objects or from Year objects.
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
	private GeneralMeasure generalMeasures;

	// period object
	private SpecificMeasure specificMeasures;

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

	public GeneralMeasure getGeneralMeasures() {
		return generalMeasures;
	}

	public void setGeneralMeasures(GeneralMeasure generalMeasures) {
		this.generalMeasures = generalMeasures;
	}

	public SpecificMeasure getSpecificMeasures() {
		return specificMeasures;
	}

	public void setSpecificMeasures(SpecificMeasure specificMeasures) {
		this.specificMeasures = specificMeasures;
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
}
