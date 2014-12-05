package org.fao.fi.vme.domain.change5;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
import org.vme.dao.sources.vme.VmeDao;

/**
 * 
 * 
 * @author Erik van Ingen
 *
 */
public class CleanFloating {

	public static Class<?> mlList[] = { FisheryAreasHistory.class, GeneralMeasure.class, InformationSource.class,
			MediaReference.class, Profile.class, SpecificMeasure.class, Vme.class, VMEsHistory.class };

	@Inject
	VmeDao dao;

	private static List<Long> collectedIds = new ArrayList<Long>();

	public void cleanFloating() {
		collectIds();
		removeOther();

	}

	private void removeOther() {
		List<MultiLingualString> list = dao.loadObjects(MultiLingualString.class);
		System.out.println("Total size is " + list.size());
		int deleted = 0;
		for (MultiLingualString multiLingualString : list) {

			if (collectedIds.size() == 0) {
				throw new VmeException("Panic");
			}

			if (!collectedIds.contains(multiLingualString.getId())) {
				deleted++;
				dao.remove(multiLingualString);
			}
		}
		System.out.println("Total deleted is " + deleted);

	}

	private void collectIds() {
		for (Class<?> clazz : mlList) {

			ReportPerObject rro = new ReportPerObject();
			rro.setClazz(clazz);
			@SuppressWarnings("unchecked")
			List<ObjectId<?>> objectList = (List<ObjectId<?>>) dao.loadObjects(clazz);
			for (ObjectId<?> object : objectList) {
				rro.setId((Long) object.getId());
				findObjects(object, rro);
			}
		}

	}

	private void findObjects(ObjectId<?> object, ReportPerObject rro) {
		PropertyUtilsBean pu = new PropertyUtilsBean();
		PropertyDescriptor[] ds = pu.getPropertyDescriptors(object.getClass());
		for (PropertyDescriptor propertyDescriptor : ds) {
			if (propertyDescriptor.getPropertyType().equals(MultiLingualString.class)) {
				try {
					MultiLingualString mls = (MultiLingualString) pu.getProperty(object, propertyDescriptor.getName());
					if (mls != null) {
						collectedIds.add(mls.getId());
					}
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					throw new VmeException(e);
				}
			}
		}

	}

}
