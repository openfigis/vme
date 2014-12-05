package org.fao.fi.vme.domain.change5;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.inject.Inject;

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
 * 
 * @author Erik van Ingen
 *
 */
public class CleanMultiLingualString {

	public static Class<?> mlList[] = { FisheryAreasHistory.class, GeneralMeasure.class, InformationSource.class,
			MediaReference.class, Profile.class, SpecificMeasure.class, Vme.class, VMEsHistory.class };

	@Inject
	VmeDao dao;

	public void cleanEmpty() {
		Report r = new Report();

		for (Class<?> clazz : mlList) {
			ReportPerObject rro = new ReportPerObject();
			rro.setClazz(clazz);
			@SuppressWarnings("unchecked")
			List<ObjectId<?>> objectList = (List<ObjectId<?>>) dao.loadObjects(clazz);
			for (ObjectId<?> object : objectList) {
				cleanObject(object, rro);
			}
			r.getReportList().add(rro);
		}
	}

	private void cleanObject(ObjectId<?> object, ReportPerObject rro) {
		PropertyUtilsBean pu = new PropertyUtilsBean();
		PropertyDescriptor[] ds = pu.getPropertyDescriptors(object.getClass());
		for (PropertyDescriptor propertyDescriptor : ds) {
			if (propertyDescriptor.getPropertyType().equals(MultiLingualString.class)) {
				try {
					MultiLingualString mls = (MultiLingualString) pu.getProperty(object, propertyDescriptor.getName());
					if (mls != null && toBeDeleted(mls)) {
						pu.setProperty(object, propertyDescriptor.getName(), null);
						dao.merge(object);
						rro.getIds().add((Long) object.getId());
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
			toBeDeleted = true;
		}
		return toBeDeleted;
	}
}
