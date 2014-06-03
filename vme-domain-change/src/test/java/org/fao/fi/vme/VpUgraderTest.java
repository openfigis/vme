package org.fao.fi.vme;

import static org.junit.Assert.assertNotNull;

import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.junit.Test;

public class VpUgraderTest {

	VpUgrader u = new VpUgrader();

	@Test
	public void testUpgrade() {
		ValidityPeriod vp = ValidityPeriodMock.create();
		u.upgrade(vp);
		assertNotNull(vp.getBeginDate());
		assertNotNull(vp.getEndDate());
	}

	@Test
	public void testUpgradeEmpty() {
		ValidityPeriod vp = null;
		u.upgrade(vp);
	}
}
