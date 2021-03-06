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

	@ManyToOne(cascade = { CascadeType.REFRESH })
	@RSGName("Authority")
	@RSGWeight(0)
	@RSGSimpleReference
	private Rfmo rfmo;

	/**
	 * InformationSource has 0,1 SpecificMeasure
	 */
	// @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	// @JoinTable(name = "IS_SM")
	/*
	 * Intervention Erik van Ingen The jointable IS_SM is not necessary because it is a 1toMany relation from a
	 * information source point of view. Therefore the annotation have been modified. From now on the IS_SM is useless
	 * because the relation will be managed by the specificmeaure.informationsource_id field in the DB.
	 */
	@OneToMany(mappedBy = "informationSource", cascade = { CascadeType.REFRESH })
	private List<SpecificMeasure> specificMeasureList;

	/**
	 * InformationSource has 0,1 GeneralMeasure
	 */

	@ManyToMany(mappedBy = "informationSourceList", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<GeneralMeasure> generalMeasureList;

	/**
	 * Also referred to as issue date of biblio entry
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
	@OneToOne(cascade = { CascadeType.ALL }, orphanRemoval=true)
	private MultiLingualString committee;

	/** */
	@RSGName("Report Summary")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(2)
	@OneToOne(cascade = { CascadeType.ALL }, orphanRemoval=true)
	private MultiLingualString reportSummary;

	/**
	 * The url where the document is to be found
	 */
	@RSGName("URL")
	@RSGConverter(URLDataConverter.class)
	@RSGWeight(2)
	private URL url;

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
	@RSGName(value = "Source Type", hideHeading = true)
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
	@OneToOne(cascade = { CascadeType.ALL }, orphanRemoval=true)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((citation == null) ? 0 : citation.hashCode());
		result = prime * result + ((committee == null) ? 0 : committee.hashCode());
		result = prime * result + ((generalMeasureList == null) ? 0 : generalMeasureList.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((meetingEndDate == null) ? 0 : meetingEndDate.hashCode());
		result = prime * result + ((meetingStartDate == null) ? 0 : meetingStartDate.hashCode());
		result = prime * result + ((publicationYear == null) ? 0 : publicationYear.hashCode());
		result = prime * result + ((reportSummary == null) ? 0 : reportSummary.hashCode());
		result = prime * result + ((rfmo == null) ? 0 : rfmo.hashCode());
		result = prime * result + ((sourceType == null) ? 0 : sourceType.hashCode());
		// result = prime * result + ((specificMeasureList == null) ? 0 : specificMeasureList.hashCode());
		return result;
	}

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