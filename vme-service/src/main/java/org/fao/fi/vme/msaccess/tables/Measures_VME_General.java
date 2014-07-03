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
	private String rfbFishingAreas;
	private String rfbExploratoryFishingProtocol;
	private int sourceId;

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public int getID() {
		return iD;
	}

	public void setID(int iD) {
		this.iD = iD;
	}

	public String getRfbId() {
		return rfbId;
	}

	public void setRfbId(String rfbId) {
		this.rfbId = rfbId;
	}

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public String getVmeGeneralMeasureValidityStart() {
		return vmeGeneralMeasureValidityStart;
	}

	public void setVmeGeneralMeasureValidityStart(String vmeGeneralMeasureValidityStart) {
		this.vmeGeneralMeasureValidityStart = vmeGeneralMeasureValidityStart;
	}

	public String getVmeGeneralMeasureValidityEnd() {
		return vmeGeneralMeasureValidityEnd;
	}

	public void setVmeGeneralMeasureValidityEnd(String vmeGeneralMeasureValidityEnd) {
		this.vmeGeneralMeasureValidityEnd = vmeGeneralMeasureValidityEnd;
	}

	public String getRfbFishingAreasCoord() {
		return rfbFishingAreasCoord;
	}

	public void setRfbFishingAreasCoord(String rfbFishingAreasCoord) {
		this.rfbFishingAreasCoord = rfbFishingAreasCoord;
	}

	public String getVmeEncounter() {
		return vmeEncounter;
	}

	public void setVmeEncounter(String vmeEncounter) {
		this.vmeEncounter = vmeEncounter;
	}

	public String getVmeIndicatorSp() {
		return vmeIndicatorSp;
	}

	public void setVmeIndicatorSp(String vmeIndicatorSp) {
		this.vmeIndicatorSp = vmeIndicatorSp;
	}

	public String getVmeThreshold() {
		return vmeThreshold;
	}

	public void setVmeThreshold(String vmeThreshold) {
		this.vmeThreshold = vmeThreshold;
	}

	public String getRfbFishingAreas() {
		return rfbFishingAreas;
	}

	public void setRfbFishingAreas(String rfbFishingAreas) {
		this.rfbFishingAreas = rfbFishingAreas;
	}

	public String getRfbExploratoryFishingProtocol() {
		return rfbExploratoryFishingProtocol;
	}

	public void setRfbExploratoryFishingProtocol(String rfbExploratoryFishingProtocol) {
		this.rfbExploratoryFishingProtocol = rfbExploratoryFishingProtocol;
	}

	@Override
	public Object map() {

		MultiLingualStringUtil u = new MultiLingualStringUtil();

		GeneralMeasure o = new GeneralMeasure();

		o.setId(Long.valueOf(this.getID()));
		o.setVmeEncounterProtocol(u.english(this.getVmeEncounter()));
		o.setVmeIndicatorSpecies(u.english(this.getVmeIndicatorSp()));
		o.setExplorataryFishingProtocol(u.english(this.getRfbExploratoryFishingProtocol()));
		o.setFishingArea(u.english(this.getRfbFishingAreas()));
		o.setVmeThreshold(u.english(this.getVmeThreshold()));

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
		o.setValidityPeriod(vp);
		o.setYear(this.yearId);
		return o;
	}
}
