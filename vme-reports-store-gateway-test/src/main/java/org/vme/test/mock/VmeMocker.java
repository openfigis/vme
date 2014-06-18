/**
 * (c) 2013 FAO / UN (project: reports-store-gateway-test)
 */
package org.vme.test.mock;

import java.util.ArrayList;
import java.util.Arrays;

import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeCriteriaMock;
import org.fao.fi.vme.domain.test.VmeTypeMock;

/**
 * Place your class / interface description here.
 * 
 * History:
 * 
 * ------------- --------------- ----------------------- Date Author Comment
 * ------------- --------------- ----------------------- 24/nov/2013 Fabio
 * Creation.
 * 
 * @version 1.0
 * @since 24/nov/2013
 */
public class VmeMocker extends AbstractMocker {
	static private Vme addSpecificMeasure(Vme vme, SpecificMeasure toAdd) {
		if (vme.getSpecificMeasureList() == null)
			vme.setSpecificMeasureList(new ArrayList<SpecificMeasure>());

		vme.getSpecificMeasureList().add(toAdd);

		return vme;
	}

	static private Vme addProfile(Vme vme, Profile toAdd) {
		if (vme.getProfileList() == null)
			vme.setProfileList(new ArrayList<Profile>());

		vme.getProfileList().add(toAdd);

		return vme;
	}

	static public Vme getMock1() throws Throwable {
		Vme vme = new Vme();
		vme.setId(1L);

		vme.setRfmo(RfmoMocker.getMock3());

		vme.setName(MLSu.english("Foobazzi mountain"));
		vme.setValidityPeriod(ValidityPeriodMocker.mockNotStarted1());

		addSpecificMeasure(vme, SpecificMeasureMocker.getMock1());
		addSpecificMeasure(vme, SpecificMeasureMocker.getMock2());

		addProfile(vme, ProfileMocker.getMock1());
		addProfile(vme, ProfileMocker.getMock2());

		vme.setCriteria(
			new ArrayList<Long>(
				Arrays.asList(new Long[] {
					VmeCriteriaMock.create().getId()
				})
			)
		);
		vme.setAreaType(VmeTypeMock.create().getId());

		return vme;
	}

	static public Vme getMock2() throws Throwable {
		Vme vme = new Vme();
		vme.setId(2L);

		vme.setRfmo(RfmoMocker.getMock1());

		vme.setName(MLSu.english("Barfoozzi hill"));
		vme.setValidityPeriod(ValidityPeriodMocker.mockInterval1());

		addSpecificMeasure(vme, SpecificMeasureMocker.getMock3());
		addSpecificMeasure(vme, SpecificMeasureMocker.getMock1());

		addProfile(vme, ProfileMocker.getMock2());
		addProfile(vme, ProfileMocker.getMock1());

		vme.setCriteria(
			new ArrayList<Long>(
				Arrays.asList(new Long[] {
					VmeCriteriaMock.createAnother().getId()
				})
			)
		);
		vme.setAreaType(VmeTypeMock.create().getId());

		return vme;
	}

	static public Vme getMock3() throws Throwable {
		Vme vme = new Vme();
		vme.setId(3L);

		vme.setRfmo(RfmoMocker.getMock2());

		vme.setName(MLSu.english("Bazfoorri plateau"));
		vme.setValidityPeriod(ValidityPeriodMocker.mockInterval2());

		addSpecificMeasure(vme, SpecificMeasureMocker.getMock2());
		addSpecificMeasure(vme, SpecificMeasureMocker.getMock3());
		addSpecificMeasure(vme, SpecificMeasureMocker.getMock1());

		addProfile(vme, ProfileMocker.getMock2());

		vme.setCriteria(
			new ArrayList<Long>(
				Arrays.asList(new Long[] {
					VmeCriteriaMock.createYetAnother().getId()
				})
			)
		);
		vme.setAreaType(VmeTypeMock.create().getId());

		return vme;
	}
}
