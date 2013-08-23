package org.fao.fi.vme.sync2.mapping;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.junit.Test;

public class PrimaryRuleValidatorTest {

	PrimaryRuleValidator v = new PrimaryRuleValidator();

	@Test
	public void testValidate() {
		VmeObservationDomain vod = new VmeObservationDomain();

		ObservationDomain o1 = new ObservationDomain();
		ObservationDomain o2 = new ObservationDomain();
		ObservationDomain o3 = new ObservationDomain();

		List<ObservationDomain> l = new ArrayList<ObservationDomain>();
		l.add(o1);
		l.add(o2);

		vod.setObservationDomainList(l);

		try {
			v.validate(vod);
			fail();
		} catch (Exception e) {
		}

		o3.setPrimary(true);
		l.add(o3);
		v.validate(vod);

	}

}
