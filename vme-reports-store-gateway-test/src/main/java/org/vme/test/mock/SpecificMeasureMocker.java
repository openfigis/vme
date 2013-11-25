/**
 * (c) 2013 FAO / UN (project: reports-store-gateway-support-compiler)
 */
package org.vme.test.mock;

import org.fao.fi.vme.domain.model.SpecificMeasure;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 24/nov/2013   Fabio     Creation.
 *
 * @version 1.0
 * @since 24/nov/2013
 */
public class SpecificMeasureMocker extends AbstractMocker {
	static public SpecificMeasure getMock1() throws Throwable {
		SpecificMeasure sm = new SpecificMeasure();
		sm.setId(1L);
		sm.setInformationSource(InformationSourceMocker.getMock2());
		sm.setVmeSpecificMeasure(MLSu.english("Usque tandem"));
		sm.setYear(2004);
		sm.setValidityPeriod(ValidityPeriodMocker.build(1956, 1980));
		
		return sm;
	}
	
	static public SpecificMeasure getMock2() throws Throwable {
		SpecificMeasure sm = new SpecificMeasure();
		sm.setId(2L);
		sm.setInformationSource(InformationSourceMocker.getMock1());
		sm.setVmeSpecificMeasure(MLSu.english("Abutere Catilina patientiae nostram"));
		sm.setYear(2006);
		sm.setValidityPeriod(ValidityPeriodMocker.build(2003, 2013));
		
		return sm;
	}
	
	static public SpecificMeasure getMock3() throws Throwable {
		SpecificMeasure sm = new SpecificMeasure();
		sm.setId(3L);
		sm.setInformationSource(InformationSourceMocker.getMock2());
		sm.setVmeSpecificMeasure(MLSu.english("Quicquid latinum dictum sit altior viditur"));
		sm.setYear(2011);
		
		return sm;
	}
}
