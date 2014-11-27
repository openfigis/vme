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
import org.fao.fi.vme.domain.model.InformationSource;
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
			if (entity instanceof SpecificMeasure) {
				SpecificMeasure sm = (SpecificMeasure) entity;
				if (sm.getInformationSource() != null && sm.getInformationSource().getSpecificMeasureList() != null) {
					sm.getInformationSource().getSpecificMeasureList().remove(sm);
				}
				sm.setInformationSource(null);
				em.merge(sm.getInformationSource());
			}
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

				if (objectDto instanceof SpecificMeasure) {
					processCopyInformationSource(em, (SpecificMeasure) objectDto, (SpecificMeasure) objectDto);
				}
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new VmeException(e);
			}
		}

	}

	private void processCopyInformationSource(EntityManager em, SpecificMeasure dto, SpecificMeasure managed) {

		boolean processed = false;

		// 0 to 0
		if (dto.getInformationSource() == null && managed.getInformationSource() == null) {
			processed = true;
		}

		// 0 to 1
		if (!processed && dto.getInformationSource() != null && managed.getInformationSource() == null) {
			InformationSource isManaged = em.find(InformationSource.class, dto.getInformationSource().getId());
			managed.setInformationSource(isManaged);
			isManaged.getSpecificMeasureList().add(managed);
			em.merge(managed);
			em.merge(isManaged);
			processed = true;
		}

		// 1 to 0
		if (!processed && dto.getInformationSource() == null && managed.getInformationSource() != null) {
			managed.setInformationSource(null);
			managed.getInformationSource().getSpecificMeasureList().remove(managed);
			em.merge(managed);
			em.merge(managed.getInformationSource());
			processed = true;
		}

		// 1 to 1
		if (!processed && dto.getInformationSource().getId().equals(managed.getInformationSource().getId())) {
			processed = true;
		}

		// 1 to another 1
		if (!processed && !dto.getInformationSource().getId().equals(managed.getInformationSource().getId())) {
			InformationSource isManagedNew = em.find(InformationSource.class, dto.getInformationSource().getId());

			// remove the old one
			managed.getInformationSource().getSpecificMeasureList().remove(managed.getInformationSource());
			em.merge(managed.getInformationSource());

			managed.setInformationSource(isManagedNew);
			isManagedNew.getSpecificMeasureList().add(managed);

			processed = true;
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
