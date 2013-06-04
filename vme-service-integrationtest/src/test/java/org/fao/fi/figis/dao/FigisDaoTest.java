package org.fao.fi.figis.dao;

import javax.inject.Inject;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.fao.fi.vme.figis.component.VmeRefSync;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class })
public class FigisDaoTest {

	@Inject
	FigisDao figisDao;

	@Test
	public void testPersist() {
		Observation observation = new Observation();
		observation.setOrder(VmeRefSync.ORDER);
		observation.setCollection(VmeRefSync.COLLECTION);
		observation.setPrimary(true);
		observation.setReference(true);

		figisDao.persist(observation);

	}
}
