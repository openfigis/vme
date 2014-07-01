package org.fao.fi.vme.msaccess.tables;

import javax.inject.Inject;

import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.fao.fi.vme.msaccess.component.MsAccessConnectionProvider;
import org.fao.fi.vme.msaccess.component.TableReader;
import org.fao.fi.vme.msaccess.mapping.TableDomainMapper;
import org.fao.fi.vme.msaccess.model.Table;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FilesystemMsAccessConnectionProvider.class })
public class Measues_VME_SpecificTest {

	TableReader tr = new TableReader();
	@Inject
	private MsAccessConnectionProvider cp;

	@Before
	public void before() {
		tr.setConnection(cp.getConnection());
	}

	@Test
	@Ignore("TODO delete MS acces related stuff")
	public void testMap() {
		Table table = tr.read(Measues_VME_Specific.class);
		for (Object o : table.getObjectList()) {
			TableDomainMapper m = (TableDomainMapper) o;
			m.map();
		}
	}
}
