package org.fao.fi.figis.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity(name = "REF_VME")
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
