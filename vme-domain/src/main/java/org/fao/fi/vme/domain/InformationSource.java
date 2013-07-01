package org.fao.fi.vme.domain;

import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * The source of information available on the level of an RFMO.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@Entity(name = "INFORMATION_SOURCE")

public class InformationSource {

	@Id
	private long id;

	/**
	 * 
	 */
	@ManyToMany
	List<Rfmo> rfmoList;

	/**
	 * InformationSource has 0,1 SpecificMeasures
	 */
	@OneToOne
	private SpecificMeasures specificMeasures;

	/**
	 * InformationSource has 0,1 GeneralMeasures
	 */
	@OneToOne
	private GeneralMeasures generalMeasures;

	/**
	 * Also referred to as issue date of biblio entry
	 */
	@Temporal(TemporalType.DATE)
	private Date date;

	@Temporal(TemporalType.DATE)
	private Date meetingStartDate;

	@Temporal(TemporalType.DATE)
	private Date meetingEndDate;

	/** */
	private String committee;

	/** */
	private String reportSummary;

	/**
	 * The url where the document is to be found
	 */
	private URL url;

	/**
	 * The title
	 */
	private String citation;

	/**
	 * This field maybe used to indicate what type of source this is. One type would be link CEM Source.
	 */
	private int sourceType;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Rfmo> getRfmoList() {
		return rfmoList;
	}

	public void setRfmoList(List<Rfmo> rfmoList) {
		this.rfmoList = rfmoList;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getMeetingStartDate() {
		return meetingStartDate;
	}

	public void setMeetingStartDate(Date meetingStartDate) {
		this.meetingStartDate = meetingStartDate;
	}

	public Date getMeetingEndDate() {
		return meetingEndDate;
	}

	public void setMeetingEndDate(Date meetingEndDate) {
		this.meetingEndDate = meetingEndDate;
	}

	public String getCommittee() {
		return committee;
	}

	public void setCommittee(String committee) {
		this.committee = committee;
	}

	public String getReportSummary() {
		return reportSummary;
	}

	public void setReportSummary(String reportSummary) {
		this.reportSummary = reportSummary;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getCitation() {
		return citation;
	}

	public void setCitation(String citation) {
		this.citation = citation;
	}

	public int getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

}
