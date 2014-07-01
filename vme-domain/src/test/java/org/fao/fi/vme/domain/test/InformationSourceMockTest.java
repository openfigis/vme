package org.fao.fi.vme.domain.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.reference.InformationSourceType;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Test;

public class InformationSourceMockTest {

	private static final MultiLingualStringUtil UTIL = new MultiLingualStringUtil();
	private InformationSource inf = InformationSourceMock.create();
	private InformationSourceType infType = InformationSourceMock.createInformationSourceType();

	@Test
	public void testCreate() {

		assertEquals("RFMO Conservation and Enforcement Measure  (Doc No. ####)", UTIL.getEnglish(inf.getCitation()));
		assertEquals("Regional Fishery Management Organization (RFMO)", UTIL.getEnglish(inf.getCommittee()));
		assertEquals("This is an abstract (report summary)", UTIL.getEnglish(inf.getReportSummary()));
		// assertTrue(1001 == inf.getId());
		assertTrue(2000 == inf.getPublicationYear());
		assertEquals("Meeting documents", inf.getSourceType().getName());
		assertEquals("http://www.rfmo.org", inf.getUrl().toExternalForm());

	}

	@Test
	public void testCreateInformationSourceType() {
		assertTrue(2 == infType.getId());
		assertEquals("2:Meeting documents:Y", infType.getSerializedForm());
		assertTrue(2 == infType.getId());
		assertEquals("Y", String.valueOf(infType.getMeetingDocument()));
	}

}
