package org.fao.fi.vme.msaccess.component;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.VMEsHistory;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.tableextension.HistoryHolder;

public class HistoryHolderCorrection {

	public void correct(List<ObjectCollection> objectCollectionList) {

		List<Object> objectList1 = new ArrayList<Object>();
		ObjectCollection o1 = new ObjectCollection();
		o1.setObjectList(objectList1);
		o1.setClazz(FisheryAreasHistory.class);

		List<Object> objectList2 = new ArrayList<Object>();
		ObjectCollection o2 = new ObjectCollection();
		o2.setObjectList(objectList2);
		o2.setClazz(VMEsHistory.class);

		ObjectCollection toBeDeletedObjectCollection = null;
		for (ObjectCollection objectCollection : objectCollectionList) {
			if (objectCollection.getClazz().equals(HistoryHolder.class)) {
				for (Object o : objectCollection.getObjectList()) {
					toBeDeletedObjectCollection = objectCollection;
					HistoryHolder hh = (HistoryHolder) o;
					if (hh.getFisheryAreasHistory() != null) {
						o1.getObjectList().add(hh.getFisheryAreasHistory());
					}
					if (hh.getVmesHistory() != null) {
						o2.getObjectList().add(hh.getVmesHistory());
					}
				}
			}
		}
		if (objectCollectionList.size() > 0) {
			objectCollectionList.remove(toBeDeletedObjectCollection);
			if (o1.getObjectList().size() > 0) {
				objectCollectionList.add(o1);
			}
			if (o2.getObjectList().size() > 0) {
				objectCollectionList.add(o2);
			}
		}

	}
}
