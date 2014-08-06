package org.fao.fi.vme.domain.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Test;

public class RfmoMockTest {

	private Rfmo rfmo = RfmoMock.create();
	private MultiLingualStringUtil UTIL = new MultiLingualStringUtil();
	
	@Test
	@SuppressWarnings("deprecation")
	public void testCreate() {
		for (GeneralMeasure genMeas : rfmo.getGeneralMeasureList()) {
			assertTrue(99 == genMeas.getValidityPeriod().getBeginDate().getYear());
		}
		for (FisheryAreasHistory faHistory : rfmo.getHasFisheryAreasHistory()) {
			assertEquals("Fishery Area history value", UTIL.getEnglish(faHistory.getHistory()));
		}
		assertTrue(1000 == Integer.valueOf(rfmo.getId()));
		for (InformationSource inf : rfmo.getInformationSourceList()) {
			assertEquals("This is an abstract (report summary)", UTIL.getEnglish(inf.getReportSummary()));
		}
		for (Vme vme : rfmo.getListOfManagedVmes()) {
			assertEquals("Hard Corner Bugs ", UTIL.getEnglish(vme.getName()));
		}
	}

}
