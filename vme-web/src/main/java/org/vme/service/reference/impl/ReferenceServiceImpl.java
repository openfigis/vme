/**
 * 
 */
package org.vme.service.reference.impl;

import java.util.List;

import org.vme.service.reference.ReferenceService;
import org.vme.service.reference.ReferenceServiceException;



/**
 * @author Fabrizio Sibeni
 * 
 */
public class ReferenceServiceImpl implements ReferenceService {

	private ReferenceServiceHelper helper;
	
	public ReferenceServiceImpl() {
		super();
		helper = new ReferenceServiceHelper();
	}

	/* (non-Javadoc)
	 * @see org.vme.service.reference.ReferenceService#getConcepts()
	 */
	@Override
	public List<Class<?>> getConcepts() throws ReferenceServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.vme.service.reference.ReferenceService#getConcept(java.lang.String)
	 */
	@Override
	public Class<?> getConcept(String acronym) throws ReferenceServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.vme.service.reference.ReferenceService#getReference(java.lang.Class, java.lang.Long)
	 */
	@Override
	public Object getReference(Class<?> concept, Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	
}
