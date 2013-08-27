package org.fao.fi.vme.domain;

import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private Long id;

	/**
	 * 
	 */

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Rfmo> rfmoList;

	/**
	 * InformationSource has 0,1 SpecificMeasure
	 */
	@OneToOne
	private SpecificMeasure specificMeasure;

	/**
	 * InformationSource has 0,1 GeneralMeasure
	 */
	@OneToOne
	private GeneralMeasure generalMeasure;

	/**
	 * Also referred to as issue date of biblio entry
	 * 
	 * TODO change into publicationYear, type int
	 */

	private int publicationYear;

	@Temporal(TemporalType.DATE)
	private Date meetingStartDate;

	@Temporal(TemporalType.DATE)
	private Date meetingEndDate;

	/** */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString committee;

	/** */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString reportSummary;

	/**
	 * The url where the document is to be found
	 */
	private URL url;

	/**
	 * The title
	 */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString citation;

	/**
	 * This field maybe used to indicate what type of source this is. One type would be link CEM Source.
	 * 
	 * <option value="1">Book </option>
	 * 
	 * <option value="3">Journal </option>
	 * 
	 * <option value="4">Project </option>
	 * 
	 * <option value="2">Meeting documents</option>
	 * 
	 * <option value="6">CD-ROM/DVD</option> <option VALUE="-1">Other </option>
	 * 
	 */
	private Integer sourceType;

	public Long getId() {
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

	public SpecificMeasure getSpecificMeasure() {
		return specificMeasure;
	}

	public void setSpecificMeasure(SpecificMeasure specificMeasure) {
		this.specificMeasure = specificMeasure;
	}

	public GeneralMeasure getGeneralMeasure() {
		return generalMeasure;
	}

	public void setGeneralMeasure(GeneralMeasure generalMeasure) {
		this.generalMeasure = generalMeasure;
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

	public MultiLingualString getCommittee() {
		return committee;
	}

	public void setCommittee(MultiLingualString committee) {
		this.committee = committee;
	}

	public MultiLingualString getReportSummary() {
		return reportSummary;
	}

	public void setReportSummary(MultiLingualString reportSummary) {
		this.reportSummary = reportSummary;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public MultiLingualString getCitation() {
		return citation;
	}

	public void setCitation(MultiLingualString citation) {
		this.citation = citation;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(int sourceType) {
		this.sourceType = sourceType;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

}
