/**
 * (c) 2013 FAO / UN (project: reports-store-gateway-support-compiler)
 */
package org.vme.test.mock;

import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;

/**
 * Place your class / interface description here.
 * 
 * History:
 * 
 * ------------- --------------- ----------------------- Date Author Comment
 * ------------- --------------- ----------------------- 24/nov/2013 Fabio
 * Creation.
 * 
 * @version 1.0
 * @since 24/nov/2013
 */
public class ValidityPeriodMocker extends AbstractMocker {
	static public ValidityPeriod build(Integer start, Integer end) {
		ValidityPeriod mock = new ValidityPeriod();
		mock = ValidityPeriodMock.create(start, end);
		return mock;
	}

	static public ValidityPeriod mockInterval1() {
		return build(2004, 2008);
	}

	static public ValidityPeriod mockInterval2() {
		return build(1975, 1978);
	}

	static public ValidityPeriod mockNotEnded1() {
		return build(null, 2008);
	}

	static public ValidityPeriod mockNotEnded2() {
		return build(null, 2004);
	}

	static public ValidityPeriod mockNotStarted1() {
		return build(2004, null);
	}

	static public ValidityPeriod mockNotStarted2() {
		return build(2008, null);
	}
}
