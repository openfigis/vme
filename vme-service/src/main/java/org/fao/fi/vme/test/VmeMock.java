package org.fao.fi.vme.test;

import org.fao.fi.vme.domain.Vme;

public class VmeMock {

	public static Vme create() {

		Vme vme = new Vme();
		vme.setValidityPeriod(ValidityPeriodMock.create());
		vme.setId(new Long(1000));
		return vme;

	}

}
