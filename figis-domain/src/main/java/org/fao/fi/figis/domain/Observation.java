package org.fao.fi.figis.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FS_OBSERVATION", schema = "figis")
public class Observation {

	@Id
	@Column(name = "CD_OBSERVATION", nullable = false)
	@SequenceGenerator(name = "SEQ_FS_OBSERVATION", sequenceName = "SEQ_FS_OBSERVATION")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FS_OBSERVATION")
	private Integer id;

	@Column(name = "FG_PRIMARY", nullable = true)
	private boolean primary;

	@Column(name = "FG_REFERENCE", nullable = true)
	private boolean reference;

	@Column(name = "CD_COLLECTION", nullable = true)
	private Integer collection;

	@Column(name = "OBS_ORDER", nullable = false)
	private Short order;

	@OneToMany(mappedBy = "observation")
	private List<ObservationXml> observationsPerLanguage;

	public Short getOrder() {
		return order;
	}

	public void setOrder(Short order) {
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public boolean isReference() {
		return reference;
	}

	public void setReference(boolean reference) {
		this.reference = reference;
	}

	public Integer getCollection() {
		return collection;
	}

	public void setCollection(Integer collection) {
		this.collection = collection;
	}

	public List<ObservationXml> getObservationsPerLanguage() {
		return observationsPerLanguage;
	}

	public void setObservationsPerLanguage(List<ObservationXml> observationsPerLanguage) {
		this.observationsPerLanguage = observationsPerLanguage;
	}

}
