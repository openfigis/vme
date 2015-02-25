package org.vme.dao.test;

import org.fao.fi.vme.domain.test.RfmoMock;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.AcronymAwareReferenceConcept;

public class AcronymAwareReferenceConceptMock implements AcronymAwareReferenceConcept {

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return 456456l;
	}

	@Override
	public String getAcronym() {
		return RfmoMock.ID;
	}

}
