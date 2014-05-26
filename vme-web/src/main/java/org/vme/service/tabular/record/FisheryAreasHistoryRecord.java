package org.vme.service.tabular.record;

import java.lang.reflect.Method;
import java.util.List;

import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.vme.service.tabular.RecordGenerator;

public class FisheryAreasHistoryRecord extends AbstractRecord implements RecordGenerator<Rfmo, FisheryAreasHistory> {

	@Override
	public void doFirstLevel(Rfmo p, List<Object> nextRecord) {
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
	public Method getThirdLevelMethod() {
		return null;
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "Year", "Fishing Area History" };
	}

}
