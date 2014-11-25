package org.vme.dao.sources.vme;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.BeanUtils;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.ObjectId;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class Update1nCardinality<T> {

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	/**
	 * this update also deletes an object from the DB if the dto list does have missing elements.
	 * 
	 * 
	 * @param em
	 * @param parent
	 * @param listDto
	 * @param listEm
	 */
	@SuppressWarnings("hiding")
	public <T> void update(EntityManager em, ObjectId<Long> parent, List<T> listDto, List<T> listEm) {
		List<T> toBeDeleted = new ArrayList<T>();
		for (T entity : listEm) {
			toBeDeleted.add(entity);
		}
		for (T dto : listDto) {
			@SuppressWarnings("unchecked")
			ObjectId<Long> dtoObject = (ObjectId<Long>) dto;

			if (dtoObject.getId() == null) {
				// a new object
				setParent(parent, dtoObject);
				em.persist(dto);
			} else {
				// an eventual change
				@SuppressWarnings("unchecked")
				ObjectId<Long> objectEm = em.find(dtoObject.getClass(), dtoObject.getId());

				// delete it from the list which need to be need to be deleted.
				toBeDeleted.remove(objectEm);
				try {
					BeanUtils.copyProperties(objectEm, dto);
				} catch (IllegalAccessException | InvocationTargetException e) {
					throw new VmeException(e);
				}
				u.copyMultiLingual(dto, objectEm);
				em.merge(objectEm);
			}
		}
		for (T entity : toBeDeleted) {
			em.remove(entity);
			listEm.remove(entity);
		}

	}

	private void setParent(ObjectId<Long> parent, ObjectId<Long> dto) {
		Method[] methods = dto.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("set") && method.getParameterTypes()[0].equals(parent.getClass())) {
				try {
					method.invoke(dto, parent);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new VmeException(e);
				}
			}
		}
	}
}
