package org.fao.fi.vme.dao.msaccess.tables;

import java.net.MalformedURLException;
import java.net.URL;

import org.fao.fi.vme.dao.msaccess.VmeDaoException;
import org.fao.fi.vme.domain.GeneralMeasures;

public class Measures_VME_General implements TableDomainMapper {

	private int ID;
	private String RFB_ID;
	private int Year_ID;
	private String VME_GeneralMeasure_Validity_Start;
	private String VME_GeneralMeasure_Validity_End;
	private String RFB_FishingAreas;
	private String RFB_FishingAreas_coord;
	private String VME_Encounter;
	private String VME_Indicator_Sp;
	private String VME_Threshold;
	private String Link_CEM_Bookmarked;
	private String Link_CEM_Source;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getRFB_ID() {
		return RFB_ID;
	}

	public void setRFB_ID(String rFB_ID) {
		RFB_ID = rFB_ID;
	}

	public int getYear_ID() {
		return Year_ID;
	}

	public void setYear_ID(int year_ID) {
		Year_ID = year_ID;
	}

	public String getVME_GeneralMeasure_Validity_Start() {
		return VME_GeneralMeasure_Validity_Start;
	}

	public void setVME_GeneralMeasure_Validity_Start(String vME_GeneralMeasure_Validity_Start) {
		VME_GeneralMeasure_Validity_Start = vME_GeneralMeasure_Validity_Start;
	}

	public String getVME_GeneralMeasure_Validity_End() {
		return VME_GeneralMeasure_Validity_End;
	}

	public void setVME_GeneralMeasure_Validity_End(String vME_GeneralMeasure_Validity_End) {
		VME_GeneralMeasure_Validity_End = vME_GeneralMeasure_Validity_End;
	}

	public String getRFB_FishingAreas() {
		return RFB_FishingAreas;
	}

	public void setRFB_FishingAreas(String rFB_FishingAreas) {
		RFB_FishingAreas = rFB_FishingAreas;
	}

	public String getRFB_FishingAreas_coord() {
		return RFB_FishingAreas_coord;
	}

	public void setRFB_FishingAreas_coord(String rFB_FishingAreas_coord) {
		RFB_FishingAreas_coord = rFB_FishingAreas_coord;
	}

	public String getVME_Encounter() {
		return VME_Encounter;
	}

	public void setVME_Encounter(String vME_Encounter) {
		VME_Encounter = vME_Encounter;
	}

	public String getVME_Indicator_Sp() {
		return VME_Indicator_Sp;
	}

	public void setVME_Indicator_Sp(String vME_Indicator_Sp) {
		VME_Indicator_Sp = vME_Indicator_Sp;
	}

	public String getVME_Threshold() {
		return VME_Threshold;
	}

	public void setVME_Threshold(String vME_Threshold) {
		VME_Threshold = vME_Threshold;
	}

	public String getLink_CEM_Bookmarked() {
		return Link_CEM_Bookmarked;
	}

	public void setLink_CEM_Bookmarked(String link_CEM_Bookmarked) {
		Link_CEM_Bookmarked = link_CEM_Bookmarked;
	}

	public String getLink_CEM_Source() {
		return Link_CEM_Source;
	}

	public void setLink_CEM_Source(String link_CEM_Source) {
		Link_CEM_Source = link_CEM_Source;
	}

	@Override
	public Object map() {
		GeneralMeasures o = new GeneralMeasures();
		o.setEncounter(this.getVME_Encounter());
		o.setId(this.getID());
		o.setIndicatorSpecies(this.getVME_Indicator_Sp());

		URL url;
		try {
			url = new URL(this.getLink_CEM_Source());
		} catch (MalformedURLException e) {
			throw new VmeDaoException(e);
		}
		o.getLinkCemSource().setUrl(url);
		o.getLinkCemSource().setCitation(this.getLink_CEM_Bookmarked());

		// o.setPrimairlyConcernedVme(primairlyConcernedVme)
		o.setRfbFishingAreas(this.getRFB_FishingAreas());
		// o.setRfmo(rfmo)
		o.setThreshold(this.getVME_Threshold());
		// o.getValidityPeriod().setBeginYear(this.VME_GeneralMeasure_Validity_Start)
		o.getValidityPeriod().setBeginYear(new Integer(this.VME_GeneralMeasure_Validity_Start).intValue());
		o.getValidityPeriod().setEndYear(new Integer(this.VME_GeneralMeasure_Validity_End).intValue());
		o.setYear(this.Year_ID);
		return o;
	}
}
