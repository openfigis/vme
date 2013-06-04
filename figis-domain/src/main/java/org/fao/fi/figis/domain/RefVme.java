package org.fao.fi.figis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REF_VME", schema = "figis")
public class RefVme {

	@Id
	@Column(name = "CD_VME")
	private Integer id;

	@Column(name = "CD_meta")
	private Integer meta;

	// @OneToMany(cascade = { CascadeType.ALL })
	// @OneToMany(cascade = { CascadeType.ALL })
	// @JoinTable(name = "FS_VME_OBSERVATION", joinColumns = @JoinColumn(name = "CD_VME", referencedColumnName =
	// "CD_VME"), inverseJoinColumns = @JoinColumn(name = "CD_OBSERVATION", referencedColumnName = "CD_OBSERVATION"))
	// private List<Observation> observationList;

	// @OneToMany
	// @JoinTable(name = "FS_VME_OBSERVATION", joinColumns = @JoinColumn(name = "CD_VME", referencedColumnName =
	// "CD_VME"), inverseJoinColumns = @JoinColumn(name = "CD_OBSERVATION", referencedColumnName = "CD_OBSERVATION"))
	// private List<VmeObservation> vmeObservationList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMeta() {
		return meta;
	}

	public void setMeta(Integer meta) {
		this.meta = meta;
	}

	// public List<VmeObservation> getVmeObservationList() {
	// return vmeObservationList;
	// }
	//
	// public void setVmeObservationList(List<VmeObservation> vmeObservationList) {
	// this.vmeObservationList = vmeObservationList;
	// }

}
