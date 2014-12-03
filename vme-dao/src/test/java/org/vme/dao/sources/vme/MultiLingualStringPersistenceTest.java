package org.vme.dao.sources.vme;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.util.Lang;
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
	 * ORA-00001: unique constraintviolated
	 * 
	 * Caused by: java.sql.SQLIntegrityConstraintViolationException: ORA-00001: unique constraint (VME.SYS_C006744)
	 * violated
	 * 
	 * Experimented with Insert into multilingualstring_stringmap (MULTILINGUALSTRING_ID,STRINGMAP_KEY) values
	 * (31463,1); In development db: SQL Error: ORA-00001: unique constraint (VME.SYS_C00135268) violated
	 * 
	 * TODO reproduce the above error 
	 * 
	 */
	@Test
	public void testORA00001uniqueConstraintViolated() {

		MultiLingualString m1 = u.english("piet");
		dao.persist(m1);
		System.out.println(m1.getId());

		MultiLingualString m2 = new MultiLingualString();

		m2.setId(m1.getId());
		m2.setStringMap(new HashMap<Integer, String>());
		m2.getStringMap().put(Lang.EN, m1.getStringMap().get(Lang.EN));
		// merge works, persist does however not cause the unique constraint violated error
		dao.merge(m2);

	}

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
