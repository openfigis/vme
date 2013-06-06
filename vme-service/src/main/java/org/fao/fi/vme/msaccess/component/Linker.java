package org.fao.fi.vme.msaccess.component;

import java.util.List;
import java.util.Map;

import org.fao.fi.vme.domain.FishingHistory;
import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.Meeting;
import org.fao.fi.vme.domain.Rfmo;
import org.fao.fi.vme.domain.SpecificMeasures;
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
		if (domainObject instanceof Meeting) {
			linkMeetingObject(domainObject, domainTableMap, objectCollectionList);
		}
		if (domainObject instanceof SpecificMeasures) {
			linkSpecificMeasuresObject(domainObject, domainTableMap, objectCollectionList);
		}
		if (domainObject instanceof GeneralMeasures) {
			linkGeneralMeasuresObject(domainObject, domainTableMap, objectCollectionList);
		}

		if (domainObject instanceof FishingHistory) {
			linkFishingHistoryObject(domainObject, domainTableMap, objectCollectionList);
		}

	}

	private void linkFishingHistoryObject(Object domainObject, Map<Object, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		FishingHistory o = (FishingHistory) domainObject;
		RFB_VME_Fishing_History record = (RFB_VME_Fishing_History) domainTableMap.get(o);
		Rfmo rfmo = findRfmo(record.getRFB_ID(), objectCollectionList, domainTableMap);
		if (!rfmo.getFishingActivityList().contains(o)) {
			rfmo.getFishingActivityList().add(o);
		}

	}

	/**
	 * 
	 * linking GeneralMeasures with Rfmo and the other way around.
	 * 
	 * @param domainObject
	 * @param domainTableMap
	 * @param objectCollectionList
	 */
	private void linkGeneralMeasuresObject(Object domainObject, Map<Object, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		GeneralMeasures gm = (GeneralMeasures) domainObject;
		Measures_VME_General record = (Measures_VME_General) domainTableMap.get(gm);

		Rfmo rfmo = findRfmo(record.getRFB_ID(), objectCollectionList, domainTableMap);
		gm.setRfmo(rfmo);
		if (!rfmo.getGeneralMeasuresList().contains(gm)) {
			rfmo.getGeneralMeasuresList().add(gm);
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
		SpecificMeasures sm = (SpecificMeasures) domainObject;
		Measues_VME_Specific record = (Measues_VME_Specific) domainTableMap.get(sm);
		for (ObjectCollection oc : objectCollectionList) {
			if (oc.getClazz() == Vme.class) {
				List<Object> objectList = oc.getObjectList();
				for (Object object : objectList) {
					Vme vme = (Vme) object;
					VME vmeRecord = (VME) domainTableMap.get(vme);
					if (record.getVME_ID().equals(vmeRecord.getVME_ID())) {
						sm.setVme(vme);
						if (!vme.getSpecificMeasuresList().contains(sm)) {
							// add only when not already in the list
							vme.getSpecificMeasuresList().add(sm);
						}
					}
				}
			}
		}
	}

	/**
	 * filling up rfmo's with meeting
	 * 
	 * 
	 * @param meetingDomainObject
	 * @param domainTableMap
	 * @param objectCollectionList
	 */
	private void linkMeetingObject(Object meetingDomainObject, Map<Object, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		Meeting meeting = (Meeting) meetingDomainObject;
		Meetings meetingsRecord = (Meetings) domainTableMap.get(meeting);

		// TODO replace with Information Source

		Rfmo rfmo = findRfmo(meetingsRecord.getRFB_ID(), objectCollectionList, domainTableMap);
		// if (!rfmo.getMeetingList().contains(meeting)) {
		// rfmo.get().add(meeting);
		// }

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
						throw new VmeDaoException("rfmoRecord " + rfmoDomainObject.getId()
								+ " at this point should not be null");
					}
					if (rfmId.equals(rfmoRecord.getRFB_ID())) {
						rfmo = rfmoDomainObject;
					}
				}
			}
		}
		if (rfmo == null) {
			throw new VmeDaoException("rfmo " + rfmId + " could not be found");
		}
		return rfmo;
	}
}
