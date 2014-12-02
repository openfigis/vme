package org.vme.dao.sources.vme;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class MultiLingualStringPersistenceTest {

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Inject
	VmeDao dao;

	/**
	 * testing the fact that the orphan multi lingual string gets removed when the parent gets removed.
	 */
	@Test
	public void test() {
		SpecificMeasure s = new SpecificMeasure();
		MultiLingualString m = u.english("piet");
		s.setVmeSpecificMeasure(m);
		dao.persist(s);
		assertEquals(1, dao.count(SpecificMeasure.class).intValue());
		assertEquals(1, dao.count(MultiLingualString.class).intValue());
		dao.remove(s);
		assertEquals(0, dao.count(SpecificMeasure.class).intValue());
		assertEquals(0, dao.count(MultiLingualString.class).intValue());

	}
}
