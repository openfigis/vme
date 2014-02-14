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
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.VmeSearchDao;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ReferenceDaoImpl.class, VmeSearchDaoImpl.class })
@ActivatedAlternatives({ FigisDataBaseProducer.class, VmeDataBaseProducer.class })
public class VmeSearchDaoImplTest {

	@Inject
	VmeSearchDao dao;

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
	public void testSearchVme() throws Exception {
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

	/**
	 * Solving Authority problem
	 * 
	 * 
	 * 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSearchVmeAuthority() throws Exception {
		long authority_id = 20010l;

		Authority a = new Authority();
		a.setId((int) authority_id);
		vmeDao.persist(a);

		// authority_id 20010 type_id 10 criteria_id 0 year 2012 text null
		long type_id = 10l;
		long criteria_id = 0l;
		int year = 2012;
		String text = null;
		List<VmeDto> list = dao.searchVme(authority_id, type_id, criteria_id, year, text);
		assertEquals(0, list.size());

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
