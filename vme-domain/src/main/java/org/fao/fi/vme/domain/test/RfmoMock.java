package org.fao.fi.vme.domain.test;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class RfmoMock {

	static MultiLingualStringUtil u = new MultiLingualStringUtil();
	final static String ID = String.valueOf(1000);

	public static Rfmo create() {

		Rfmo rfmo = new Rfmo();
		List<GeneralMeasure> gmList = new ArrayList<GeneralMeasure>();
		gmList.add(GeneralMeasureMock.create());
		gmList.add(GeneralMeasureMock.create());
		rfmo.setGeneralMeasureList(gmList);
		rfmo.setId(ID);

		List<FisheryAreasHistory> hasFisheryAreasHistory = new ArrayList<FisheryAreasHistory>();
		FisheryAreasHistory fahistory = new FisheryAreasHistory();
		fahistory.setHistory(u.english("Fishery Area history value"));
		fahistory.setYear(1999);
		hasFisheryAreasHistory.add(fahistory);
		rfmo.setHasFisheryAreasHistory(hasFisheryAreasHistory);

		List<VMEsHistory> hasVmesHistory = new ArrayList<VMEsHistory>();
		VMEsHistory vhistory = new VMEsHistory();
		vhistory.setHistory(u.english("VMEs history value"));
		vhistory.setYear(1999);
		hasVmesHistory.add(vhistory);
		rfmo.setHasVmesHistory(hasVmesHistory);

		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(VmeMock.generateVme(3));
		vmeList.add(VmeMock.generateVme(2));
		rfmo.setListOfManagedVmes(vmeList);

		List<InformationSource> infoSourceList = new ArrayList<InformationSource>();
		infoSourceList.add(InformationSourceMock.create());
		rfmo.setInformationSourceList(infoSourceList);
		
		for (GeneralMeasure gm : gmList) {
			List<InformationSource> infoSourceList1 = new ArrayList<InformationSource>();
			infoSourceList1.add(InformationSourceMock.create());
			gm.setInformationSourceList(infoSourceList1);
		}

		return rfmo;

	}
}
