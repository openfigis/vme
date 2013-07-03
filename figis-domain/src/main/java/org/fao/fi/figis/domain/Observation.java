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

	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ObservationIDGenerator")
	// @SequenceGenerator(name = "ObservationIDGenerator", sequenceName = "SEQ_FS_OBSERVATION")

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "G1")
	@Column(name = "CD_OBSERVATION", unique = true, nullable = false, precision = 10)
	@SequenceGenerator(name = "G1", sequenceName = "SEQ_FS_OBSERVATION", allocationSize = 1)
	private int id;

	@Column(name = "FG_PRIMARY", nullable = true)
	private boolean primary;

	@Column(name = "FG_REFERENCE", nullable = true)
	private boolean reference;

	@Column(name = "CD_COLLECTION", nullable = true)
	private int collection;

	@Column(name = "OBS_ORDER", nullable = false)
	private short order;

	@OneToMany(targetEntity = ObservationXml.class, cascade = CascadeType.MERGE, mappedBy = "observation")
	private List<ObservationXml> observationsPerLanguage;

	public short getOrder() {
		return order;
	}

	public void setOrder(short order) {
		this.order = order;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
