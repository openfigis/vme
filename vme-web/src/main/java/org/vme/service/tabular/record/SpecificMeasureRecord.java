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
		nextRecord.add(sm.getValidityPeriod().getBeginDate());
		nextRecord.add(sm.getValidityPeriod().getEndDate());
		nextRecord.add(sm.getReviewYear());
		if (sm.getInformationSource() == null) {
			nextRecord.add("");
			nextRecord.add("");
		} else {
			if (sm.getInformationSource().getCitation() == null) {
				nextRecord.add("");
			} else {
				nextRecord.add(u.getEnglish(sm.getInformationSource().getCitation()));
			}
			if (sm.getInformationSource().getId() == null) {
				nextRecord.add("");
			} else {
				nextRecord.add(sm.getInformationSource().getId());
			}
		}

		/*
		 * Note: this methods lacks of nextRecord.add(sm.getReviewYear) because
		 * this field (ReviewYear) is missing in Specific Measure, once it`ll be
		 * implemented it could be available
		 */

	}

	@Override
	public Method getSecondLevelMethod() {
		return getMethod(Vme.class, "getSpecificMeasureList");
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "Vme Name", "Measure Specific to this area", "Year", "Begin date", "End date",
				"Review Year", "Source of Information - Citation", "Source ID" };
	}

	@Override
	public void doThirdLevel(Empty p, List<Object> nextRecord) {
		/*
		 * Unusued method
		 */
	}

	@Override
	public Method getThirdLevelMethod() {
		return null;
	}

}
