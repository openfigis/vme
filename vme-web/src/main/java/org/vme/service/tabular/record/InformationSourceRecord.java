package org.vme.service.tabular.record;

import java.lang.reflect.Method;
import java.util.List;

import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.vme.service.tabular.RecordGenerator;

public class InformationSourceRecord extends AbstractRecord implements RecordGenerator<Rfmo, InformationSource> {

	@Override
	public void doFirstLevel(Rfmo p, List<Object> nextRecord) {
	}

	@Override
	public void doSecondLevel(InformationSource p, List<Object> nextRecord) {
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
		return getMethod(Rfmo.class, "getInformationSourceList");
	}

	@Override
	public Method getThirdLevelMethod() {
		return null;
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "Publication Year", "Meeting Start Date", "Meeting End Date", "Committee",
				"Report Summary", "URL", "Citation", "Type Name" };
	}

}
