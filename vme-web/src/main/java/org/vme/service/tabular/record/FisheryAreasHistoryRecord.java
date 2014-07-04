package org.vme.service.tabular.record;

import java.lang.reflect.Method;
import java.util.List;

import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.vme.service.tabular.Empty;
import org.vme.service.tabular.RecordGenerator;

public class FisheryAreasHistoryRecord extends AbstractRecord implements RecordGenerator<Rfmo, FisheryAreasHistory, Empty> {

	@Override
	public void doFirstLevel(Rfmo p, List<Object> nextRecord) {
		/*
		 * Unusued method
		 */
	}

	@Override
	public void doSecondLevel(FisheryAreasHistory p, List<Object> nextRecord) {
		nextRecord.add(p.getYear());
		nextRecord.add(u.getEnglish(p.getHistory()));

	}

	@Override
	public Method getSecondLevelMethod() {
		return getMethod(Rfmo.class, "getHasFisheryAreasHistory");
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "Year", "Overview of Bottom fishing areas" };
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
