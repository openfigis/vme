package org.fao.fi.vme.domain.test;

import org.fao.fi.vme.domain.model.VmeType;

public class VmeTypeMock {

	public static VmeType create() {

		return new VmeType(150l, "TTH_FISH_RSCH", "Research fisheries");
	}

}
