package org.fao.fi.vme.dao.msaccess.mapping;

import org.fao.fi.vme.dao.msaccess.ConnectionProvider;
import org.fao.fi.vme.dao.msaccess.Table;
import org.fao.fi.vme.dao.msaccess.TableReader;
import org.fao.fi.vme.dao.msaccess.VmeReader;
import org.fao.fi.vme.dao.msaccess.mapping.TableDomainMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TableDomainMapperTest {
	TableReader tr = new TableReader();
	ConnectionProvider cp = new ConnectionProvider();

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
