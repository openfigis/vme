package org.fao.fi.vme.domain.change5;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.MediaReference;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.ObjectId;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.VMEsHistory;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.Lang;
import org.vme.dao.sources.vme.VmeDao;

/**
 * 
 * When a business object references a mls without content, this logic will set it to null, therefore dereferencing it.
 *
 * Next step is CleanFloating
 * 
 * @author Erik van Ingen
 *
 */
public class CleanMultiLingualString {

	public static Class<?> mlList[] = { FisheryAreasHistory.class, GeneralMeasure.class, InformationSource.class,
			MediaReference.class, Profile.class, SpecificMeasure.class, Vme.class, VMEsHistory.class };

	@Inject
	VmeDao dao;

	Report r = new Report();

	public void cleanEmpty() {

		for (Class<?> clazz : mlList) {

			ReportPerObject rro = new ReportPerObject();
			r.getReportList().add(rro);
			rro.setClazz(clazz);
			@SuppressWarnings("unchecked")
			List<ObjectId<?>> objectList = (List<ObjectId<?>>) dao.loadObjects(clazz);
			for (ObjectId<?> object : objectList) {
				rro.setId((Long) object.getId());
				cleanObject(object, rro);
			}
		}

	}

	public void report() {

		List<ReportPerObject> l = r.getReportList();
		for (ReportPerObject reportPerObject : l) {
			System.out.print(reportPerObject.getClazz().getSimpleName() + reportPerObject.getId()
					+ " Number of deletions " + reportPerObject.getIds().size());
			System.out.print(" Ids deleted are: ");
			List<Long> ids = reportPerObject.getIds();
			for (Long id : ids) {
				System.out.print(id + " ");
			}
			System.out.println();

		}

	};

	private void cleanObject(ObjectId<?> object, ReportPerObject rro) {
		PropertyUtilsBean pu = new PropertyUtilsBean();
		PropertyDescriptor[] ds = pu.getPropertyDescriptors(object.getClass());
		for (PropertyDescriptor propertyDescriptor : ds) {
			if (propertyDescriptor.getPropertyType().equals(MultiLingualString.class)) {
				try {
					MultiLingualString mls = (MultiLingualString) pu.getProperty(object, propertyDescriptor.getName());
					if (mls != null && toBeDeleted(mls)) {
						rro.getIds().add((Long) mls.getId());
						EntityTransaction t1 = dao.getEm().getTransaction();
						t1.begin();
						// object = dao.getEm().merge(object);
						// mls = dao.getEm().merge(mls);
						// mls.getStringMap().remove(Lang.EN);
						// dao.getEm().merge(mls);
						// mls.setStringMap(null);
						// dao.getEm().merge(mls);
						pu.setProperty(object, propertyDescriptor.getName(), null);
						dao.getEm().merge(object);
						t1.commit();
					}
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					throw new VmeException(e);
				}
			}
		}

	}

	private boolean toBeDeleted(MultiLingualString mls) {
		boolean toBeDeleted = false;
		if (mls.getStringMap() == null || mls.getStringMap().get(Lang.EN) == null) {
			System.out.println("hit! " + mls.getId());
			toBeDeleted = true;
		}
		return toBeDeleted;
	}
}
