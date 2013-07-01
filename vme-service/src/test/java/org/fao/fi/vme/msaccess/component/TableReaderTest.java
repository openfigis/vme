package org.fao.fi.vme.msaccess.component;

import java.util.List;

import org.fao.fi.vme.msaccess.model.Table;
import org.fao.fi.vme.msaccess.tables.Measures_VME_General;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TableReaderTest {

	TableReader tr = new TableReader();
	MsAccessConnectionProvider cp = new MsAccessConnectionProvider();

	@Before
	public void before() {
		tr.setConnection(cp.getConnecton());
	}

	@Test
	public void testRead() {
		Table table = tr.read(Measures_VME_General.class);
		List<Object> list = table.getObjectList();
		assertTrue(list.size() > 0);
		assertTrue(list.get(0) instanceof Measures_VME_General);
	}
}
