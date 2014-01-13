/**
 * (c) 2013 FAO / UN (project: vme-reports-store-gateway-test)
 */
package org.vme.test.mock;

import java.util.ArrayList;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;

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
public class GeneralMeasureMocker extends AbstractMocker {
	static private void addInformationSource(GeneralMeasure generalMeasure, InformationSource informationSource) {
		if(generalMeasure.getInformationSourceList() == null)
			generalMeasure.setInformationSourceList(new ArrayList<InformationSource>());
		
		generalMeasure.getInformationSourceList().add(informationSource);
	}
	
	static public GeneralMeasure getMock1() throws Throwable {
		GeneralMeasure mock = new GeneralMeasure();
		mock.setId(1L);
		mock.setValidityPeriod(ValidityPeriodMocker.mockInterval1());
		mock.setVmeEncounterProtocols(MLSu.english("Tertium non datur"));
		mock.setVmeIndicatorSpecies(MLSu.english("In girum imus nocte"));
		mock.setVmeThreshold(MLSu.english("Et consumimur igni"));
		
		mock.setYear(2001);
	
		addInformationSource(mock, InformationSourceMocker.getMock1());
		addInformationSource(mock, InformationSourceMocker.getMock2());
		
		return mock;
	}
	
	static public GeneralMeasure getMock2() throws Throwable {
		GeneralMeasure mock = new GeneralMeasure();
		mock.setId(2L);
		mock.setValidityPeriod(ValidityPeriodMocker.mockInterval2());
		mock.setVmeEncounterProtocols(MLSu.english("Nunc est bibendum"));
		mock.setVmeIndicatorSpecies(MLSu.english("Nunc pede libero"));
		mock.setVmeThreshold(MLSu.english("Tella pulsanda est"));
		
		mock.setYear(2011);
	
		addInformationSource(mock, InformationSourceMocker.getMock2());
		
		return mock;
	}
}
