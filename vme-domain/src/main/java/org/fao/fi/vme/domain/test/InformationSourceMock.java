package org.fao.fi.vme.domain.test;

import java.net.URL;
import java.util.Calendar;

import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class InformationSourceMock {
	public static final String CIT = "RFMO Conservation and Enforcement Measure  (Doc No. ####)";
	private static MultiLingualStringUtil u = new MultiLingualStringUtil();

	public static InformationSource create() {
		InformationSource is = new InformationSource();
		is.setSourceType(3);
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

}
