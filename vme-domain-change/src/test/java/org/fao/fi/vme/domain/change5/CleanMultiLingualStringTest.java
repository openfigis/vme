package org.fao.fi.vme.domain.change5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.HashMap;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class CleanMultiLingualStringTest {

	@Inject
	CleanMultiLingualString c;

	@Inject
	VmeDao dao;

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void analyses() {

		MultiLingualString mls = dao.getEm().find(MultiLingualString.class, 31363l);
		mls.setStringMap(null);
		EntityTransaction t = dao.getEm().getTransaction();
		t.begin();
		dao.getEm().merge(mls);
		t.commit();
	}

	@Test
	public void testClean() {
		try {
			c.cleanEmpty();
			c.report();
		} catch (Exception e) {
			e.printStackTrace();
			c.report();
			fail();
		}
	}

	@Test
	@Ignore
	public void testCleanVme() {
		Vme vme = new Vme();
		MultiLingualString mls = new MultiLingualString();
		vme.setName(mls);
		dao.persist(vme);
		assertEquals(1, dao.loadObjects(MultiLingualString.class).size());
		vme.setName(mls);

		SpecificMeasure s = new SpecificMeasure();
		MultiLingualString mls2 = new MultiLingualString();
		mls2.setStringMap(new HashMap<Integer, String>());
		s.setVmeSpecificMeasure(mls2);
		dao.persist(s);
		assertEquals(1, dao.loadObjects(SpecificMeasure.class).size());

		InformationSource i = new InformationSource();
		String text = "go";
		i.setCitation(u.english(text));
		dao.persist(i);

		c.cleanEmpty();
		assertEquals(1, dao.loadObjects(MultiLingualString.class).size());
		assertNull(vme.getName());
		assertNull(s.getVmeSpecificMeasure());
		assertEquals(text, u.getEnglish(dao.loadObjects(MultiLingualString.class).get(0)));

	}

}
