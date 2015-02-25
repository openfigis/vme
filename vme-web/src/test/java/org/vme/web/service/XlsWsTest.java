package org.vme.web.service;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.vme.domain.test.RfmoMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.test.ReferenceDaoMockImpl;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoMockImpl.class, VmePersistenceUnitConfiguration.class,
		FigisPersistenceUnitConfiguration.class })
@AdditionalClasses({ FigisDataBaseProducer.class, VmeDataBaseProducerApplicationScope.class })
public class XlsWsTest {

	@Inject
	XlsWs xlsWs;

	@Test
	public void testName() {
		assertEquals(404, xlsWs.name("not known").getStatus());
		assertEquals(200, xlsWs.name(RfmoMock.ID).getStatus());
	}
}
