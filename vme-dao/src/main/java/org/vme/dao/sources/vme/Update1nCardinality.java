package org.vme.dao.sources.vme;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.ObjectId;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class Update1nCardinality {

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
	@SuppressWarnings({ "unchecked" })
	public <T> void update(EntityManager em, ObjectId<Long> parent, List<T> listDto, List<T> listEm) {
		List<T> toBeDeleted = new ArrayList<T>();
		for (T entity : listEm) {
			toBeDeleted.add(entity);
		}
		for (T dto : listDto) {
			ObjectId<Long> objectDto = (ObjectId<Long>) dto;

			if (objectDto.getId() == null) {
				// a new object
				setParent(parent, objectDto);
				listEm.add((T) objectDto);
				em.persist(objectDto);
				if (objectDto instanceof SpecificMeasure
						&& ((SpecificMeasure) objectDto).getInformationSource() != null) {
					SpecificMeasure sm = (SpecificMeasure) objectDto;
					if (sm.getInformationSource().getSpecificMeasureList() == null) {
						sm.getInformationSource().setSpecificMeasureList(new ArrayList<SpecificMeasure>());
					}
					sm.getInformationSource().getSpecificMeasureList().add(sm);
					em.merge(sm.getInformationSource());
				}
				em.merge(dto);
			} else {
				// an eventual change
				ObjectId<Long> objectEm = em.find(objectDto.getClass(), objectDto.getId());

				// delete it from the list which need to be need to be deleted.
				// (for the next loop, see below)
				toBeDeleted.remove(objectEm);

				copyProperties(em, parent, objectDto, objectEm);

				em.merge(objectEm);
			}
		}
		for (T entity : toBeDeleted) {
			// TODO update manyToOne relations here
			// what is missing here is that the manyToOne relations need to be updated.
			em.remove(entity);
			listEm.remove(entity);
		}

	}

	private void copyProperties(EntityManager em, ObjectId<Long> parentManaged, ObjectId<Long> objectDto,
			ObjectId<Long> objectEm) {
		PropertyUtilsBean pu = new PropertyUtilsBean();
		PropertyDescriptor[] ds = pu.getPropertyDescriptors(objectDto.getClass());

		for (PropertyDescriptor d : ds) {
			try {
				if (CLASSES.contains(d.getPropertyType())) {
					// this is for long, string, integer and ValidityPeriod
					pu.setProperty(objectEm, d.getName(), pu.getProperty(objectDto, d.getName()));
				}

				// do the multilingual stuff
				u.copyMultiLingual(objectDto, objectEm);

				// this is for other properties (which can only be many to 1 relations)
				// if (!CLASSES.contains(d.getPropertyType()) && !d.getPropertyType().equals(MultiLingualString.class)
				// && !d.getPropertyType().equals(Class.class)
				// && !d.getPropertyType().equals(parentManaged.getClass())) {
				//
				// // this property can be 1toMany or 1toOne. For now ONLY the 1toMany is implemented
				// ObjectId<Long> memberDto = (ObjectId<Long>) pu.getProperty(objectDto, d.getName());
				// ObjectId<Long> memberEm = null;
				//
				// if (memberDto != null) {
				// memberEm = em.find(objectDto.getClass(), memberDto.getId());
				// }
				//
				// // now we need to update the other end of the 1toMany relation
				// PropertyDescriptor[] dsMemeber = pu.getPropertyDescriptors(memberDto.getClass());
				// PropertyDescriptor listPropertyDescriptor = null;
				// for (PropertyDescriptor propertyDescriptor : dsMemeber) {
				// if (propertyDescriptor.getPropertyType().equals(List.class)) {
				// listPropertyDescriptor = propertyDescriptor;
				// }
				// }
				//
				// boolean goFurther = listPropertyDescriptor.getName().equals("informationSource")
				// && objectDto instanceof InformationSource;
				//
				// if (listPropertyDescriptor != null && goFurther) {
				// List<ObjectId<Long>> listDto = (List<ObjectId<Long>>) pu.getIndexedProperty(memberDto,
				// listPropertyDescriptor.getName());
				// List<ObjectId<Long>> listEm = (List<ObjectId<Long>>) pu.getIndexedProperty(memberEm,
				// listPropertyDescriptor.getName());
				// uMany1.update(em, objectEm, memberEm, memberDto, objectDto, listEm, listDto, d.getName());
				// } else {
				// System.out.println("no many to 1 relation found");
				// }
				// }
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new VmeException(e);
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
