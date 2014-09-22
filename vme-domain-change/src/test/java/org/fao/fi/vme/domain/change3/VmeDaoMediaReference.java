package org.fao.fi.vme.domain.change3;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.MediaReference;
import org.fao.fi.vme.domain.test.MediaReferenceMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

/**
 * Dao in order to run Hibernate with temporary update mode in order to apply
 * the DB changes for MediaReference.
 * 
 * 
 * @author Erik van Ingen
 *
 */

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class VmeDaoMediaReference {

	@Inject
	VmeDao dao;

	@Test
	public void testMediaReference() throws Throwable {
		MediaReference r = MediaReferenceMock.create();
		dao.persist(r);
		assertEquals(1, dao.count(MediaReference.class).intValue());
		dao.remove(r);
		assertEquals(0, dao.count(MediaReference.class).intValue());
	}
}
