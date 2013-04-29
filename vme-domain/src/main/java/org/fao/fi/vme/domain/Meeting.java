package org.fao.fi.vme.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * 
 * 
 * @author Erik van Ingen
 */
@Entity
public class Meeting {

	@Id
	private int id;

	/** 
	 */
	@Temporal(TemporalType.DATE)
	private Date start;

	@Temporal(TemporalType.DATE)
	private Date end;

	/**
	 * 
	 */
	@OneToOne
	private SpecificMeasures specificMeasures;

	/**
	 * 
	 */
	@OneToOne
	private GeneralMeasures generalMeasures;

	@ManyToOne
	private Rfmo rfmo;

	/** */
	private String reportSummary;

	/** */
	private String committee;

	/** */
	private int year;

	/**
	 * meetingDocument
	 */
	public Source meetingDocument = new Source();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public SpecificMeasures getSpecificMeasures() {
		return specificMeasures;
	}

	public void setSpecificMeasures(SpecificMeasures specificMeasures) {
		this.specificMeasures = specificMeasures;
	}

	public GeneralMeasures getGeneralMeasures() {
		return generalMeasures;
	}

	public void setGeneralMeasures(GeneralMeasures generalMeasures) {
		this.generalMeasures = generalMeasures;
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Source getMeetingDocument() {
		return meetingDocument;
	}

	public void setMeetingDocument(Source meetingDocument) {
		this.meetingDocument = meetingDocument;
	}

	public Rfmo getRfmo() {
		return rfmo;
	}

	public void setRfmo(Rfmo rfmo) {
		this.rfmo = rfmo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meeting other = (Meeting) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
