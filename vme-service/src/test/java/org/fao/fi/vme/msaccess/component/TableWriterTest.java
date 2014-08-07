package org.fao.fi.vme.msaccess.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.VMEsHistory;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.model.Table;
import org.fao.fi.vme.msaccess.tables.RFB_VME_Fishing_History;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, VmeTestPersistenceUnitConfiguration.class,
		FilesystemMsAccessConnectionProvider.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class, MsAcces2DomainMapper.class })
public class TableWriterTest {

	@Inject
	private VmeReader reader;

	@Inject
	TableWriter tableWriter;

	@Inject
	MsAcces2DomainMapper mapper;

	@Inject
	VmeDao vmeDao;

	@Test
	public void testPersistNew() throws InstantiationException, IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {

		Class<?> classes[] = { /* Authority.class, */GeneralMeasure.class, GeoRef.class, FisheryAreasHistory.class,
				VMEsHistory.class, InformationSource.class, MultiLingualString.class, Profile.class,
				SpecificMeasure.class, Vme.class /* , VmeCriteria.class */};
		for (Class<?> clazz : classes) {
			delegateTestPersistNew(clazz);
		}

	}

	private void delegateTestPersistNew(Class<?> clazz) throws InstantiationException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {
		ObjectCollection oc = new ObjectCollection();
		oc.setClazz(clazz);
		List<Object> objectList = new ArrayList<Object>();
		oc.setObjectList(objectList);

		Object o = clazz.newInstance();
		oc.getObjectList().add(o);

		try {
			tableWriter.persistNew(oc);
		} catch (Throwable t) {
			throw new RuntimeException("Can't persist object collection " + oc, t);
		}

	}

	/**
	 * TODO can be deleted.
	 */

	// @Test
	public void testWrite() {
		List<Table> tables = reader.readObjects();
		assertTrue(tables.size() > 0);
		for (Table table : tables) {
			System.out.println(table.getClazz().getSimpleName());
			assertTrue(table.getObjectList().size() > 0);
			ObjectCollection objectCollection = mapper.map(table);
			assertTrue(objectCollection.getObjectList().size() > 0);
			tableWriter.write(objectCollection);
			Class<?> clazz = mapper.getDomainClass(table.getClazz());
			assertNotNull(clazz);
			if (table.getClazz() != RFB_VME_Fishing_History.class) {
				// History has this exception of creating two domain objects out
				// of 1 record, therefore we skip it in
				// the test.
				List<?> objects = vmeDao.loadObjects(clazz);
				String message = "Original table is " + table.getClazz().getSimpleName() + ", Vme table is "
						+ clazz.getSimpleName();
				assertEquals(message, table.getObjectList().size(), objects.size());
			}
		}
	}

}
