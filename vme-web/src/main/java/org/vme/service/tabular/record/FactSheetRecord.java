package org.vme.service.tabular.record;

import java.lang.reflect.Method;
import java.util.List;

import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.Lang;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.service.tabular.Empty;
import org.vme.service.tabular.RecordGenerator;

public class FactSheetRecord extends AbstractRecord implements RecordGenerator<Vme, VmeObservation, Empty> {

	@Override
	public void doFirstLevel(Vme p, List<Object> nextRecord) {
		nextRecord.add(p.getName());
	}

	@Override
	public void doSecondLevel(VmeObservation p, List<Object> nextRecord) {
		nextRecord.add(p.getId().getReportingYear());
		nextRecord.add("http://figisapps.fao.org/fishery/vme/"+p.getId().getVmeId()+"/"+p.getId().getObservationId()+"/"+Lang.EN);
		
	}

	@Override
	public void doThirdLevel(Empty p, List<Object> nextRecord) {

	}

	@Override
	public Method getSecondLevelMethod() {
		return getMethod(VmeContainer.class, "findVmeObservationByVme");
	}

	@Override
	public Method getThirdLevelMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "VME Name" , "Year" , "Fact Sheet URL"};
	}

}
