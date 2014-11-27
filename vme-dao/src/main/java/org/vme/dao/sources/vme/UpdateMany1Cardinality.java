package org.vme.dao.sources.vme;

import java.util.List;

import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.model.ObjectId;

public class UpdateMany1Cardinality {

	/**
	 * This method will only shuffle references in lists and then merge the changes to the DB. It will never
	 * instantiate, nor persist new objects.
	 * 
	 * 
	 * @param em
	 * @param parentManaged
	 * @param parentDto
	 * @param listDto
	 * @return
	 */
	public <T> void update(EntityManager em, ObjectId<Long> parentManaged, ObjectId<Long> parentDto,
			List<T> listManaged, List<T> listDto) {

	}

}
