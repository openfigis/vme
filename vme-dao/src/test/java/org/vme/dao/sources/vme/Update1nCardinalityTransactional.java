package org.vme.dao.sources.vme;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.ObjectId;

public class Update1nCardinalityTransactional extends Update1nCardinality {

	@Override
	public <T> void update(EntityManager em, ObjectId<Long> parent, List<T> listDto, List<T> listEm) {

		EntityTransaction t = em.getTransaction();
		t.begin();
		super.update(em, parent, listDto, listEm);
		t.commit();

	}

}
