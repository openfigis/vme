package org.fao.fi.vme.msaccess.mapping;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.fao.fi.vme.batch.reference.ReferenceDataHardcodedBatch;
import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.fao.fi.vme.msaccess.component.MsAccessConnectionProvider;
import org.fao.fi.vme.msaccess.component.TableReader;
import org.fao.fi.vme.msaccess.component.VmeReader;
import org.fao.fi.vme.msaccess.model.Table;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FilesystemMsAccessConnectionProvider.class, VmeDataBaseProducer.class,
		VmeTestPersistenceUnitConfiguration.class })
@AdditionalClasses({ ReferenceDaoImpl.class })
public class TableDomainMapperTest {
	TableReader tr = new TableReader();

	@Inject
	@ConceptProvider
	private ReferenceDaoImpl conceptProvider;
	@Inject
	private MsAccessConnectionProvider cp;

	@Inject
	private VmeReader reader;

	@Inject
	ReferenceDataHardcodedBatch rData;

	@Before
	public void before() {
		tr.setConnection(cp.getConnection());
		rData.run();
	}

	@Test
	@Ignore("because msaccess is not supported anymore")
	public void testMap() {
		for (Class<?> clazz : reader.getTables()) {
			Table table = tr.read(clazz);
			for (Object t : table.getObjectList()) {
				TableDomainMapper m = (TableDomainMapper) t;
				Object o = null;

				if (m instanceof ReferenceDependentTableDomainMapper)
					o = ((ReferenceDependentTableDomainMapper) m).map(this.conceptProvider);
				else
					o = m.map();

				assertNotNull(m.getClass().getSimpleName(), o);
			}
		}

	}
}
