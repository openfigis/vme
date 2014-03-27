package org.vme.dao.impl.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.VmeSearchDao;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDB;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ReferenceDaoImpl.class, VmeSearchDaoImpl.class })
@ActivatedAlternatives({ FigisTestPersistenceUnitConfiguration.class, 
						 FigisDataBaseProducer.class, 
						 VmeTestPersistenceUnitConfiguration.class, 
						 VmeDataBaseProducerApplicationScope.class })
public class VmeSearchDaoImplTest {

	@VmeDB
	@Inject
	private EntityManager entityManager;

	@Inject
	private VmeSearchDao dao;

	@Inject
	private VmeDao vmeDao;

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void testGetObservationByGeographicFeatureId() {

		int year = 2000;
		String f1 = "1";

		Rfmo r = new Rfmo();
		r.setId("3498wuerfews");

		Vme vme = new Vme();
		GeoRef g1 = new GeoRef();
		g1.setGeographicFeatureID(f1);
		g1.setYear(year);
		g1.setVme(vme);

		GeoRef g2 = new GeoRef();
		g2.setGeographicFeatureID(f1);
		g2.setYear(+year);
		g2.setVme(vme);

		List<GeoRef> l1 = new ArrayList<GeoRef>();
		l1.add(g1);
		l1.add(g2);
		g1.setVme(vme);
		vme.setRfmo(r);
		vme.setGeoRefList(l1);
		vme.setValidityPeriod(ValidityPeriodMock.create());

		List<Vme> listOfManagedVmes = new ArrayList<Vme>();
		listOfManagedVmes.add(vme);

		r.setListOfManagedVmes(listOfManagedVmes);

		vmeDao.persist(r);
		vmeDao.saveVme(vme);

		Authority a = new Authority();
		a.setId(100L);
		a.setAcronym(r.getId());
		vmeDao.persist(a);

		assertEquals(1, vmeDao.count(Vme.class).intValue());
		assertEquals(2, vmeDao.count(GeoRef.class).intValue());
		assertEquals(1, dao.getVmeByGeographicFeatureId(f1, year).size());
	}

	/**
	 * @TODO Failing because of using 2 different EntityManagers
	 * 
	 * 
	 *       This test is successful if without a publication year the logic
	 *       works without NPEs
	 * 
	 * @throws Exception
	 */
	// @Test
	public void testSearchVme() throws Exception {
		String text = "lola";
		Vme vme = VmeMock.generateVme(1);

		Authority a = new Authority();
		a.setAcronym(vme.getRfmo().getId());
		assertTrue(vme.getProfileList().size() > 0);
		vmeDao.persist(a);

		vme.getProfileList().get(0).setDescriptionBiological(null);
		vmeDao.saveVme(vme);

		System.out.println(vme.getId());

		assertTrue(vmeDao.findVme(vme.getId()).getProfileList().size() > 0);

		List<VmeDto> list = dao.searchVme(0, 0, 0, VmeMock.YEAR, text);
		vme.getSpecificMeasureList().get(0).setVmeSpecificMeasure((u.english(text)));

		list = dao.searchVme(0, 0, 0, VmeMock.YEAR, text);
		assertEquals(1, list.size());
	}

	/**
	 * @TODO Failing because of using 2 different EntityManagers
	 * 
	 * @throws Exception
	 */

	// @Test
	public void testSearchVmeYear() throws Exception {
		int year = 2012;
		String text = "lola";
		Vme vme = VmeMock.generateVme(1);
		vme.getValidityPeriod().setBeginYear(year);
		vme.getValidityPeriod().setEndYear(year);

		Authority a = new Authority();
		a.setAcronym(vme.getRfmo().getId());
		vmeDao.persist(a);

		vme.getSpecificMeasureList().get(0).setVmeSpecificMeasure((u.english(text)));
		vmeDao.saveVme(vme);
		List<VmeDto> list = dao.searchVme(0, 0, 0, year + 1, text);

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
		long type_id = 10l;
		long criteria_id = 0l;
		int year = 2012;
		String text = null;
		List<VmeDto> list = dao.searchVme(authority_id, type_id, criteria_id, year, text);
		assertEquals(0, list.size());

	}

	@Test
	public void testSearchVmeNullText() throws Exception {

		long authority_id = 0l;
		long type_id = 0l;
		long criteria_id = 0l;
		int year = 0;
		String text = null;
		List<VmeDto> list = dao.searchVme(authority_id, type_id, criteria_id, year, text);
		assertEquals(0, list.size());

	}

	/**
	 * 
	 * authority_id 0 type_id 0 criteria_id 0 year 2014 text "lophelia"
	 */
	@Test
	public void testSearchVmeOnlyText() throws Exception {

		long authority_id = 0l;
		long type_id = 0l;
		long criteria_id = 0l;
		int year = 0;
		String text = "lophelia";
		List<VmeDto> list = dao.searchVme(authority_id, type_id, criteria_id, year, text);
		assertEquals(0, list.size());

	}

	@Test
	public void testGetObservationById() {

	}

	@Test
	public void testGetObservationByInevntoryIdentifier() {

	}

}
