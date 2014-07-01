package org.fao.fi.vme.domain.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.interfaces.Year;
import org.fao.fi.vme.domain.support.MultiLingualStringConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReferenceReport;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGName;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGRichInput;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGSimpleReference;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGWeight;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.IntegerDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.LongDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.ReferenceReport;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@RSGReferenceReport(name = "General Measure")
@Entity(name = "GENERAL_MEASURE")
public class GeneralMeasure implements ObjectId<Long>, Year<GeneralMeasure>, ReferenceReport, Serializable, Period {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6703934250783141637L;

	/** 
	 * 
	 */
	@RSGIdentifier
	@RSGConverter(LongDataConverter.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * GeneralMeasure are defined on the level of a RFMO.
	 * 
	 * This was here @OneToOne but I believe that this should be @ManyToOne
	 * 
	 */
	@RSGName("Authority")
	@RSGSimpleReference
	@RSGWeight(0)
	@ManyToOne(fetch = FetchType.EAGER)
	private Rfmo rfmo;

	/**
	 * YearObject in which the measure are defined, established.
	 */
	@RSGName("Year")
	@RSGWeight(1)
	@RSGConverter(IntegerDataConverter.class)
	private Integer year;

	/**
	 * 
	 */
	@RSGName("Validity Period")
	@RSGWeight(2)
	private ValidityPeriod validityPeriod;

	/**
	 * GeneralMeasure has one linkCemSource. It should be the column
	 * Link_CEM_Source within the Measures_VME_general table.
	 * 
	 */
	@RSGName("Information Sources")
	@RSGWeight(0)
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "GM_IS", joinColumns = { @JoinColumn(name = "GM_ID") },//
	inverseJoinColumns = { @JoinColumn(name = "IS_ID") })
	private List<InformationSource> informationSourceList;

	/**
	 * 
	 */
	@RSGName("Fishing areas")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(1)
	@OneToOne(cascade = { CascadeType.ALL })
	private @RSGRichInput MultiLingualString fishingArea;

	/**
	 * 
	 */
	@RSGName("Exploratory Fishing Protocols")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(1)
	@OneToOne(cascade = { CascadeType.ALL })
	private @RSGRichInput MultiLingualString explorataryFishingProtocol;

	/**
	 * 
	 */
	@RSGName("Encounter protocols")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(1)
	@OneToOne(cascade = { CascadeType.ALL })
	private @RSGRichInput MultiLingualString vmeEncounterProtocol;

	/**
	 * 
	 */
	@RSGName("Indicator Species")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(1)
	@OneToOne(cascade = { CascadeType.ALL })
	private @RSGRichInput MultiLingualString vmeIndicatorSpecies;

	/**
	 * 
	 */
	@RSGName("Threshold")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(1)
	@OneToOne(cascade = { CascadeType.ALL })
	private @RSGRichInput MultiLingualString vmeThreshold;

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

	public List<InformationSource> getInformationSourceList() {
		return informationSourceList;
	}

	public void setInformationSourceList(List<InformationSource> informationSourceList) {
		this.informationSourceList = informationSourceList;
	}

	@Override
	public Integer getYear() {
		return year;
	}

	@Override
	public void setYear(Integer year) {
		this.year = year;
	}

	public MultiLingualString getFishingArea() {
		return fishingArea;
	}

	public void setFishingArea(MultiLingualString fishingArea) {
		this.fishingArea = fishingArea;
	}

	public MultiLingualString getExplorataryFishingProtocol() {
		return explorataryFishingProtocol;
	}

	public void setExplorataryFishingProtocol(MultiLingualString explorataryFishingProtocol) {
		this.explorataryFishingProtocol = explorataryFishingProtocol;
	}

	public MultiLingualString getVmeEncounterProtocol() {
		return vmeEncounterProtocol;
	}

	public void setVmeEncounterProtocol(MultiLingualString vmeEncounterProtocol) {
		this.vmeEncounterProtocol = vmeEncounterProtocol;
	}

	public MultiLingualString getVmeIndicatorSpecies() {
		return vmeIndicatorSpecies;
	}

	public void setVmeIndicatorSpecies(MultiLingualString vmeIndicatorSpecies) {
		this.vmeIndicatorSpecies = vmeIndicatorSpecies;
	}

	public MultiLingualString getVmeThreshold() {
		return vmeThreshold;
	}

	public void setVmeThreshold(MultiLingualString vmeThreshold) {
		this.vmeThreshold = vmeThreshold;
	}

	public ValidityPeriod getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(ValidityPeriod validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((explorataryFishingProtocol == null) ? 0 : explorataryFishingProtocol.hashCode());
		result = prime * result + ((fishingArea == null) ? 0 : fishingArea.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((informationSourceList == null) ? 0 : informationSourceList.hashCode());
		result = prime * result + ((rfmo == null) ? 0 : rfmo.hashCode());
		result = prime * result + ((validityPeriod == null) ? 0 : validityPeriod.hashCode());
		result = prime * result + ((vmeEncounterProtocol == null) ? 0 : vmeEncounterProtocol.hashCode());
		result = prime * result + ((vmeIndicatorSpecies == null) ? 0 : vmeIndicatorSpecies.hashCode());
		result = prime * result + ((vmeThreshold == null) ? 0 : vmeThreshold.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
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
		GeneralMeasure other = (GeneralMeasure) obj;
		if (explorataryFishingProtocol == null) {
			if (other.explorataryFishingProtocol != null) {
				return false;
			}
		} else if (!explorataryFishingProtocol.equals(other.explorataryFishingProtocol)) {
			return false;
		}
		if (fishingArea == null) {
			if (other.fishingArea != null) {
				return false;
			}
		} else if (!fishingArea.equals(other.fishingArea)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (informationSourceList == null) {
			if (other.informationSourceList != null) {
				return false;
			}
		} else if (!informationSourceList.equals(other.informationSourceList)) {
			return false;
		}
		if (rfmo == null) {
			if (other.rfmo != null) {
				return false;
			}
		} else if (!rfmo.equals(other.rfmo)) {
			return false;
		}
		if (validityPeriod == null) {
			if (other.validityPeriod != null) {
				return false;
			}
		} else if (!validityPeriod.equals(other.validityPeriod)) {
			return false;
		}
		if (vmeEncounterProtocol == null) {
			if (other.vmeEncounterProtocol != null) {
				return false;
			}
		} else if (!vmeEncounterProtocol.equals(other.vmeEncounterProtocol)) {
			return false;
		}
		if (vmeIndicatorSpecies == null) {
			if (other.vmeIndicatorSpecies != null) {
				return false;
			}
		} else if (!vmeIndicatorSpecies.equals(other.vmeIndicatorSpecies)) {
			return false;
		}
		if (vmeThreshold == null) {
			if (other.vmeThreshold != null) {
				return false;
			}
		} else if (!vmeThreshold.equals(other.vmeThreshold)) {
			return false;
		}
		if (year == null) {
			if (other.year != null) {
				return false;
			}
		} else if (!year.equals(other.year)) {
			return false;
		}
		return true;
	}

}
