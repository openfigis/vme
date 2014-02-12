package org.vme.dao.impl.jpa;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.impl.jpa.VmeSearchDaoImpl;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class, VmeDataBaseProducer.class })
public class ObservationJpaDaoTest {

	@Inject
	VmeSearchDaoImpl dao;

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

		Authority a = new Authority();
		a.setAcronym(vme.getRfmo().getId());
		vmeDao.persist(a);

		vme.getSpecificMeasureList().get(0).setVmeSpecificMeasure((u.english(text)));
		vmeDao.saveVme(vme);
		List<VmeDto> list = dao.searchVme(0, 0, 0, VmeMock.YEAR, text);

		assertEquals(1, list.size());
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
