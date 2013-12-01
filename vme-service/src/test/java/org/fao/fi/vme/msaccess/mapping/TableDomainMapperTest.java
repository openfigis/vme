package org.fao.fi.vme.msaccess.mapping;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.fao.fi.vme.msaccess.component.MsAccessConnectionProvider;
import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.fao.fi.vme.msaccess.component.TableReader;
import org.fao.fi.vme.msaccess.component.VmeReader;
import org.fao.fi.vme.msaccess.model.Table;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FilesystemMsAccessConnectionProvider.class })
public class TableDomainMapperTest {
	TableReader tr = new TableReader();
	
	@Inject private MsAccessConnectionProvider cp;

	@Inject private VmeReader reader;

	@Before
	public void before() {
		tr.setConnection(cp.getConnection());
	}

	@Test
	public void testMap() {
		for (Class<?> clazz : reader.getTables()) {
			Table table = tr.read(clazz);
			for (Object t : table.getObjectList()) {
				TableDomainMapper m = (TableDomainMapper) t;
				Object o = m.map();
				assertNotNull(m.getClass().getSimpleName(), o);
			}
		}

	}
}
