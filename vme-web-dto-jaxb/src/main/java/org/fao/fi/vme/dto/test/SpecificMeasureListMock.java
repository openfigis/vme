package org.fao.fi.vme.dto.test;

import org.fao.fi.vme.webservice.SpecificMeasureList;

public class SpecificMeasureListMock {
	
	private SpecificMeasureListMock() {
		
	}

	public static SpecificMeasureList create() {
		SpecificMeasureList l = new SpecificMeasureList();
		l.getSpecificMeasures().add(SpecificMeasureTypeMock.create());
		l.getSpecificMeasures().add(SpecificMeasureTypeMock.create());
		return l;

	}
}
