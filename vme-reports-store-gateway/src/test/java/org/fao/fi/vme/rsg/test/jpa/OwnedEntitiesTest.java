/**
 * (c) 2013 FAO / UN (project: vme-dao)
 */
package org.fao.fi.vme.rsg.test.jpa;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.VMEsHistory;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- ----------------------- Date Author Comment
 * ------------- --------------- ----------------------- 11 Dec 2013 Fiorellato
 * Creation.
 *
 * @version 1.0
 * @since 11 Dec 2013
 */
@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeTestPersistenceUnitConfiguration.class, ReferenceDaoImpl.class })
@AdditionalClasses(VmeDataBaseProducerApplicationScope.class)
public class OwnedEntitiesTest {
	@Inject
	VmeDao _vmeDao;

	@Before
	public void setup() throws Throwable {
		Vme vme = VmeMock.generateVme(3);

		_vmeDao.saveVme(vme);
	}

	@Test
	public void testCheckVME() throws Throwable {
		Vme vme = this._vmeDao.getEntityById(this._vmeDao.getEm(), Vme.class, new Long(1));

		Assert.assertNotNull(vme);
		Assert.assertNotNull(vme.getRfmo());
	}

	@Test
	@Ignore
	public void testCheckGeneralMeasure() throws Throwable {
		GeneralMeasure gm = this._vmeDao.getEntityById(this._vmeDao.getEm(), GeneralMeasure.class, new Long(1));

		Assert.assertNotNull(gm);
		Assert.assertNotNull(gm.getRfmo());
	}

	@Test
	@Ignore
	public void testCheckFisheryAreasHistory() throws Throwable {
		FisheryAreasHistory fah = this._vmeDao.getEntityById(this._vmeDao.getEm(), FisheryAreasHistory.class, new Long(
				1));

		Assert.assertNotNull(fah);
		Assert.assertNotNull(fah.getRfmo());
	}

	@Test
	@Ignore
	public void testCheckVMEsHistory() throws Throwable {
		VMEsHistory vmh = this._vmeDao.getEntityById(this._vmeDao.getEm(), VMEsHistory.class, new Long(1));

		Assert.assertNotNull(vmh);
		Assert.assertNotNull(vmh.getRfmo());
	}

	@Test
	@Ignore
	public void testCheckInformationSources() throws Throwable {
		InformationSource is = this._vmeDao.getEntityById(this._vmeDao.getEm(), InformationSource.class, new Long(1));

		Assert.assertNotNull(is.getRfmo());

		System.out.println(is.getRfmo());
	}
}