package org.vme.service.tabular.record;

import java.lang.reflect.Method;
import java.util.List;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.Rfmo;
import org.vme.service.tabular.RecordGenerator;

public class GeneralMeasureRecord extends AbstractRecord implements RecordGenerator<Rfmo, GeneralMeasure> {

	@Override
	public void doFirstLevel(Rfmo p, List<Object> nextRecord) {
	}

	@Override
	public void doSecondLevel(GeneralMeasure p, List<Object> nextRecord) {
		nextRecord.add(p.getYear());
		nextRecord.add(u.getEnglish(p.getFishingArea()));
		nextRecord.add(u.getEnglish(p.getExplorataryFishingProtocol()));
		nextRecord.add(u.getEnglish(p.getVmeEncounterProtocol()));
		nextRecord.add(u.getEnglish(p.getVmeIndicatorSpecies()));
		nextRecord.add(u.getEnglish(p.getVmeThreshold()));
		nextRecord.add(p.getValidityPeriod().getBeginYear());
		nextRecord.add(p.getValidityPeriod().getEndYear());
	}

	@Override
	public Method getSecondLevelMethod() {
		return getMethod(Rfmo.class, "getGeneralMeasureList");
	}

	@Override
	public Method getThirdLevelMethod() {
		return null;
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "Year", "getFishingArea", "getExplorataryFishingProtocol", "getVmeEncounterProtocol",
				"getVmeIndicatorSpecies", "getVmeThreshold", "Begin Year", "End Year" };
	}
}
