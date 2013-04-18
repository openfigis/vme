package org.fao.fi.vme.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Rfb {

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
