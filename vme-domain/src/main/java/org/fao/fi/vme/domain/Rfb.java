package org.fao.fi.vme.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Rfb {

	private int rfbId;

	public int getRfbId() {
		return rfbId;
	}

	public void setRfbId(int rfbId) {
		this.rfbId = rfbId;
	}

}
