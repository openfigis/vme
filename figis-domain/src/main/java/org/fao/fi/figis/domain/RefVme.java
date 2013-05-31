package org.fao.fi.figis.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "REF_VME")
public class RefVme {

	@Id
	@Column(name = "CD_VME")
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
