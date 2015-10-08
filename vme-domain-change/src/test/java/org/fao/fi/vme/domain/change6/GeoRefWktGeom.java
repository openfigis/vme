package org.fao.fi.vme.domain.change6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeoRef;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

/**
 * Scripts for this change are to be found in /vme-sql/gis
 * 
 * @author Erik van Ingen
 *
 */

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class GeoRefWktGeom {

	@Inject
	VmeDao dao;

	/**
	 * Update in VME dev took 92 seconds.
	 */
	@Test
	public void applyGeoRefWktGeom() {
		assertNotNull(dao);
		assertEquals(dao.loadObjects(GeoRef.class).size(), 195);

	}

}
