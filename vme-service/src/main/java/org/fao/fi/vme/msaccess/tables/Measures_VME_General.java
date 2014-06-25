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

	private int iD;
	private String rfbId;
	private int yearId;
	private String vmeGeneralMeasureValidityStart;
	private String vmeGeneralMeasureValidityEnd;
	private String rfbFishingAreasCoord;
	private String vmeEncounter;
	private String vmeIndicatorSp;
	private String vmeThreshold;
	// private String Link_CEM_Source;
	private String rfbFishingAreas;
	private String rfbExploratoryFishingProtocol;
	private int sourceId;

	public int getSource_ID() {
		return sourceId;
	}

	public void setSource_ID(int sourceId) {
		this.sourceId = sourceId;
	}

	public int getID() {
		return iD;
	}

	public void setID(int iD) {
		this.iD = iD;
	}

	public String getRFB_ID() {
		return rfbId;
	}

	public void setRFB_ID(String rfbId) {
		this.rfbId = rfbId;
	}

	public int getYear_ID() {
		return yearId;
	}

	public void setYear_ID(int yearId) {
		this.yearId = yearId;
	}

	public String getVME_GeneralMeasure_Validity_Start() {
		return vmeGeneralMeasureValidityStart;
	}

	public void setVME_GeneralMeasure_Validity_Start(String vmeGeneralMeasureValidityStart) {
		this.vmeGeneralMeasureValidityStart = vmeGeneralMeasureValidityStart;
	}

	public String getVME_GeneralMeasure_Validity_End() {
		return vmeGeneralMeasureValidityEnd;
	}

	public void setVME_GeneralMeasure_Validity_End(String vmeGeneralMeasureValidityEnd) {
		this.vmeGeneralMeasureValidityEnd = vmeGeneralMeasureValidityEnd;
	}

	public String getRFB_FishingAreas_coord() {
		return rfbFishingAreasCoord;
	}

	public void setRFB_FishingAreas_coord(String rfbFishingAreasCoord) {
		this.rfbFishingAreasCoord = rfbFishingAreasCoord;
	}

	public String getVME_Encounter() {
		return vmeEncounter;
	}

	public void setVME_Encounter(String vmeEncounter) {
		this.vmeEncounter = vmeEncounter;
	}

	public String getVME_Indicator_Sp() {
		return vmeIndicatorSp;
	}

	public void setVME_Indicator_Sp(String vmeIndicatorSp) {
		this.vmeIndicatorSp = vmeIndicatorSp;
	}

	public String getVME_Threshold() {
		return vmeThreshold;
	}

	public void setVME_Threshold(String vmeThreshold) {
		this.vmeThreshold = vmeThreshold;
	}

	// public String getLink_CEM_Source() {
	// return Link_CEM_Source;
	// }
	//
	// public void setLink_CEM_Source(String link_CEM_Source) {
	// Link_CEM_Source = link_CEM_Source;
	// }

	public String getRFB_Fishing_Areas() {
		return rfbFishingAreas;
	}

	public void setRFB_Fishing_Areas(String rfbFishingAreas) {
		this.rfbFishingAreas = rfbFishingAreas;
	}

	public String getRFB_Exploratory_Fishing_Protocol() {
		return rfbExploratoryFishingProtocol;
	}

	public void setRFB_Exploratory_Fishing_Protocol(String rfbExploratoryFishingProtocol) {
		this.rfbExploratoryFishingProtocol = rfbExploratoryFishingProtocol;
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

		if (this.sourceId > 0) {
			InformationSource is = new InformationSource();
			is.setId(Long.valueOf(this.sourceId));
			List<InformationSource> informationSourceList = new ArrayList<InformationSource>();
			informationSourceList.add(is);
			o.setInformationSourceList(informationSourceList);
		}

		ValidityPeriodRule r = new ValidityPeriodRule(this.vmeGeneralMeasureValidityStart,
				vmeGeneralMeasureValidityEnd);
		ValidityPeriod vp = new ValidityPeriod();
		// Intervention Erik van Ingen 5 June 2014. Commented because this logic
		// is not used anymore and therefore does not need to be updated.
		// vp.setBeginYear(r.getStart());
		// vp.setEndYear(r.getEnd());
		o.setValidityPeriod(vp);
		o.setYear(this.yearId);
		return o;
	}
}
