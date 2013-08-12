package org.fao.fi.vme.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.interfacee.Valid;
import org.fao.fi.vme.domain.interfacee.YearObject;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity(name = "GENERAL_MEASURES")
public class GeneralMeasures implements YearObject<GeneralMeasures>, Valid {

	/** 
	 * 
	 */
	@Id
	private Long id;

	/**
	 * GeneralMeasures are defined on the level of a RFMO.
	 * 
	 * This was here @OneToOne but I believe that this should be @ManyToOne
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private Rfmo rfmo;

	/**
	 * YearObject in which the measures are defined, established.
	 */
	private Integer year;

	/**
	 * 
	 */
	private ValidityPeriod validityPeriod;

	/**
	 * GeneralMeasure has one linkCemSource. It should be the column Link_CEM_Source within the Measures_VME_general
	 * table.
	 * 
	 */
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "INFORMATION_SOURCE_LIST")
	private List<InformationSource> informationSourceList;

	/**
	 * 
	 */

	@Lob
	private String fishingAreas;

	/**
	 * 
	 */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString explorataryFishingProtocols;

	/**
	 * 
	 */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString vmeEncounterProtocols;

	/**
	 * 
	 */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString vmeIndicatorSpecies;

	/**
	 * 
	 */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString vmeThreshold;

	public Long getId() {
		return id;
	}

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

	public String getFishingAreas() {
		return fishingAreas;
	}

	public void setFishingAreas(String fishingAreas) {
		this.fishingAreas = fishingAreas;
	}

	public MultiLingualString getExplorataryFishingProtocols() {
		return explorataryFishingProtocols;
	}

	public void setExplorataryFishingProtocols(MultiLingualString explorataryFishingProtocols) {
		this.explorataryFishingProtocols = explorataryFishingProtocols;
	}

	public MultiLingualString getVmeEncounterProtocols() {
		return vmeEncounterProtocols;
	}

	public void setVmeEncounterProtocols(MultiLingualString vmeEncounterProtocols) {
		this.vmeEncounterProtocols = vmeEncounterProtocols;
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

}
