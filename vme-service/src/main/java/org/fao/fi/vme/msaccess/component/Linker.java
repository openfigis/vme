package org.fao.fi.vme.msaccess.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.GeneralMeasure;
import org.fao.fi.vme.domain.History;
import org.fao.fi.vme.domain.InformationSource;
import org.fao.fi.vme.domain.Rfmo;
import org.fao.fi.vme.domain.SpecificMeasure;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.model.Table;
import org.fao.fi.vme.msaccess.tables.Measues_VME_Specific;
import org.fao.fi.vme.msaccess.tables.Measures_VME_General;
import org.fao.fi.vme.msaccess.tables.Meetings;
import org.fao.fi.vme.msaccess.tables.RFB_MetaData;
import org.fao.fi.vme.msaccess.tables.RFB_VME_Fishing_History;
import org.fao.fi.vme.msaccess.tables.VME;

public class Linker {

	/**
	 * Link the different domain objects, using the original MS-Access table objects.
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
				linkObject(object, domainTableMap, objectCollectionList, tables);
			}

		}

	}

	private void linkObject(Object domainObject, Map<Object, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList, List<Table> tables) {
		if (domainObject instanceof Vme) {
			linkVmeObject(domainObject, domainTableMap, objectCollectionList);
		}
		// if (domainObject instanceof Rfmo) {
		// linkRfmoObject(domainObject, objectCollectionList, tables);
		// }
		if (domainObject instanceof InformationSource) {
			linkInformationSourceObject(domainObject, domainTableMap, objectCollectionList);
		}
		if (domainObject instanceof SpecificMeasure) {
			linkSpecificMeasuresObject(domainObject, domainTableMap, objectCollectionList);
		}
		if (domainObject instanceof GeneralMeasure) {
			linkGeneralMeasuresObject(domainObject, domainTableMap, objectCollectionList);
		}

		if (domainObject instanceof History) {
			linkFishingHistoryObject(domainObject, domainTableMap, objectCollectionList);
		}

	}

	private void linkFishingHistoryObject(Object domainObject, Map<Object, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		History o = (History) domainObject;
		RFB_VME_Fishing_History record = (RFB_VME_Fishing_History) domainTableMap.get(o);
		Rfmo rfmo = findRfmo(record.getRFB_ID(), objectCollectionList, domainTableMap);
		if (rfmo.getHasFisheryAreasHistory() == null) {
			rfmo.setHasFisheryAreasHistory(new ArrayList<History>());
		}
		if (!rfmo.getHasFisheryAreasHistory().contains(o)) {
			rfmo.getHasFisheryAreasHistory().add(o);
		}

	}

	/**
	 * 
	 * linking GeneralMeasures with Rfmo and InformationSource
	 * 
	 * @param domainObject
	 * @param domainTableMap
	 * @param objectCollectionList
	 */
	private void linkGeneralMeasuresObject(Object domainObject, Map<Object, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		GeneralMeasure gm = (GeneralMeasure) domainObject;
		Measures_VME_General record = (Measures_VME_General) domainTableMap.get(gm);

		Rfmo rfmo = findRfmo(record.getRFB_ID(), objectCollectionList, domainTableMap);
		gm.setRfmo(rfmo);

		if (rfmo.getGeneralMeasureList() == null) {
			rfmo.setGeneralMeasureList(new ArrayList<GeneralMeasure>());
		}
		if (!rfmo.getGeneralMeasureList().contains(gm)) {
			rfmo.getGeneralMeasureList().add(gm);
		}

	}

	/**
	 * linking SpecificMeasures with vme and the other way around
	 * 
	 * 
	 * @param domainObject
	 * @param domainTableMap
	 * @param objectCollectionList
	 */
	private void linkSpecificMeasuresObject(Object domainObject, Map<Object, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		SpecificMeasure sm = (SpecificMeasure) domainObject;
		if (sm.getVmeList() == null) {
			sm.setVmeList(new ArrayList<Vme>());
		}

		Measues_VME_Specific record = (Measues_VME_Specific) domainTableMap.get(sm);

		if (record == null) {
			throw new VmeException(
					"At this point, the Measues_VME_Specific record or its id would need to be there, for Measues_VME_Specific"
							+ sm.getId());
		}

		for (ObjectCollection oc : objectCollectionList) {
			if (record.getMeetings_ID() > 0) {
				if (oc.getClazz() == InformationSource.class) {
					List<Object> objectList = oc.getObjectList();
					for (Object object : objectList) {
						InformationSource is = (InformationSource) object;
						if (is.getId().intValue() == record.getMeetings_ID()) {
							sm.setInformationSource(is);
						}
					}
				}
			}

			if (oc.getClazz() == Vme.class) {
				List<Object> objectList = oc.getObjectList();
				for (Object object : objectList) {
					Vme vme = (Vme) object;
					if (vme.getSpecificMeasureList() == null) {
						vme.setSpecificMeasureList(new ArrayList<SpecificMeasure>());
					}
					VME vmeRecord = (VME) domainTableMap.get(vme);

					if (vmeRecord == null || vmeRecord.getVME_ID() == null) {
						throw new VmeException("At this point, the record or its id would need to be there, for Vme"
								+ vme.getId() + " " + vme.getInventoryIdentifier());
					}

					if (record.getVME_ID().equals(vmeRecord.getVME_ID())) {
						if (!sm.getVmeList().contains(vme)) {
							// add only when not already in the list
							sm.getVmeList().add(vme);
						}
						if (!vme.getSpecificMeasureList().contains(sm)) {
							// add only when not already in the list
							vme.getSpecificMeasureList().add(sm);
						}
					}
				}
			}
		}
	}

	/**
	 * filling up rfmo's with informationSource
	 * 
	 * 
	 * @param informationSourceDomainObject
	 * @param domainTableMap
	 * @param objectCollectionList
	 */
	private void linkInformationSourceObject(Object informationSourceDomainObject, Map<Object, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		InformationSource informationSource = (InformationSource) informationSourceDomainObject;
		Meetings record = (Meetings) domainTableMap.get(informationSource);

		Rfmo rfmo = findRfmo(record.getRFB_ID(), objectCollectionList, domainTableMap);

		if (informationSource.getRfmoList() == null) {
			informationSource.setRfmoList(new ArrayList<Rfmo>());
		}

		if (!informationSource.getRfmoList().contains(rfmo)) {
			informationSource.getRfmoList().add(rfmo);
		}

		if (!rfmo.getInformationSourceList().contains(informationSource)) {
			rfmo.getInformationSourceList().add(informationSource);
		}
	}

	/**
	 * Fills up a VmeDB with a Rfmo and the other way around.
	 * 
	 * 
	 * @param vmeObject
	 * @param domainTableMap
	 * @param objectCollectionList
	 */
	private void linkVmeObject(Object vmeObject, Map<Object, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		Vme vme = (Vme) vmeObject;
		VME vmeRecord = (VME) domainTableMap.get(vme);

		Rfmo rfmo = findRfmo(vmeRecord.getRFB_ID(), objectCollectionList, domainTableMap);
		vme.setRfmo(rfmo);
		if (!rfmo.getListOfManagedVmes().contains(vme)) {
			rfmo.getListOfManagedVmes().add(vme);
		}

	}

	Rfmo findRfmo(String rfmId, List<ObjectCollection> objectCollectionList, Map<Object, Object> domainTableMap) {
		Rfmo rfmo = null;
		for (ObjectCollection oc : objectCollectionList) {
			if (oc.getClazz() == Rfmo.class) {
				List<Object> objectList = oc.getObjectList();
				for (Object object : objectList) {
					Rfmo rfmoDomainObject = (Rfmo) object;
					RFB_MetaData rfmoRecord = (RFB_MetaData) domainTableMap.get(rfmoDomainObject);
					if (rfmoRecord == null) {
						throw new VmeException("rfmoRecord " + rfmoDomainObject.getId()
								+ " at this point should not be null");
					}
					if (rfmId.equals(rfmoRecord.getRFB_ID())) {
						rfmo = rfmoDomainObject;
					}
				}
			}
		}
		if (rfmo == null) {
			throw new VmeException("rfmo " + rfmId + " could not be found");
		}
		return rfmo;
	}
}
