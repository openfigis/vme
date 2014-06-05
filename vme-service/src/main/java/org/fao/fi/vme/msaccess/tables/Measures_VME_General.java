package org.fao.fi.vme.msaccess.tables;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.msaccess.mapping.TableDomainMapper;
import org.fao.fi.vme.msaccess.mapping.ValidityPeriodRule;

public class Measures_VME_General implements TableDomainMapper {

	private int ID;
	private String RFB_ID;
	private int Year_ID;
	private String VME_GeneralMeasure_Validity_Start;
	private String VME_GeneralMeasure_Validity_End;
	private String RFB_FishingAreas_coord;
	private String VME_Encounter;
	private String VME_Indicator_Sp;
	private String VME_Threshold;
	// private String Link_CEM_Source;
	private String RFB_Fishing_Areas;
	private String RFB_Exploratory_Fishing_Protocol;
	private int Source_ID;

	public int getSource_ID() {
		return Source_ID;
	}

	public void setSource_ID(int source_ID) {
		Source_ID = source_ID;
	}

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

	// public String getLink_CEM_Source() {
	// return Link_CEM_Source;
	// }
	//
	// public void setLink_CEM_Source(String link_CEM_Source) {
	// Link_CEM_Source = link_CEM_Source;
	// }

	public String getRFB_Fishing_Areas() {
		return RFB_Fishing_Areas;
	}

	public void setRFB_Fishing_Areas(String rFB_Fishing_Areas) {
		RFB_Fishing_Areas = rFB_Fishing_Areas;
	}

	public String getRFB_Exploratory_Fishing_Protocol() {
		return RFB_Exploratory_Fishing_Protocol;
	}

	public void setRFB_Exploratory_Fishing_Protocol(String rFB_Exploratory_Fishing_Protocol) {
		RFB_Exploratory_Fishing_Protocol = rFB_Exploratory_Fishing_Protocol;
	}

	@Override
	public Object map() {

		MultiLingualStringUtil u = new MultiLingualStringUtil();

		GeneralMeasure o = new GeneralMeasure();

		o.setId(Long.valueOf(this.getID()));
		o.setVmeEncounterProtocol(u.english(this.getVME_Encounter()));
		o.setVmeIndicatorSpecies(u.english(this.getVME_Indicator_Sp()));
		o.setExplorataryFishingProtocol(u.english(this.getRFB_Exploratory_Fishing_Protocol()));
		o.setFishingArea(u.english(this.getRFB_Fishing_Areas()));
		o.setVmeThreshold(u.english(this.getVME_Threshold()));

		if (this.Source_ID > 0) {
			InformationSource is = new InformationSource();
			is.setId(Long.valueOf(this.Source_ID));
			List<InformationSource> informationSourceList = new ArrayList<InformationSource>();
			informationSourceList.add(is);
			o.setInformationSourceList(informationSourceList);
		}

		ValidityPeriodRule r = new ValidityPeriodRule(this.VME_GeneralMeasure_Validity_Start,
				VME_GeneralMeasure_Validity_End);
		ValidityPeriod vp = new ValidityPeriod();
		// Intervention Erik van Ingen 5 June 2014. Commented because this logic
		// is not used anymore and therefore does not need to be updated.
		// vp.setBeginYear(r.getStart());
		// vp.setEndYear(r.getEnd());
		o.setValidityPeriod(vp);
		o.setYear(this.Year_ID);
		return o;
	}
}
