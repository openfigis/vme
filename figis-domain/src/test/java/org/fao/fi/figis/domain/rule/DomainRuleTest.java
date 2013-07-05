package org.fao.fi.figis.domain.rule;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DomainRuleTest extends DomainRule4ObservationXmlId {

	@Test
	public void testComposeId() {
		DomainRule4ObservationXmlId r = new DomainRule4ObservationXmlId();
		Observation o = new Observation();
		// int id = 78789;
		Long id = new Long(78789);
		o.setId(id);
		ObservationXml x = new ObservationXml();
		x.setObservation(o);
		x.setLanguage(4);
		r.composeId(x);
		// 1019:en
		String composed = id + ":zh";
		System.out.println(composed);
		assertEquals(composed, x.getId());

	}
}
