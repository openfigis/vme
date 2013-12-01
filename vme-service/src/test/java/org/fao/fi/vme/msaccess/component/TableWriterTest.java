package org.fao.fi.vme.msaccess.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.model.Table;
import org.fao.fi.vme.msaccess.tables.RFB_VME_Fishing_History;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.service.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FilesystemMsAccessConnectionProvider.class })
public class TableWriterTest {

	@Inject private VmeReader reader;

	@Inject TableWriter tableWriter;

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
			if (table.getClazz() != RFB_VME_Fishing_History.class) {
				// History has this exception of creating two domain objects out of 1 record, therefore we skip it in
				// the test.
				List<?> objects = vmeDao.loadObjects(clazz);
				assertEquals(table.getObjectList().size(), objects.size());
			}
		}
	}

}
