package org.fao.fi.figis.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REF_VME", schema = "figis")
public class RefVme implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1166509835208065643L;

	@Id
	@Column(name = "CD_VME")
	private Long id;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMeta() {
		return meta;
	}

	public void setMeta(Integer meta) {
		this.meta = meta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((meta == null) ? 0 : meta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefVme other = (RefVme) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (meta == null) {
			if (other.meta != null)
				return false;
		} else if (!meta.equals(other.meta))
			return false;
		return true;
	}

}
