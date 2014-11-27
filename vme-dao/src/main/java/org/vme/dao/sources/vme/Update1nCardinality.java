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
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.ObjectId;
import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class Update1nCardinality<T> {

	UpdateMany1Cardinality uMany1 = new UpdateMany1Cardinality();

	private static Set<Class<?>> CLASSES = new HashSet<Class<?>>();
	static {
		CLASSES.add(Long.class);
		CLASSES.add(String.class);
		CLASSES.add(Integer.class);
		CLASSES.add(ValidityPeriod.class);
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

				copyProperties(em, objectDto, objectEm);

				// do long, integer and string
				copyCertainProperties(objectDto, objectEm);

				// do the multilingual stuff
				u.copyMultiLingual(objectDto, objectEm);

				// the all others
				processOtherProperties(em, objectDto, objectEm);
				em.merge(objectEm);
			}
		}
		for (T entity : toBeDeleted) {
			em.remove(entity);
			listEm.remove(entity);
		}

	}

	@SuppressWarnings("unchecked")
	public void copyProperties(EntityManager em, ObjectId<Long> objectDto, ObjectId<Long> objectEm) {
		PropertyUtilsBean u = new PropertyUtilsBean();
		PropertyDescriptor[] ds = u.getPropertyDescriptors(objectDto);
		for (PropertyDescriptor d : ds) {
			try {
				if (CLASSES.contains(d.getPropertyType())) {
					// this is for long, string, integer and ValidityPeriod
					u.setProperty(objectEm, d.getName(), u.getProperty(objectDto, d.getName()));
				}

				// TODO do the multilingual stuff

				// this is for other properties
				if (!CLASSES.contains(d.getPropertyType()) && !d.getPropertyType().equals(MultiLingualString.class)) {
					// this property can be 1toMany or 1toOne. For now ONLY the 1toMany is implemented
					ObjectId<Long> memberDto = (ObjectId<Long>) u.getProperty(objectDto, d.getName());
					ObjectId<Long> memberEm = em.find(objectDto.getClass(), memberDto.getId());
					u.setProperty(objectEm, d.getName(), memberEm);
					em.merge(objectEm);

					// now we need to update the other end of the 1toMany relation

					PropertyDescriptor[] dsMemeber = u.getPropertyDescriptors(memberDto);
					for (PropertyDescriptor propertyDescriptor : dsMemeber) {
						if (propertyDescriptor.getPropertyType().equals(List.class)) {
							List<ObjectId<Long>> listDto = (List<ObjectId<Long>>) u.getProperty(memberDto,
									propertyDescriptor.getName());
							List<ObjectId<Long>> listEm = (List<ObjectId<Long>>) u.getProperty(memberEm,
									propertyDescriptor.getName());
							// copyList(em, listDto, listEm);
						}
					}
				}
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new VmeException(e);
			}
		}

	}

	/**
	 * copy all other properties, except: List, Long, Integer, String, MultiLingualString
	 * 
	 * 
	 * @param em
	 * @param objectDto
	 * @param objectEm
	 */
	public void processOtherProperties(EntityManager em, ObjectId<Long> source, ObjectId<Long> destination) {
		PropertyUtilsBean u = new PropertyUtilsBean();
		PropertyDescriptor[] ds = u.getPropertyDescriptors(source);
		for (PropertyDescriptor d : ds) {
			System.out.println(d.getPropertyType());

			if (!CLASSES.contains(d.getPropertyType()) && !d.getPropertyType().equals(MultiLingualString.class)
					&& !d.getPropertyType().equals(List.class) && !d.getPropertyType().equals(Class.class)) {
				try {
					System.out.println("hit!");

					// ObjectId<Long> sourcePropertyUtils.getProperty(source, d.getName());

					PropertyUtils.setProperty(destination, d.getName(), PropertyUtils.getProperty(source, d.getName()));
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					throw new VmeException(e);
				}
			}
		}

	}

	/**
	 * Copy only the Long, Integer and String properties
	 * 
	 * 
	 * @param source
	 * @param destination
	 */
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
