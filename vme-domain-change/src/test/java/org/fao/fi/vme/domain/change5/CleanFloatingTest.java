package org.fao.fi.vme.domain.change5;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class CleanFloatingTest {

	@Inject
	CleanFloating c;

	@Inject
	VmeDao dao;

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void cleanFloating() {
		c.cleanFloating();
	}

	@Test
	public void testCleanFloating() {
		Vme vme = new Vme();

		MultiLingualString mls = new MultiLingualString();
		vme.setName(mls);
		dao.persist(vme);
		assertEquals(1, dao.loadObjects(MultiLingualString.class).size());

		MultiLingualString mls2 = u.english("car");
		dao.persist(mls2);
		assertEquals(2, dao.loadObjects(MultiLingualString.class).size());
		c.cleanFloating();
		assertEquals(1, dao.loadObjects(MultiLingualString.class).size());

	}
}
