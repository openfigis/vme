/**
 * 
 */
package org.vme.service.dao.impl.hardcoded;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.fao.fi.vme.domain.Authority;
import org.fao.fi.vme.domain.VmeCriteria;
import org.fao.fi.vme.domain.VmeType;
import org.vme.service.dao.ReferenceDAO;
import org.vme.service.dao.ReferenceServiceException;
import org.vme.service.dto.ref.Year;



/**
 * @author Fabrizio Sibeni
 * 
 */
public class ReferenceHarcodedDao implements ReferenceDAO {



	private ReferenceHardcodedDaoHelper helper;

	private List<Class<?>> concepts = Arrays.asList(new Class<?>[]{Authority.class, VmeCriteria.class, VmeType.class, Year.class});

	public ReferenceHarcodedDao() {
		super();
		helper = new ReferenceHardcodedDaoHelper();
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
			try {
				if (concept.getDeclaredField("PARAMETER_ID").get(concept).toString().equalsIgnoreCase(acronym)){
					return concept;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ReferenceServiceException("Internal error");
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
		} else if (concept.equals(VmeCriteria.class)){
			return helper.getVmeCriteria(id);
		} else if (concept.equals(VmeType.class)){
			return helper.getVmeType(id);
		} else if(concept.equals(Year.class)){
			return helper.getYear(id);
		} else {
			throw new ReferenceServiceException("Undefined reference concept");
		}
	}





	/* (non-Javadoc)
	 * @see org.vme.service.reference.ReferenceService#getAllReferences(java.lang.Class)
	 */
	@Override
	public List<?> getAllReferences(Class<?> concept)	throws ReferenceServiceException {
		if (concept.equals(Authority.class)){
			return helper.getAllAuthorities();
		} else if (concept.equals(VmeCriteria.class)){
			return helper.getAllVmeCriterias();
		} else if (concept.equals(VmeType.class)){
			return helper.getAllVmeTypes();
		} else if (concept.equals(Year.class)){
			return helper.getAllYears();
		} else {
			throw new ReferenceServiceException("Undefined reference concept");
		}

	}

	/* (non-Javadoc)
	 * @see org.vme.service.reference.ReferenceService#getReferencebyAcronym(java.lang.Class, java.lang.String)
	 */
	@Override
	public Object getReferenceByAcronym(Class<?> concept, String acronym) throws ReferenceServiceException {

		Method getAcronymMethod = null;
		try {
			getAcronymMethod =  concept.getDeclaredMethod("getAcronym");
		} catch (NoSuchMethodException e) {
			throw new ReferenceServiceException("Undefined attribute acronym on this concept");
		}

		Collection<?> allObject = getAllReferences(concept);

		for (Object reference : allObject) {
			try {
				String acronymFound = (String)getAcronymMethod.invoke(reference);
				if (acronym.equals(acronymFound)) return reference;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}





}
