/**
 * 
 */
package org.vme.service.hibernate.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.fao.fi.vme.domain.Authority;
import org.fao.fi.vme.domain.VmeCriteria;
import org.fao.fi.vme.domain.VmeType;
import org.vme.service.dao.JpaDaoFactory;
import org.vme.service.dao.ReferenceDAO;
import org.vme.service.dao.ReferenceServiceException;
import org.vme.service.dto.ref.Year;



/**
 * @author Fabrizio Sibeni
 * 
 */
public class ReferenceJpaDao implements ReferenceDAO {
	

	private JpaDaoFactory factory;
	
	private Map<Long, Year> repYear;

	private List<Class<?>> concepts = Arrays.asList(new Class<?>[]{Authority.class, VmeCriteria.class, VmeType.class, Year.class});
	
	
	public ReferenceJpaDao(JpaDaoFactory factory) {
		this.factory = factory;
		repYear = new LinkedHashMap<Long, Year>();
		createYears();
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
			return getAuthority(id);
		} else if (concept.equals(VmeCriteria.class)){
			return getVmeCriteria(id);
		} else if (concept.equals(VmeType.class)){
			return getVmeType(id);
		} else if(concept.equals(Year.class)){
			return getYear(id);
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
			return getAllAuthorities();
		} else if (concept.equals(VmeCriteria.class)){
			return getAllVmeCriterias();
		} else if (concept.equals(VmeType.class)){
			return getAllVmeTypes();
		} else if (concept.equals(Year.class)){
			return getAllYears();
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

	
	
	public Authority getAuthority(Long key) {
		List<?> res =  factory.getEntityManager().createQuery("from Authority where id = " + key).getResultList();	
		return res.size()>0? (Authority)res.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Authority> getAllAuthorities(){
		return   factory.getEntityManager().createQuery("from Authority").getResultList();	
	}
	
	
	public VmeCriteria getVmeCriteria(Long key) {
		List<?> res =  factory.getEntityManager().createQuery("from VmeCriteria where id = " + key).getResultList();	
		return res.size()>0? (VmeCriteria)res.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<VmeCriteria> getAllVmeCriterias(){
		return   factory.getEntityManager().createQuery("from VmeCriteria").getResultList();	
	}

	public VmeType getVmeType(Long key) {
		List<?> res =  factory.getEntityManager().createQuery("from VmeType where id = " + key).getResultList();	
		return res.size()>0? (VmeType)res.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<VmeType> getAllVmeTypes(){
		return   factory.getEntityManager().createQuery("from VmeType").getResultList();	
	}
	
	public Year getYear(Long key) {
		return repYear.get(key);
	}
	
	public List<Year> getAllYears(){
		return new LinkedList<Year>(repYear.values());
	}
	
	
	
	private void createYears() {
		repYear.put((long)2013, new Year(2013));
		repYear.put((long)2012, new Year(2012));
		repYear.put((long)2011, new Year(2011));
		repYear.put((long)2010, new Year(2010));
		repYear.put((long)2009, new Year(2009));
		repYear.put((long)2008, new Year(2008));
		repYear.put((long)2007, new Year(2007));
		repYear.put((long)2006, new Year(2006));
	}



}
