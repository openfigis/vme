/**
 * (c) 2013 FAO / UN (project: vme-reports-store-gateway-test)
 */
package org.vme.test.mock;

import java.util.ArrayList;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 25/nov/2013   Fabio     Creation.
 *
 * @version 1.0
 * @since 25/nov/2013
 */
public class RfmoMocker extends AbstractMocker {
	static private void addGeneralMeasure(Rfmo rfmo, GeneralMeasure toAdd) {
		if(rfmo.getGeneralMeasureList() == null)
			rfmo.setGeneralMeasureList(new ArrayList<GeneralMeasure>());
		
		rfmo.getGeneralMeasureList().add(toAdd);
	}
	
	static private void addInformationSource(Rfmo rfmo, InformationSource toAdd) {
		if(rfmo.getInformationSourceList() == null)
			rfmo.setInformationSourceList(new ArrayList<InformationSource>());
		
		rfmo.getInformationSourceList().add(toAdd);
	}
	
	static private void addVMEsHistory(Rfmo rfmo, VMEsHistory toAdd) {
		if(rfmo.getHasVmesHistory() == null)
			rfmo.setHasVmesHistory(new ArrayList<VMEsHistory>());
		
		rfmo.getHasVmesHistory().add(toAdd);
	}
	
	static private void addFisheryAreasHistory(Rfmo rfmo, FisheryAreasHistory toAdd) {
		if(rfmo.getHasFisheryAreasHistory() == null)
			rfmo.setHasFisheryAreasHistory(new ArrayList<FisheryAreasHistory>());
		
		rfmo.getHasFisheryAreasHistory().add(toAdd);
	}
	
	static public Rfmo getMock1() throws Throwable {
		Rfmo mock = new Rfmo();
		mock.setId("NEAFC");
		
		addGeneralMeasure(mock, GeneralMeasureMocker.getMock1());
		addGeneralMeasure(mock, GeneralMeasureMocker.getMock2());
		
		addInformationSource(mock, InformationSourceMocker.getMock1());
		addInformationSource(mock, InformationSourceMocker.getMock2());
		
		addVMEsHistory(mock, VMEsHistoryMocker.getMock1());
		addVMEsHistory(mock, VMEsHistoryMocker.getMock3());
		
		addFisheryAreasHistory(mock, FisheryAreasHistoryMocker.getMock2());
		
		return mock;
	}
	
	static public Rfmo getMock2() throws Throwable {
		Rfmo mock = new Rfmo();
		mock.setId("NAFO");
		
		addGeneralMeasure(mock, GeneralMeasureMocker.getMock2());
			
		addInformationSource(mock, InformationSourceMocker.getMock2());
		
		addVMEsHistory(mock, VMEsHistoryMocker.getMock3());
		addVMEsHistory(mock, VMEsHistoryMocker.getMock2());
		
		addFisheryAreasHistory(mock, FisheryAreasHistoryMocker.getMock3());
		addFisheryAreasHistory(mock, FisheryAreasHistoryMocker.getMock1());
		addFisheryAreasHistory(mock, FisheryAreasHistoryMocker.getMock2());
		
		return mock;
	}
	
	static public Rfmo getMock3() throws Throwable {
		Rfmo mock = new Rfmo();
		mock.setId("FOOBAR");
		
		addGeneralMeasure(mock, GeneralMeasureMocker.getMock2());
		addGeneralMeasure(mock, GeneralMeasureMocker.getMock1());
			
		addInformationSource(mock, InformationSourceMocker.getMock1());
		
		addVMEsHistory(mock, VMEsHistoryMocker.getMock2());
		addVMEsHistory(mock, VMEsHistoryMocker.getMock1());
		
		addFisheryAreasHistory(mock, FisheryAreasHistoryMocker.getMock3());
		addFisheryAreasHistory(mock, FisheryAreasHistoryMocker.getMock1());
		
		return mock;
	}
}
