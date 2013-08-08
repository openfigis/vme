package org.fao.fi.vme.msaccess.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.model.Table;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
public class TableWriterTest {

	VmeReader reader = new VmeReader();

	@Inject
	TableWriter tableWriter;

	MsAcces2DomainMapper mapper = new MsAcces2DomainMapper();

	@Inject
	VmeDao vmeDao;

	@Test
	public void testWrite() {
		List<Table> tables = reader.readObjects();
		assertTrue(tables.size() > 0);
		for (Table table : tables) {
			assertTrue(table.getObjectList().size() > 0);
			ObjectCollection objectCollection = mapper.map(table);
			tableWriter.write(objectCollection);
			Class<?> clazz = mapper.getDomainClass(table.getClazz());
			assertNotNull(clazz);
			List<?> objects = vmeDao.loadObjects(clazz);
			assertEquals(table.getObjectList().size(), objects.size());
		}
	}

}
