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
		nextRecord.add(u.getEnglish(sm.getInformationSource().getCitation()));
		nextRecord.add(sm.getInformationSource().getId());
		
		/*
		 *  Note: this methods lacks of nextRecord.add(sm.getReviewYear) because
		 *  this field (ReviewYear) is missing in Specific Measure, once it`ll be
		 *  implemented it could be available
		 */
		
	}

	@Override
	public Method getSecondLevelMethod() {
		return getMethod(Vme.class, "getSpecificMeasureList");
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "Vme Name", "Measure Specific to this area", "Year", "Begin year", "End year", "Citation" , "Source ID" , "Review Year" };
	}

	@Override
	public void doThirdLevel(Empty p, List<Object> nextRecord) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Method getThirdLevelMethod() {
		// TODO Auto-generated method stub
		return null;
	}

}
