package org.fao.fi.vme.msaccess.component;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.msaccess.model.Table;
import org.fao.fi.vme.msaccess.tables.Measures_VME_General;
import org.fao.fi.vme.msaccess.tables.VME;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FilesystemMsAccessConnectionProvider.class })
public class TableReaderTest {

	TableReader tr = new TableReader();
	@Inject
	private MsAccessConnectionProvider cp;

	@Before
	public void before() {
		tr.setConnection(cp.getConnection());
	}

	@Test
	@Ignore("TODO delete MS acces related stuff")
	public void testRead() {
		Table table = tr.read(Measures_VME_General.class);
		List<Object> list = table.getObjectList();
		assertTrue(list.size() > 0);
		assertTrue(list.get(0) instanceof Measures_VME_General);
	}

	@Test
	@Ignore("TODO delete MS acces related stuff")
	public void testReadVME() {
		Table table = tr.read(VME.class);
		List<Object> list = table.getObjectList();
		for (Object object : list) {
			VME v = (VME) object;

			assertNotNull(v.getRfbId());
		}
		assertTrue(list.size() > 0);
		assertTrue(list.get(0) instanceof VME);
	}

}
