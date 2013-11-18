package org.fao.fi.vme.domain.test;

import java.net.URL;
import java.util.Calendar;

import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class InformationSourceMock {
	private static MultiLingualStringUtil u = new MultiLingualStringUtil();

	public static InformationSource create() {
		InformationSource is = new InformationSource();
		is.setSourceType(2);
		is.setCitation(u.english("RFMO Conservation and Enforcement Measure  (Doc No. ####)"));
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
