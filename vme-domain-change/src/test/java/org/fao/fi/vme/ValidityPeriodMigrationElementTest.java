package org.fao.fi.vme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.InformationSourceType;
import org.fao.fi.vme.domain.model.Period;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class,
		FilesystemMsAccessConnectionProvider.class })
public class ValidityPeriodMigrationElementTest {

	@Inject
	ValidityPeriodMigrationElement v;

	@Inject
	VmeDao vmeDao;

	@Test
	public void testMigrate() {
		InformationSourceType defaultIST = InformationSourceMock.createInformationSourceType();
		vmeDao.persist(defaultIST);
		Vme vme = VmeMock.generateVme(2);
		vmeDao.saveVme(vme);

		int number = vmeDao.loadObjects(InformationSource.class).size();

		v.migrate();
		// vmeDao.getEm().clear();
		delegate(vmeDao.loadObjects(SpecificMeasure.class));
		delegate(vmeDao.loadObjects(Vme.class));
		delegate(vmeDao.loadObjects(GeneralMeasure.class));

		assertEquals(number, vmeDao.loadObjects(InformationSource.class).size());

	}

	<T> void delegate(List<T> loadObjects) {
		for (T t : loadObjects) {
			Period p = (Period) t;
			System.out.println(p.getValidityPeriod().getBeginYear());
			System.out.println(p.getValidityPeriod().getEndYear());
			assertNotNull(p.getValidityPeriod().getBeginDate());
			assertNotNull(p.getValidityPeriod().getEndDate());
		}

	}

}
