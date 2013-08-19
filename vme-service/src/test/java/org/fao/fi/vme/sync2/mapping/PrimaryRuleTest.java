package org.fao.fi.vme.sync2.mapping;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.junit.Test;

public class PrimaryRuleTest {

	PrimaryRule primaryRule = new PrimaryRule();

	@Test
	public void testApply() {

		ObservationDomain od1 = new DefaultObservationDomain().defineDefaultObservation();
		od1.setReportingYear("2010");
		ObservationDomain od2 = new DefaultObservationDomain().defineDefaultObservation();
		od1.setReportingYear("2011");

		List<ObservationDomain> observationList = new ArrayList<ObservationDomain>();
		observationList.add(od1);
		observationList.add(od2);

		VmeObservationDomain vod = new VmeObservationDomain();
		vod.setObservationDomainList(observationList);
		primaryRule.apply(vod);
		assertFalse(od1.isPrimary());
		assertTrue(od2.isPrimary());

	}

}
