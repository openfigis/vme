package org.fao.fi.vme.dao.msaccess.tables;

import org.fao.fi.vme.dao.msaccess.ConnectionProvider;
import org.fao.fi.vme.dao.msaccess.Table;
import org.fao.fi.vme.dao.msaccess.TableReader;
import org.fao.fi.vme.dao.msaccess.mapping.TableDomainMapper;
import org.junit.Before;
import org.junit.Test;

public class Measues_VME_SpecificTest {

	TableReader tr = new TableReader();
	ConnectionProvider cp = new ConnectionProvider();

	@Before
	public void before() {
		tr.setConnection(cp.getConnecton());
	}

	@Test
	public void testMap() {
		Table table = tr.read(Measues_VME_Specific.class);
		for (Object o : table.getObjectList()) {
			TableDomainMapper m = (TableDomainMapper) o;
			m.map();
		}
	}
}
