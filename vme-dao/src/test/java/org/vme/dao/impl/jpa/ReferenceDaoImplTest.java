package org.vme.dao.impl.jpa;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.ReferenceYear;
import org.fao.fi.vme.domain.model.reference.VmeCriteria;
import org.fao.fi.vme.domain.model.reference.VmeScope;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.ReferenceDAO;
import org.vme.dao.ReferenceServiceException;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, VmePersistenceUnitConfiguration.class, })
@AdditionalClasses(VmeDataBaseProducerApplicationScope.class)
public class ReferenceDaoImplTest {

	@Inject
	@ConceptProvider
	ReferenceDAO referenceDao;

	@Test
	public void testGetConcepts() throws ReferenceServiceException {
		assertEquals(7, referenceDao.getConcepts().size());
	}

	@Test
	public void testGetConcept() throws Exception {
		assertEquals(VmeCriteria.class, referenceDao.getConcept("criteria"));
		assertEquals(VmeScope.class, referenceDao.getConcept("scope"));
		assertEquals(ReferenceYear.class, referenceDao.getConcept("years"));

		referenceDao.getAllReferences(ReferenceYear.class);

	}

	@Test
	public void testYears() throws Exception {
		referenceDao.getAllReferences(ReferenceYear.class);

	}

}
