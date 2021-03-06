package org.vme.dao.sources.vme;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.GeneralMeasureMock;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.RfmoMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class VmeDaoTestGm {

	@Inject
	private VmeDao dao;

	@Test
	@Ignore
	public void find() throws Throwable {
		Vme vme = dao.getEm().find(Vme.class, 23787l);
		System.out.println(vme.getSpecificMeasureList().size());

		// Vme vme2 = dao.getEntityById(dao.getEm(), Vme.class, 23787l);
		// System.out.println(vme2.getSpecificMeasureList().size());

	}

	@Test
	@Ignore
	public void analyzingDoubleSpecificMeasures() throws Throwable {
		Vme vme = new Vme();
		SpecificMeasure sm = new SpecificMeasure();

		sm.setVme(vme);

		List<SpecificMeasure> list = new ArrayList<SpecificMeasure>();
		list.add(sm);
		vme.setSpecificMeasureList(list);

		EntityTransaction et = dao.getEm().getTransaction();
		et.begin();
		dao.getEm().persist(vme);
		et.commit();
		System.out.println(vme.getId());

	}

	@Test
	@Ignore
	public void testCreateGeneralMeasure() throws Throwable {

		Rfmo rfmo = RfmoMock.create();

		GeneralMeasure gm = GeneralMeasureMock.create();
		gm.setRfmo(rfmo);

		EntityTransaction et = dao.getEm().getTransaction();
		et.begin();
		dao.create(gm);
		et.commit();

		System.out.println(dao.getEm());
		assertEquals(1, dao.loadObjects(GeneralMeasure.class).size());

	}

	@Test
	@Ignore
	public void testUpdateGmAddInformationSource() throws Throwable {

		GeneralMeasure gm = GeneralMeasureMock.create();
		InformationSource im1 = InformationSourceMock.create();
		dao.persist(InformationSourceMock.createInformationSourceType());
		dao.persist(gm);
		dao.persist(im1);
		gm.setInformationSourceList(new ArrayList<InformationSource>());
		gm.getInformationSourceList().add(im1);

		EntityTransaction et = dao.getEm().getTransaction();
		et.begin();
		dao.update(gm);
		et.commit();

		InformationSource im2 = InformationSourceMock.create();
		dao.persist(im2);
		gm.getInformationSourceList().add(im2);

		EntityTransaction et2 = dao.getEm().getTransaction();
		et2.begin();
		dao.update(gm);

		dao.getEm().merge(gm);
		dao.getEm().merge(gm);
		dao.getEm().merge(gm);
		et2.commit();

		assertEquals(1, dao.loadObjects(GeneralMeasure.class).size());
		assertEquals(2, dao.loadObjects(InformationSource.class).size());

	}
}
