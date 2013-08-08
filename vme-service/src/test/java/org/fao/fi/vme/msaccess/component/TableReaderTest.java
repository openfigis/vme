package org.fao.fi.vme.msaccess.component;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.fao.fi.vme.msaccess.model.Table;
import org.fao.fi.vme.msaccess.tables.Measures_VME_General;
import org.fao.fi.vme.msaccess.tables.VME;
import org.junit.Before;
import org.junit.Test;

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

	@Test
	public void testReadVME() {
		Table table = tr.read(VME.class);
		List<Object> list = table.getObjectList();
		for (Object object : list) {
			VME v = (VME) object;

			assertNotNull(v.getRFB_ID());
		}
		assertTrue(list.size() > 0);
		assertTrue(list.get(0) instanceof VME);
	}

}
