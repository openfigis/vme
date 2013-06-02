package org.fao.fi.figis.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "REF_VME", schema = "figis")
public class RefVme {

	@Id
	@Column(name = "CD_VME")
	private int id;

	@OneToMany
	@JoinTable(name = "FS_VME_OBSERVATION", joinColumns = @JoinColumn(name = "CD_VME", referencedColumnName = "CD_VME"), inverseJoinColumns = @JoinColumn(name = "CD_OBSERVATION", referencedColumnName = "CD_OBSERVATION"))
	private List<Observation> observationList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
