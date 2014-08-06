package org.vme.dao.sources.vme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.test.VmeTypeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class })
public class VmeDaoTest {

	@Inject
	VmeDao dao;

	@Test
	public void testVmeProfile() {
	}

	@Test
	public void testCircelRelation() {
		String id = "fhuewiqof";
		Rfmo rfmo = new Rfmo();
		rfmo.setId(id);

		InformationSource i = new InformationSource();
		i.setRfmo(rfmo);

		SpecificMeasure s = new SpecificMeasure();
		s.setInformationSource(i);

		Vme v = new Vme();
		v.setRfmo(rfmo);

		List<Vme> vList = new ArrayList<Vme>();
		vList.add(v);
		rfmo.setListOfManagedVmes(vList);

		List<SpecificMeasure> sList4Vme = new ArrayList<SpecificMeasure>();
		sList4Vme.add(s);
		v.setSpecificMeasureList(sList4Vme);

		List<SpecificMeasure> sList4SpecificMeasure = new ArrayList<SpecificMeasure>();
		sList4SpecificMeasure.add(s);
		i.setSpecificMeasureList(sList4SpecificMeasure);

		List<InformationSource> iList4Rfmo = new ArrayList<InformationSource>();
		iList4Rfmo.add(i);
		rfmo.setInformationSourceList(iList4Rfmo);

		GeneralMeasure g = new GeneralMeasure();
		g.setRfmo(rfmo);

		List<InformationSource> iList4GeneralMeasure = new ArrayList<InformationSource>();
		iList4GeneralMeasure.add(i);
		g.setInformationSourceList(iList4GeneralMeasure);

		List<GeneralMeasure> gList4Rfmo = new ArrayList<GeneralMeasure>();
		gList4Rfmo.add(g);
		rfmo.setGeneralMeasureList(gList4Rfmo);

		List<GeneralMeasure> gList4IformationSource = new ArrayList<GeneralMeasure>();
		gList4IformationSource.add(g);
		i.setGeneralMeasureList(gList4IformationSource);

		List<Object> oList = new ArrayList<Object>();
		oList.add(rfmo);
		oList.add(v);
		oList.add(s);
		oList.add(i);
		oList.add(g);

		dao.persist(oList);
		for (Object object : oList) {
			assertTrue(dao.getEm().contains(object));
		}
	}

	@Test
	public void testMergeRfmo() {
		String id = "fiosdfsd";
		Rfmo rfmo = new Rfmo();
		rfmo.setId(id);
		dao.persist(rfmo);

		List<FisheryAreasHistory> hasFisheryAreasHistory = new ArrayList<FisheryAreasHistory>();
		FisheryAreasHistory h = new FisheryAreasHistory();
		h.setYear(2008);
		// h.setId(456l);

		dao.persist(h);

		hasFisheryAreasHistory.add(h);
		rfmo.setHasFisheryAreasHistory(hasFisheryAreasHistory);

		dao.merge(rfmo);

	}

	@Test
	public void testSave() {
		dao.persist(InformationSourceMock.createInformationSourceType());
		int nrOfyears = 1;
		Vme vme = VmeMock.generateVme(nrOfyears);
		dao.persist(VmeTypeMock.create());
		dao.saveVme(vme);
		assertNotNull(vme.getId());
		Vme vmeFound = dao.findVme(vme.getId());
		assertTrue(vmeFound.getRfmo().getGeneralMeasureList().size() > 0);
		for (GeneralMeasure gm : vmeFound.getRfmo().getGeneralMeasureList()) {
			assertNotNull(gm.getFishingArea());
		}

	}

	@Test
	public void testLoadVmes() {
		assertNotNull(dao);

		// Vme vme = new Vme();
		// String criteria = "go";
		// vme.setCriteria(criteria);
		dao.loadVmes();
	}

	@Test
	public void testCount() {
		assertEquals(0, dao.count(Vme.class).intValue());
		dao.persist(VmeTypeMock.create());

		dao.persist(VmeMock.create());
		assertEquals(1, dao.count(Vme.class).intValue());

	}

}
