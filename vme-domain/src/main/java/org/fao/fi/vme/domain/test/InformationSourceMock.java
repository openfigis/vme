package org.fao.fi.vme.domain.test;

import java.net.URL;
import java.util.Calendar;

import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.InformationSourceType;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class InformationSourceMock {
	public static final String CIT = "RFMO Conservation and Enforcement Measure  (Doc No. ####)";
	public static final int YEAR = 2000;
	private static MultiLingualStringUtil u = new MultiLingualStringUtil();
	
	static private InformationSourceType INSTANCE = null;

	public static InformationSource create() {
		InformationSource is = new InformationSource();
		is.setSourceType(createInformationSourceType());
		is.setPublicationYear(2000);
		is.setCitation(u.english(CIT));
		is.setMeetingEndDate(Calendar.getInstance().getTime());
		try {
			is.setUrl(new URL("http://www.rfmo.org"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		is.setCommittee(u.english("Regional Fishery Management Organization (RFMO)"));
		is.setReportSummary(u.english("This is an abstract (report summary)"));

		return is;
	}

	public static InformationSourceType createInformationSourceType() {
		if(INSTANCE == null)
			INSTANCE = new InformationSourceType(2L, "Meeting documents", InformationSourceType.IS_A_MEETING_DOCUMENT);
		
		return INSTANCE;
	}
}
