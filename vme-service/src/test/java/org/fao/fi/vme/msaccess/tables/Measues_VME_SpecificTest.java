package org.fao.fi.vme.msaccess.tables;

import org.fao.fi.vme.msaccess.component.ConnectionProvider;
import org.fao.fi.vme.msaccess.component.TableReader;
import org.fao.fi.vme.msaccess.mapping.TableDomainMapper;
import org.fao.fi.vme.msaccess.model.Table;
import org.fao.fi.vme.msaccess.tables.Measues_VME_Specific;
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
