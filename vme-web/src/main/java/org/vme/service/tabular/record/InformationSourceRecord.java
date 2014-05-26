package org.vme.service.tabular.record;

import java.lang.reflect.Method;
import java.util.List;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.vme.service.tabular.RecordGenerator;

public class InformationSourceRecord extends AbstractRecord implements RecordGenerator<Rfmo, GeneralMeasure, InformationSource>   {

	@Override
	public void doFirstLevel(Rfmo p, List<Object> nextRecord) {
	}

	@Override
	public void doSecondLevel(GeneralMeasure p, List<Object> nextRecord) {
	}

	@Override
	public void doThirdLevel(InformationSource p, List<Object> nextRecord) {
		nextRecord.add(p.getPublicationYear());
		nextRecord.add(p.getMeetingStartDate());
		nextRecord.add(p.getMeetingEndDate());
		nextRecord.add(u.getEnglish(p.getCommittee()));
		nextRecord.add(u.getEnglish(p.getReportSummary()));
		nextRecord.add(p.getUrl().toString());
		nextRecord.add(u.getEnglish(p.getCitation()));
		nextRecord.add(p.getSourceType().getName());
	}

	@Override
	public Method getSecondLevelMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Method getThirdLevelMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "Publication Year" , "Meeting Start Date" , "Meeting End Date" , "Committee" , "Report Summary" ,
				"URL" , "Citation" , "Type Name" };
	}

}
