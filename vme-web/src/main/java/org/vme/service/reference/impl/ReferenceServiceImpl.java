/**
 * 
 */
package org.vme.service.reference.impl;

import java.util.Arrays;
import java.util.List;

import org.vme.service.reference.ReferenceService;
import org.vme.service.reference.ReferenceServiceException;
import org.vme.service.reference.domain.Authority;
import org.vme.service.reference.domain.VmeCriteria;
import org.vme.service.reference.domain.VmeType;



/**
 * @author Fabrizio Sibeni
 * 
 */
public class ReferenceServiceImpl implements ReferenceService {

	private ReferenceServiceHelper helper;
	
	private List<Class<?>> concepts = Arrays.asList(new Class<?>[]{Authority.class, VmeCriteria.class, VmeType.class});
	
	public ReferenceServiceImpl() {
		super();
		helper = new ReferenceServiceHelper();
	}

	/* (non-Javadoc)
	 * @see org.vme.service.reference.ReferenceService#getConcepts()
	 */
	@Override
	public List<Class<?>> getConcepts() throws ReferenceServiceException {
		return concepts;
	}

	/* (non-Javadoc)
	 * @see org.vme.service.reference.ReferenceService#getConcept(java.lang.String)
	 */
	@Override
	public Class<?> getConcept(String acronym) throws ReferenceServiceException {
		for (Class<?> concept : concepts) {
			if (concept.getSimpleName().endsWith(acronym)){
				return concept;
			}
			
		}
		throw new ReferenceServiceException("Undefined reference concept");
	}

	/* (non-Javadoc)
	 * @see org.vme.service.reference.ReferenceService#getReference(java.lang.Class, java.lang.Long)
	 */
	@Override
	public Object getReference(Class<?> concept, Long id) throws ReferenceServiceException  {
		if (concept.equals(Authority.class)){
			return helper.getAuthority(id);
		}
		if (concept.equals(VmeCriteria.class)){
			return helper.getVmeCriteria(id);
		}
		if (concept.equals(VmeType.class)){
			return helper.getVmeType(id);
		}
		throw new ReferenceServiceException("Undefined reference concept");
	
	}
	

	
	
}
