package org.fao.fi.vme.msaccess.component;

import org.fao.fi.vme.msaccess.component.ConnectionProvider;
import org.fao.fi.vme.msaccess.component.TableReader;
import org.fao.fi.vme.msaccess.model.Table;
import org.fao.fi.vme.msaccess.tables.Measures_VME_General;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TableReaderTest {

	TableReader tr = new TableReader();
	ConnectionProvider cp = new ConnectionProvider();

	@Before
	public void before() {
		tr.setConnection(cp.getConnecton());
	}

	@Test
	public void testRead() {
		Table table = tr.read(Measures_VME_General.class);
		assertTrue(table.getObjectList().size() > 0);
	}
}
