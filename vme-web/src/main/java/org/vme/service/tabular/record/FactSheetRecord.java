package org.vme.service.tabular.record;

import java.lang.reflect.Method;
import java.util.List;

import org.fao.fi.figis.domain.VmeObservation;
import org.vme.service.tabular.Empty;
import org.vme.service.tabular.RecordGenerator;

public class FactSheetRecord extends AbstractRecord implements RecordGenerator<VmeContainer, VmeObservation, Empty> {

	@Override
	public void doFirstLevel(VmeContainer p, List<Object> nextRecord) {
		nextRecord.add(u.getEnglish(p.getName()));
	}

	@Override
	public void doSecondLevel(VmeObservation p, List<Object> nextRecord) {
		nextRecord.add(Integer.valueOf(p.getId().getReportingYear()));
//		nextRecord.add("http://figisapps.fao.org/fishery/vme/" + p.getId().getVmeId() + "/"+ p.getId().getObservationId() + "/en");
		nextRecord.add("http://www.fao.org/fishery/vme/" + p.getId().getVmeId() + "/"+ p.getId().getObservationId() + "/en");
	}

	@Override
	public void doThirdLevel(Empty p, List<Object> nextRecord) {

		/*
		 * Unusued method
		 */
		
	}

	@Override
	public Method getSecondLevelMethod() {
		return getMethod(VmeContainer.class, "findVmeObservationByVme");
	}

	@Override
	public Method getThirdLevelMethod() {
		return null;
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "VME Name", "Year", "Fact Sheet URL" };
	}

}
