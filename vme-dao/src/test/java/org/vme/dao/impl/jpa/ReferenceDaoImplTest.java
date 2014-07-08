package org.vme.dao.impl.jpa;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.reference.VmeCriteria;
import org.fao.fi.vme.domain.model.reference.VmeScope;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.ReferenceDAO;
import org.vme.dao.ReferenceServiceException;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, VmeTestPersistenceUnitConfiguration.class,
		VmeDataBaseProducerApplicationScope.class })
public class ReferenceDaoImplTest {

	@Inject
	@ConceptProvider
	ReferenceDAO referenceDao;

	@Test
	public void testGetConcepts() throws ReferenceServiceException {
		assertEquals(6, referenceDao.getConcepts().size());
	}

	@Test
	public void testGetConcept() throws ReferenceServiceException {
		assertEquals(VmeCriteria.class, referenceDao.getConcept("criteria"));
		assertEquals(VmeScope.class, referenceDao.getConcept("scope"));
	}

}
