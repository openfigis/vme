package org.vme.dao.sources.vme;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.ObjectId;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class Update1nCardinality<T> {

	private static Set<Class<?>> CLASSES = new HashSet<Class<?>>();
	static {
		CLASSES.add(Long.class);
		CLASSES.add(String.class);
		CLASSES.add(Integer.class);
	}

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
			ObjectId<Long> objectDto = (ObjectId<Long>) dto;

			if (objectDto.getId() == null) {
				// a new object
				setParent(parent, objectDto);
				em.persist(dto);
			} else {
				// an eventual change
				@SuppressWarnings("unchecked")
				ObjectId<Long> objectEm = em.find(objectDto.getClass(), objectDto.getId());

				// delete it from the list which need to be need to be deleted.
				// (for the next loop, see below)
				toBeDeleted.remove(objectEm);

				copyCertainProperties(objectDto, objectEm);
				u.copyMultiLingual(objectDto, objectEm);
				em.merge(objectEm);
			}
		}
		for (T entity : toBeDeleted) {
			em.remove(entity);
			listEm.remove(entity);
		}

	}

	public void copyCertainProperties(Object source, Object destination) {
		PropertyUtilsBean u = new PropertyUtilsBean();
		PropertyDescriptor[] ds = u.getPropertyDescriptors(source);
		for (PropertyDescriptor d : ds) {
			if (CLASSES.contains(d.getPropertyType())) {
				try {
					BeanUtils.copyProperty(destination, d.getName(), BeanUtils.getProperty(source, d.getName()));
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					throw new VmeException(e);
				}
			}
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
