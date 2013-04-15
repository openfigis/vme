package org.fao.fi.vme.domain;

public class Meeting {

	private int id;
	private String yearId;
	private String meetingDate;
	private String reportSummary;
	private String committee;
	private String citation;
	private String linkTaggedFile;
	private String linkSource;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYearId() {
		return yearId;
	}

	public void setYearId(String yearId) {
		this.yearId = yearId;
	}

	public String getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getReportSummary() {
		return reportSummary;
	}

	public void setReportSummary(String reportSummary) {
		this.reportSummary = reportSummary;
	}

	public String getCommittee() {
		return committee;
	}

	public void setCommittee(String committee) {
		this.committee = committee;
	}

	public String getCitation() {
		return citation;
	}

	public void setCitation(String citation) {
		this.citation = citation;
	}

	public String getLinkTaggedFile() {
		return linkTaggedFile;
	}

	public void setLinkTaggedFile(String linkTaggedFile) {
		this.linkTaggedFile = linkTaggedFile;
	}

	public String getLinkSource() {
		return linkSource;
	}

	public void setLinkSource(String linkSource) {
		this.linkSource = linkSource;
	}

}
