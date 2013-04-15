package org.fao.fi.vme.domain;

public class Meeting {

	private int meetingId;
	private int year;
	private String meetingDate;
	private String reportSummary;
	private String committee;
	private String citation;
	private String linkTaggedFile;
	private String linkSource;

	public int getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
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
