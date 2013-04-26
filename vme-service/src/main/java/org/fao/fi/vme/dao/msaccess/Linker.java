package org.fao.fi.vme.dao.msaccess;

import java.util.List;
import java.util.Map;

import org.fao.fi.vme.domain.Vme;

public class Linker {

	/**
	 * Link the diffent domain objects, using the original MS-Access table objects.
	 * 
	 * 
	 * @param objectCollectionList
	 * @param tables
	 */
	public void link(List<ObjectCollection> objectCollectionList, List<Table> tables) {
		for (ObjectCollection o : objectCollectionList) {
			Map<Object, Object> domainTableMap = o.getDomainTableMap();
			List<Object> objectList = o.getObjectList();
			for (Object object : objectList) {
				linkObject(object, objectCollectionList, tables);
			}

		}

	}

	private void linkObject(Object domainObject, List<ObjectCollection> objectCollectionList, List<Table> tables) {
		if (domainObject instanceof Vme) {
			linkVmeObject(domainObject, objectCollectionList, tables);
		}
		// if (domainObject instanceof Rfmo) {
		// linkRfmoObject(domainObject, objectCollectionList, tables);
		// }
		// if (domainObject instanceof Meeting) {
		// linkVmeObject(domainObject, objectCollectionList, tables);
		// }
		// if (domainObject instanceof SpecificMeasures) {
		// linkSpecificMeasuresObject(domainObject, objectCollectionList, tables);
		// }
		// if (domainObject instanceof GeneralMeasures) {
		// linkGeneralMeasuresObject(domainObject, objectCollectionList, tables);
		// }

	}

	private void linkVmeObject(Object vmeObject, List<ObjectCollection> objectCollectionList, List<Table> tables) {
		Vme vme = (Vme) vmeObject;

		// linking the RFMO
		for (Table table : tables) {

		}

	}
}
