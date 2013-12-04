package org.fao.fi.vme.msaccess.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.model.Table;
import org.fao.fi.vme.msaccess.tableextension.HistoryHolder;
import org.fao.fi.vme.msaccess.tables.Measues_VME_Specific;
import org.fao.fi.vme.msaccess.tables.Measures_VME_General;
import org.fao.fi.vme.msaccess.tables.Meetings;
import org.fao.fi.vme.msaccess.tables.RFB_MetaData;
import org.fao.fi.vme.msaccess.tables.RFB_VME_Fishing_History;
import org.fao.fi.vme.msaccess.tables.VME;

public class Linker {

	/**
	 * Link the different domain objects, using the original MS-Access table
	 * objects.
	 * 
	 * 
	 * @param objectCollectionList
	 * @param tables
	 */
	public void link(List<ObjectCollection> objectCollectionList, List<Table> tables) {
		for (ObjectCollection o : objectCollectionList) {
			Map<String, Object> domainTableMap = o.getDomainTableMap();
			List<Object> objectList = o.getObjectList();
			for (Object object : objectList) {
				linkObject(object, domainTableMap, objectCollectionList, tables);
			}

		}

	}
	
	private void linkObject(Object domainObject, Map<String, Object> domainTableMap,
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

		if (domainObject instanceof HistoryHolder) {
			linkFishingHistoryObject(domainObject, domainTableMap, objectCollectionList);
		}

	}

	private void linkFishingHistoryObject(Object domainObject, Map<String, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		HistoryHolder h = (HistoryHolder) domainObject;
		RFB_VME_Fishing_History record = (RFB_VME_Fishing_History) domainTableMap.get(MsAcces2DomainMapper.buildKey(h));
		Rfmo rfmo = findRfmo(record.getRFB_ID(), objectCollectionList, domainTableMap);

		if (rfmo.getHasFisheryAreasHistory() == null) {
			rfmo.setHasFisheryAreasHistory(new ArrayList<FisheryAreasHistory>());
		}
		if (rfmo.getHasVmesHistory() == null) {
			rfmo.setHasVmesHistory(new ArrayList<VMEsHistory>());
		}

		if (!MsAcces2DomainMapper.contains(rfmo.getHasFisheryAreasHistory(), h.getFisheryAreasHistory())) {
			rfmo.getHasFisheryAreasHistory().add(h.getFisheryAreasHistory());
		}
		if (!MsAcces2DomainMapper.contains(rfmo.getHasVmesHistory(), h.getVmesHistory())) {
			rfmo.getHasVmesHistory().add(h.getVmesHistory());
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
	private void linkGeneralMeasuresObject(Object domainObject, Map<String, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		GeneralMeasure gm = (GeneralMeasure) domainObject;
		Measures_VME_General record = (Measures_VME_General) domainTableMap.get(MsAcces2DomainMapper.buildKey(gm));

		Rfmo rfmo = findRfmo(record.getRFB_ID(), objectCollectionList, domainTableMap);
		gm.setRfmo(rfmo);

		if (rfmo.getGeneralMeasureList() == null) {
			rfmo.setGeneralMeasureList(new ArrayList<GeneralMeasure>());
		}
		if (!MsAcces2DomainMapper.contains(rfmo.getGeneralMeasureList(), gm)) {
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
	private void linkSpecificMeasuresObject(Object domainObject, Map<String, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		SpecificMeasure sm = (SpecificMeasure) domainObject;
		if (sm.getVmeList() == null) {
			sm.setVmeList(new ArrayList<Vme>());
		}

		Measues_VME_Specific record = (Measues_VME_Specific) domainTableMap.get(MsAcces2DomainMapper.buildKey(sm));

		if (record == null) {
			throw new VmeException(
					"At this point, the Measues_VME_Specific record or its id would need to be there, for Measues_VME_Specific"
							+ sm.getId());
		}

		for (ObjectCollection oc : objectCollectionList) {
			if (record.getSource_ID() > 0) {
				if (oc.getClazz() == InformationSource.class) {
					List<Object> objectList = oc.getObjectList();
					for (Object object : objectList) {
						InformationSource is = (InformationSource) object;
						if (is.getId() == record.getSource_ID()) {
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
					VME vmeRecord = (VME) domainTableMap.get(MsAcces2DomainMapper.buildKey(vme));

					if (vmeRecord == null || vmeRecord.getVME_ID() == null) {
						throw new VmeException("At this point, the record or its id would need to be there, for Vme"
								+ vme.getId() + " " + vme.getInventoryIdentifier());
					}

					if (record.getVME_ID().equals(vmeRecord.getVME_ID())) {
						if (!MsAcces2DomainMapper.contains(sm.getVmeList(), vme)) {
							// add only when not already in the list
							sm.getVmeList().add(vme);
						}
						if (!MsAcces2DomainMapper.contains(vme.getSpecificMeasureList(), sm)) {
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
	private void linkInformationSourceObject(Object informationSourceDomainObject, Map<String, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		InformationSource informationSource = (InformationSource) informationSourceDomainObject;
		Meetings record = (Meetings) domainTableMap.get(MsAcces2DomainMapper.buildKey(informationSource));

		Rfmo rfmo = findRfmo(record.getRFB_ID(), objectCollectionList, domainTableMap);

		if (informationSource.getRfmoList() == null) {
			informationSource.setRfmoList(new ArrayList<Rfmo>());
		}

		if (!MsAcces2DomainMapper.contains(informationSource.getRfmoList(), rfmo)) {
			informationSource.getRfmoList().add(rfmo);
		}

		if (!MsAcces2DomainMapper.contains(rfmo.getInformationSourceList(), informationSource)) {
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
	private void linkVmeObject(Object vmeObject, Map<String, Object> domainTableMap,
			List<ObjectCollection> objectCollectionList) {
		Vme vme = (Vme) vmeObject;
		VME vmeRecord = (VME) domainTableMap.get(MsAcces2DomainMapper.buildKey(vme));

		Rfmo rfmo = findRfmo(vmeRecord.getRFB_ID(), objectCollectionList, domainTableMap);
		vme.setRfmo(rfmo);
		if (!MsAcces2DomainMapper.contains(rfmo.getListOfManagedVmes(), vme)) {
			rfmo.getListOfManagedVmes().add(vme);
		}

	}

	Rfmo findRfmo(String rfmId, List<ObjectCollection> objectCollectionList, Map<String, Object> domainTableMap) {
		Rfmo rfmo = null;
		for (ObjectCollection oc : objectCollectionList) {
			if (oc.getClazz() == Rfmo.class) {
				List<Object> objectList = oc.getObjectList();
				for (Object object : objectList) {
					Rfmo rfmoDomainObject = (Rfmo) object;
					RFB_MetaData rfmoRecord = (RFB_MetaData) domainTableMap.get(MsAcces2DomainMapper.buildKey(rfmoDomainObject));
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
