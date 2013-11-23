package org.vme.service.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.GeneralMeasureMock;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.VmeMock;

public abstract class Dao {

	@SuppressWarnings("unchecked")
	public <E extends Object> E getByID(EntityManager em, Class<E> entity, long id) {
		if(entity.equals(Vme.class))
			return (E)VmeMock.create();
		else if(entity.equals(GeneralMeasure.class))
			return (E)GeneralMeasureMock.create();
		else if(entity.equals(InformationSource.class))
			return (E)InformationSourceMock.create();
		
		Query query = em.createQuery("select o from " + entity.getSimpleName() + " o where o.id = :id");
		query.setParameter("id", id);
		
		return (E)query.getSingleResult();
	}
	
	protected TypedQuery<?> generateTypedQuery(EntityManager em, Class<?> clazz) {
		
		String queryString = " select o from  " + clazz.getCanonicalName() + " o ";
		return generateTypedQuery(em, clazz, queryString);
	}

	protected List<?> selectFrom(EntityManager em, Class<?> clazz) {
		return this.generateTypedQuery(em, clazz).getResultList();
	}

	protected List<?> loadObjects(EntityManager em, Class<?> clazz) {
		return this.generateTypedQuery(em, clazz).getResultList();
	}

	protected TypedQuery<?> generateTypedQuery(EntityManager em, Class<?> clazz, String queryString) {
		TypedQuery<?> query = em.createQuery(queryString, clazz);
		return query;
	}

	protected Long count(EntityManager em, Class<?> clazz) {
		String queryString = " select count(o) from  " + clazz.getCanonicalName() + " o ";
		Query query = em.createQuery(queryString);
		return (Long) query.getSingleResult();
	}
}
