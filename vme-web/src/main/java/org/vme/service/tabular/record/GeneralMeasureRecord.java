package org.vme.service.tabular.record;

import java.lang.reflect.Method;
import java.util.List;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.vme.service.tabular.RecordGenerator;

public class GeneralMeasureRecord extends AbstractRecord implements
		RecordGenerator<Rfmo, GeneralMeasure, InformationSource> {

	@Override
	public void doFirstLevel(Rfmo p, List<Object> nextRecord) {
	}

	@Override
	public void doSecondLevel(GeneralMeasure p, List<Object> nextRecord) {
		nextRecord.add(p.getYear());
		nextRecord.add(p.getValidityPeriod().getBeginDate());
		nextRecord.add(p.getValidityPeriod().getEndDate());
		nextRecord.add(u.getEnglish(p.getFishingArea()));
		nextRecord.add(u.getEnglish(p.getExplorataryFishingProtocol()));
		nextRecord.add(u.getEnglish(p.getVmeEncounterProtocol()));
		nextRecord.add(u.getEnglish(p.getVmeIndicatorSpecies()));
		nextRecord.add(u.getEnglish(p.getVmeThreshold()));

	}

	@Override
	public void doThirdLevel(InformationSource p, List<Object> nextRecord) {
		nextRecord.add(u.getEnglish(p.getCitation()));
		nextRecord.add(p.getId());
	}

	@Override
	public Method getSecondLevelMethod() {
		return getMethod(Rfmo.class, "getGeneralMeasureList");
	}

	@Override
	public Method getThirdLevelMethod() {
		return getMethod(GeneralMeasure.class, "getInformationSourceList");
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "Year", "Begin date", "End date", "Fishing areas", "Exploratory fishing protocol",
				"Encounter protocols", "Indicator species", "Threshold", "Source of Information - Citation",
				"Source ID" };
	}
}
