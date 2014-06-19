package org.fao.fi.vme.domain.model;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.fao.fi.vme.domain.model.reference.InformationSourceType;
import org.fao.fi.vme.domain.model.support.CustomInformationSourceTypeDataConverter;
import org.fao.fi.vme.domain.support.MultiLingualStringConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReferenceReport;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGInstructions;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGMandatory;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGName;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGOneAmong;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGSimpleReference;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGWeight;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.constants.ConceptData;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.DateDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.IntegerDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.LongDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.URLDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.ReferenceReport;

/**
 * 
 * The source of information available on the level of an RFMO.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@RSGReferenceReport(name = "VME Information Source")
@Entity(name = "INFORMATION_SOURCE")
public class InformationSource implements ObjectId<Long>, ReferenceReport, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1951414470540758166L;

	@RSGIdentifier
	@RSGConverter(LongDataConverter.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@RSGName("Authority")
	@RSGWeight(0)
	@RSGSimpleReference
	private Rfmo rfmo;

	/**
	 * InformationSource has 0,1 SpecificMeasure
	 */
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "IS_SM")
	private List<SpecificMeasure> specificMeasureList;

	/**
	 * InformationSource has 0,1 GeneralMeasure
	 */

	@ManyToMany(mappedBy = "informationSourceList", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<GeneralMeasure> generalMeasureList;

	/**
	 * Also referred to as issue date of biblio entry
	 * 
	 * TODO change into publicationYear, type int
	 */

	@RSGName("Publication Year")
	@RSGConverter(IntegerDataConverter.class)
	@RSGWeight(2)
	private Integer publicationYear;

	@RSGName("Meeting Start Date")
	@RSGInstructions("Use the YYYY/MM/DD format")
	@RSGConverter(DateDataConverter.class)
	@RSGWeight(2)
	@Temporal(TemporalType.DATE)
	private Date meetingStartDate;

	@RSGName("Meeting End Date")
	@RSGInstructions("Use the YYYY/MM/DD format")
	@RSGConverter(DateDataConverter.class)
	@RSGWeight(2)
	@Temporal(TemporalType.DATE)
	private Date meetingEndDate;

	/** */
	@RSGName("Committee")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(2)
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString committee;

	/** */
	@RSGName("Report Summary")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(2)
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString reportSummary;

	/**
	 * The url where the document is to be found
	 */
	@RSGName("URL")
	@RSGConverter(URLDataConverter.class)
	@RSGWeight(2)
	private URL url;

	/**
	 * This field maybe used to indicate what type of source this is. One type
	 * would be link CEM Source.
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
	@RSGName("Source Type")
	@RSGMandatory
	@RSGOneAmong(concept = InformationSourceType.class, label = ConceptData.NAME, value = ConceptData.ID)
	@RSGConverter(CustomInformationSourceTypeDataConverter.class)
	@RSGWeight(1)
	@ManyToOne(fetch = FetchType.EAGER)
	private InformationSourceType sourceType;

	/**
	 * The title
	 */
	@RSGName("Citation")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(1)
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString citation;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}

	public Rfmo getRfmo() {
		return rfmo;
	}

	public void setRfmo(Rfmo rfmo) {
		this.rfmo = rfmo;
	}

	public List<SpecificMeasure> getSpecificMeasureList() {
		return specificMeasureList;
	}

	public void setSpecificMeasureList(List<SpecificMeasure> specificMeasureList) {
		this.specificMeasureList = specificMeasureList;
	}

	public List<GeneralMeasure> getGeneralMeasureList() {
		return generalMeasureList;
	}

	public void setGeneralMeasureList(List<GeneralMeasure> generalMeasureList) {
		this.generalMeasureList = generalMeasureList;
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

	/**
	 * @return the 'sourceType' value
	 */
	public InformationSourceType getSourceType() {
		return this.sourceType;
	}

	/**
	 * @param sourceType
	 *            the 'sourceType' value to set
	 */
	public void setSourceType(InformationSourceType sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		InformationSource other = (InformationSource) obj;

		if (this.citation == null) {
			if (other.citation != null) {
				return false;
			}
		} else if (!this.citation.equals(other.citation)) {
			return false;
		}

		if (this.sourceType == null) {
			if (other.sourceType != null) {
				return false;
			}
		} else if (!this.sourceType.equals(other.sourceType)) {
			return false;
		}
		if (this.committee == null) {
			if (other.committee != null) {
				return false;
			}
		} else if (!this.committee.equals(other.committee)) {
			return false;
		}
		if (this.generalMeasureList == null) {
			if (other.generalMeasureList != null) {
				return false;
			}
		} else if (!this.generalMeasureList.equals(other.generalMeasureList)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.meetingEndDate == null) {
			if (other.meetingEndDate != null) {
				return false;
			}
		} else if (!this.meetingEndDate.equals(other.meetingEndDate)) {
			return false;
		}
		if (this.meetingStartDate == null) {
			if (other.meetingStartDate != null) {
				return false;
			}
		} else if (!this.meetingStartDate.equals(other.meetingStartDate)) {
			return false;
		}
		if (this.publicationYear == null) {
			if (other.publicationYear != null) {
				return false;
			}
		} else if (!this.publicationYear.equals(other.publicationYear)) {
			return false;
		}
		if (this.reportSummary == null) {
			if (other.reportSummary != null) {
				return false;
			}
		} else if (!this.reportSummary.equals(other.reportSummary)) {
			return false;
		}
		if (this.rfmo == null) {
			if (other.rfmo != null) {
				return false;
			}
		} else if (!this.rfmo.equals(other.rfmo)) {
			return false;
		}
		if (this.sourceType == null) {
			if (other.sourceType != null) {
				return false;
			}
		} else if (!this.sourceType.equals(other.sourceType)) {
			return false;
		}
		if (this.specificMeasureList == null) {
			if (other.specificMeasureList != null) {
				return false;
			}
		} else if (!this.specificMeasureList.equals(other.specificMeasureList)) {
			return false;
		}
		if (this.url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!this.url.toString().equals(other.url.toString())) {
			return false;
		}
		return true;
	}
}