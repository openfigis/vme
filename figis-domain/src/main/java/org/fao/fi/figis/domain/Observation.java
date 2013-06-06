package org.fao.fi.figis.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
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
public class Observation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 448844837583234537L;

	@Id
	@Column(name = "CD_OBSERVATION", unique = true, nullable = false, precision = 10)
	@SequenceGenerator(name = "SEQ_FS_OBSERVATION", sequenceName = "SEQ_FS_OBSERVATION")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FS_OBSERVATION")
	private long id;

	@Column(name = "FG_PRIMARY", nullable = true)
	private boolean primary;

	@Column(name = "FG_REFERENCE", nullable = true)
	private boolean reference;

	@Column(name = "CD_COLLECTION", nullable = true)
	private int collection;

	@Column(name = "OBS_ORDER", nullable = false)
	private Short order;

	@OneToMany(targetEntity = ObservationXml.class, cascade = CascadeType.ALL, mappedBy = "observation")
	private List<ObservationXml> observationsPerLanguage;

	public Short getOrder() {
		return order;
	}

	public void setOrder(Short order) {
		this.order = order;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public int getCollection() {
		return collection;
	}

	public void setCollection(int collection) {
		this.collection = collection;
	}

	public List<ObservationXml> getObservationsPerLanguage() {
		return observationsPerLanguage;
	}

	public void setObservationsPerLanguage(List<ObservationXml> observationsPerLanguage) {
		this.observationsPerLanguage = observationsPerLanguage;
	}

}
