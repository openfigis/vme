package org.vme.service.dao.impl.jpa;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.dto.observations.ObservationDto;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
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

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	/**
	 * This test is successful if without a publication year the logic works
	 * without NPEs
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSearchObservations() throws Exception {
		String text = "lola";
		Vme vme = VmeMock.generateVme(1);
		vme.getSpecificMeasureList().get(0).setVmeSpecificMeasure((u.english(text)));
		vmeDao.saveVme(vme);
		List<ObservationDto> list = dao.searchObservations(0, 0, 0, VmeMock.YEAR, text);

		// TODO
		// assertEquals(1, list.size());
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
