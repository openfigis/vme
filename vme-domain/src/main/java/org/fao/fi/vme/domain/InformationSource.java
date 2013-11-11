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

import org.gcube.application.rsg.support.annotations.ReferenceReport;
import org.gcube.application.rsg.support.annotations.fields.Identifier;

/**
 * 
 * The source of information available on the level of an RFMO.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@ReferenceReport(name="VME Information Source")
@Entity(name = "INFORMATION_SOURCE")
public class InformationSource {

	@Identifier
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

	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + ((citation == null) ? 0 : citation.hashCode());
	// result = prime * result + ((committee == null) ? 0 : committee.hashCode());
	// result = prime * result + ((generalMeasure == null) ? 0 : generalMeasure.hashCode());
	// result = prime * result + ((id == null) ? 0 : id.hashCode());
	// result = prime * result + ((meetingEndDate == null) ? 0 : meetingEndDate.hashCode());
	// result = prime * result + ((meetingStartDate == null) ? 0 : meetingStartDate.hashCode());
	// result = prime * result + publicationYear;
	// result = prime * result + ((reportSummary == null) ? 0 : reportSummary.hashCode());
	// result = prime * result + ((sourceType == null) ? 0 : sourceType.hashCode());
	// result = prime * result + ((specificMeasure == null) ? 0 : specificMeasure.hashCode());
	// result = prime * result + ((url == null) ? 0 : url.hashCode());
	// return result;
	// }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof InformationSource)) {
			return false;
		}
		InformationSource other = (InformationSource) obj;
		if (citation == null) {
			if (other.citation != null) {
				return false;
			}
		} else if (!citation.equals(other.citation)) {
			return false;
		}
		if (committee == null) {
			if (other.committee != null) {
				return false;
			}
		} else if (!committee.equals(other.committee)) {
			return false;
		}
		if (generalMeasure == null) {
			if (other.generalMeasure != null) {
				return false;
			}
		} else if (!generalMeasure.equals(other.generalMeasure)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (meetingEndDate == null) {
			if (other.meetingEndDate != null) {
				return false;
			}
		} else if (!meetingEndDate.equals(other.meetingEndDate)) {
			return false;
		}
		if (meetingStartDate == null) {
			if (other.meetingStartDate != null) {
				return false;
			}
		} else if (!meetingStartDate.equals(other.meetingStartDate)) {
			return false;
		}
		if (publicationYear != other.publicationYear) {
			return false;
		}
		if (reportSummary == null) {
			if (other.reportSummary != null) {
				return false;
			}
		} else if (!reportSummary.equals(other.reportSummary)) {
			return false;
		}
		if (rfmoList == null) {
			if (other.rfmoList != null) {
				return false;
			}
		} else if (!rfmoList.equals(other.rfmoList)) {
			return false;
		}
		if (sourceType == null) {
			if (other.sourceType != null) {
				return false;
			}
		} else if (!sourceType.equals(other.sourceType)) {
			return false;
		}
		if (specificMeasure == null) {
			if (other.specificMeasure != null) {
				return false;
			}
		} else if (!specificMeasure.equals(other.specificMeasure)) {
			return false;
		}
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		return true;
	}

}
