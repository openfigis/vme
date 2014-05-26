package org.fao.fi.vme.domain.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.domain.util.Lang;

public class RfmoMock {

	public static Rfmo create(){
		
		Rfmo rfmo = new Rfmo();
		List<GeneralMeasure> gmList = new ArrayList<GeneralMeasure>();
		gmList.add(GeneralMeasureMock.create());
		gmList.add(GeneralMeasureMock.create());
		rfmo.setGeneralMeasureList(gmList);
		
		List<FisheryAreasHistory> hasFisheryAreasHistory = new ArrayList<FisheryAreasHistory>();
		FisheryAreasHistory fahistory = new FisheryAreasHistory();
		MultiLingualString mshistory = new MultiLingualString();
		Map<Integer, String> stringMap = new HashMap<Integer, String>();
		stringMap.put(Lang.EN, "Fishery Area history value");
		mshistory.setStringMap(stringMap);
		fahistory.setHistory(mshistory);
		fahistory.setYear(1999);
		hasFisheryAreasHistory.add(fahistory);
		rfmo.setHasFisheryAreasHistory(hasFisheryAreasHistory);
		
		List<VMEsHistory> hasVmesHistory = new ArrayList<VMEsHistory>();
		VMEsHistory vhistory = new VMEsHistory();
		MultiLingualString mshistory2 = new MultiLingualString();
		Map<Integer, String> stringMap2 = new HashMap<Integer, String>();
		stringMap2.put(Lang.EN, "VMEs history value");
		mshistory2.setStringMap(stringMap2);
		vhistory.setHistory(mshistory2);
		vhistory.setYear(1999);
		hasVmesHistory.add(vhistory);
		rfmo.setHasVmesHistory(hasVmesHistory);
		
		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(VmeMock.generateVme(3));
		vmeList.add(VmeMock.generateVme(2));
		rfmo.setListOfManagedVmes(vmeList);
		
		List<InformationSource> InfoSourceList = new ArrayList<InformationSource>();
		InfoSourceList.add(InformationSourceMock.create());
		rfmo.setInformationSourceList(InfoSourceList);
		
		return rfmo;
		
	}
	
}
