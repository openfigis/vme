package org.fao.fi.vme.domain.change5;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.MultiLingualString;

public class Report {

	private List<ReportPerObject> reportList = new ArrayList<ReportPerObject>();
	private List<MultiLingualString> floating = new ArrayList<MultiLingualString>();

	public List<ReportPerObject> getReportList() {
		return reportList;
	}

	public void setReportList(List<ReportPerObject> reportList) {
		this.reportList = reportList;
	}

	public List<MultiLingualString> getFloating() {
		return floating;
	}

	public void setFloating(List<MultiLingualString> floating) {
		this.floating = floating;
	}

}
