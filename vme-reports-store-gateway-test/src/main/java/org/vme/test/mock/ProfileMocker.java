/**
 * (c) 2013 FAO / UN (project: vme-reports-store-gateway-test)
 */
package org.vme.test.mock;

import org.fao.fi.vme.domain.model.Profile;

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
public class ProfileMocker extends AbstractMocker {
	static public Profile getMock1() {
		Profile profile = new Profile();
		profile.setId(1L);
		profile.setYear(1998);
		
		profile.setDescriptionImpact(MLSu.english("Carpe diem"));
		profile.setDescriptionPhisical(MLSu.english("Retro se recepit"));
		
		return profile;
	}
	
	static public Profile getMock2() {
		Profile profile = new Profile();
		profile.setId(2L);
		profile.setYear(2008);
		
		profile.setDescriptionImpact(MLSu.english("Da mi basia mille deinde centum"));
		profile.setDescriptionPhisical(MLSu.english("Tytire tu patulae, recubans sub tegmine fagi"));
		profile.setDescriptionBiological(MLSu.english("Dulce et decorum est pro Claudia Mori"));

		return profile;
	}
}
