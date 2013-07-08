package org.fao.fi.vme.sync2.mapping;

import java.util.List;

import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.test.VmeMock;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ObjectMappingTest {

	ObjectMapping om = new ObjectMapping();

	@Test
	public void testMapVme2Figis() {
		Vme vme = VmeMock.create();
		VmeObservationDomain vod = om.mapVme2Figis(vme);

		List<ObservationDomain> odList = vod.getObservationDomainList();
		assertEquals(1, odList.size());
		for (ObservationDomain od : odList) {
			assertNotNull(od.getReportingYear());
			List<ObservationXml> xmlList = od.getObservationsPerLanguage();
			assertEquals(1, xmlList.size());
		}
	}
}
