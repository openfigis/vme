package org.fao.fi.vme.msaccess.mapping;

import org.fao.fi.vme.msaccess.component.MsAccessConnectionProvider;
import org.fao.fi.vme.msaccess.component.TableReader;
import org.fao.fi.vme.msaccess.component.VmeReader;
import org.fao.fi.vme.msaccess.mapping.TableDomainMapper;
import org.fao.fi.vme.msaccess.model.Table;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TableDomainMapperTest {
	TableReader tr = new TableReader();
	MsAccessConnectionProvider cp = new MsAccessConnectionProvider();

	VmeReader r = new VmeReader();

	@Before
	public void before() {
		tr.setConnection(cp.getConnecton());
	}

	@Test
	public void testMap() {
		for (Class<?> clazz : r.getTables()) {
			Table table = tr.read(clazz);
			for (Object t : table.getObjectList()) {
				TableDomainMapper m = (TableDomainMapper) t;
				Object o = m.map();
				assertNotNull(m.getClass().getSimpleName(), o);
			}
		}

	}
}
