package org.vme.service.dao.impl.jpa;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.JpaDaoFactory;
import org.vme.service.dao.config.figis.FigisDataBaseProducer;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.service.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class, VmeDataBaseProducer.class, JpaDaoFactory.class })
public class ObservationJpaDaoTest {

	@Inject
	ObservationJpaDao dao;

	@Inject
	private VmeDao vmeDao;

	/**
	 * This test is successful if without a publication year the logic works
	 * without NPEs
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSearchObservations() throws Exception {
		Vme vme = VmeMock.generateVme(1);
		vme.getSpecificMeasureList().get(0).getInformationSource().setPublicationYear(null);
		vmeDao.saveVme(vme);
		String text = "lola";
		dao.searchObservations(0, 0, 0, VmeMock.YEAR, text);
	}

	@Test
	public void testGetObservationById() {

	}

	@Test
	public void testGetObservationByInevntoryIdentifier() {

	}

	@Test
	public void testGetObservationByGeographicFeatureId() {

	}

}
