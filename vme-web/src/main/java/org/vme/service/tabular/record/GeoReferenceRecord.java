package org.vme.service.tabular.record;

import java.lang.reflect.Method;
import java.util.List;

import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Vme;
import org.vme.service.tabular.RecordGenerator;

public class GeoReferenceRecord extends AbstractRecord implements RecordGenerator<Vme, GeoRef> {

	@Override
	public void doFirstLevel(Vme p, List<Object> nextRecord) {
	}

	@Override
	public void doSecondLevel(GeoRef p, List<Object> nextRecord) {
		nextRecord.add(p.getYear());
		nextRecord.add(p.getGeographicFeatureID());
	}

	@Override
	public Method getSecondLevelMethod() {
		return getMethod(Vme.class, "getGeoRefList");
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "Year", "Geographic Feature Id" };
	}

}