package org.vme.service.tabular.record;

import java.lang.reflect.Method;
import java.util.List;

import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.vme.service.tabular.Empty;
import org.vme.service.tabular.RecordGenerator;

public class SpecificMeasureRecord extends AbstractRecord implements RecordGenerator<Vme, SpecificMeasure, Empty> {

	@Override
	public void doFirstLevel(Vme v, List<Object> nextRecord) {
		nextRecord.add(u.getEnglish(v.getName()));
	}

	@Override
	public void doSecondLevel(SpecificMeasure sm, List<Object> nextRecord) {
		nextRecord.add(u.getEnglish(sm.getVmeSpecificMeasure()));
		nextRecord.add(sm.getYear());
		nextRecord.add(sm.getValidityPeriod().getBeginYear());
		nextRecord.add(sm.getValidityPeriod().getEndYear());
		nextRecord.add(sm.getInformationSource().getUrl().toString());
	}

	@Override
	public void doThirdLevel(Empty p, List<Object> nextRecord) {

	}

	@Override
	public Method getSecondLevelMethod() {
		return getMethod("getSpecificMeasureList");
	}

	@Override
	public Method getThirdLevelMethod() {
		return null;
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "Vme Name", "SpecificMeasure", "Year", "Begin year", "End year", "Information Source URL" };
	}

}
