package org.vme.dao.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Alternative;

import org.fao.fi.vme.domain.model.reference.VmeCriteria;
import org.fao.fi.vme.domain.model.reference.VmeScope;
import org.fao.fi.vme.domain.model.reference.VmeType;
import org.fao.fi.vme.domain.test.VmeCriteriaMock;
import org.fao.fi.vme.domain.test.VmeScopeMock;
import org.fao.fi.vme.domain.test.VmeTypeMock;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.AcronymAwareReferenceConcept;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.ReferenceConcept;
import org.vme.dao.ReferenceDAO;
import org.vme.dao.ReferenceServiceException;

@ConceptProvider
@Alternative
public class ReferenceDaoMockImpl implements ReferenceDAO {

	private static Map<Class<?>, ReferenceConcept> refObjects = new HashMap<Class<?>, ReferenceConcept>();
	static {
		refObjects.put(VmeScope.class, VmeScopeMock.create());
//		refObjects.put(Authority.class, AuthorityMock.create());
		refObjects.put(VmeCriteria.class, VmeCriteriaMock.create());
		refObjects.put(VmeType.class, VmeTypeMock.create());
//		refObjects.put(ReferenceYear.class, ReferenceYearMock.create());
//		refObjects.put(InformationSourceType.class, InformationSourceTypeMock.create);
	}

	@Override
	public <R extends ReferenceConcept> List<R> getAllReferences(Class<R> arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R extends ReferenceConcept> R getReferenceByID(Class<R> arg0, Long arg1) throws Exception {
		return (R) refObjects.get(arg0);
	}

	@Override
	public List<Class<? extends ReferenceConcept>> getConcepts() throws ReferenceServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends ReferenceConcept> getConcept(String acronym) throws ReferenceServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReferenceConcept getReference(Class<? extends ReferenceConcept> concept, Long id)
			throws ReferenceServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcronymAwareReferenceConcept getReferenceByAcronym(Class<? extends AcronymAwareReferenceConcept> concept,
			String acronym) throws ReferenceServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
