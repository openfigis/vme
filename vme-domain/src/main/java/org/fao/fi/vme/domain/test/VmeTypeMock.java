package org.fao.fi.vme.domain.test;

import org.fao.fi.vme.domain.model.reference.VmeType;

public class VmeTypeMock {
	
	private VmeTypeMock(){
		
	}

	public static VmeType create() {

		return new VmeType(150L, "TTH_FISH_RSCH", "Research fisheries");
	}
	
	public static VmeType createAnother() {
		
		return new VmeType(180L, "MPR_FISH_FOO", "Foo fisheries");
	}

}
