package org.vme.dao.sources.vme;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.ObjectId;

/**
 * Managing many to 1 relations with JPA
 * 
 * @author Erik van Ingen
 *
 */
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
	@SuppressWarnings("unchecked")
	public <T> void update(EntityManager em, ObjectId<Long> parentManaged, ObjectId<Long> memberManaged,
			ObjectId<Long> memberDto, ObjectId<Long> parentDto, List<T> listManaged, List<T> listDto,
			String propertyName) {

		boolean touched = false;

		// 0 to 0
		if (memberManaged == null && memberDto == null) {
			touched = true;
		}

		// 0 to 1
		if (!touched && memberManaged == null && memberDto != null) {
			memberManaged = em.find(memberDto.getClass(), memberDto.getId());
			setProperty(parentManaged, propertyName, memberManaged);
			listManaged.add((T) memberManaged);
			em.merge(memberManaged);
			em.merge(parentManaged);
			touched = true;
		}

		// 1 to 0
		if (!touched && memberManaged != null && memberDto == null) {
			setProperty(parentManaged, propertyName, null);
			listManaged.remove((T) memberManaged);
			em.merge(memberManaged);
			em.merge(parentManaged);
			touched = true;
		}

		// 1 to 1
		if (!touched && memberManaged != null && memberDto != null && memberManaged.getId().equals(memberDto.getId())) {
			// do nothing
			touched = true;
		}

		// 1 to another 1
		if (!touched && memberManaged != null && memberDto != null && !memberManaged.getId().equals(memberDto.getId())) {
			ObjectId<Long> anotherMember = em.find(memberDto.getClass(), memberDto.getId());
			setProperty(parentManaged, propertyName, anotherMember);
			listManaged.remove((T) memberManaged);
			listManaged.add((T) anotherMember);
			em.merge(memberManaged);
			em.merge(parentManaged);
			touched = true;
		}

		if (!touched) {
			throw new VmeException("UpdateMany1Cardinality has not done its work for parentManaged = "
					+ parentManaged.getClass() + parentManaged.getId());
		}

	}

	private void setProperty(ObjectId<Long> object, String propertyName, ObjectId<Long> value) {
		PropertyUtilsBean u = new PropertyUtilsBean();
		try {
			u.setProperty(object, propertyName, value);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new VmeException(e);
		}
	}

}
