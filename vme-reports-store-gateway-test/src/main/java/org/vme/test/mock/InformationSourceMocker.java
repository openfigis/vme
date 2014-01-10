/**
 * (c) 2013 FAO / UN (project: reports-store-gateway-support-compiler)
 */
package org.vme.test.mock;

import java.net.URL;
import java.util.Date;

import org.fao.fi.vme.domain.model.InformationSource;

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
public class InformationSourceMocker extends AbstractMocker {
	static public InformationSource getMock1() throws Throwable {
		InformationSource is = new InformationSource();
		
		is.setId(1L);
		is.setMeetingStartDate(new Date());
		is.setCitation(MLSu.english("Lorem ipsum"));
		is.setCommittee(MLSu.english("Sit dolor amet"));
		is.setPublicationYear(1975);
		is.setUrl(new URL("http://no.co.in/pleaseDonate.html"));
		
		return is;
	}
	
	static public InformationSource getMock2() throws Throwable {
		InformationSource is = new InformationSource();
		
		is.setId(2L);
		is.setMeetingStartDate(new Date());
		is.setCitation(MLSu.english("Odi et amo"));
		is.setCommittee(MLSu.english("Hodie Tamo"));
		is.setPublicationYear(1978);
		is.setUrl(new URL("http://just.do.it/nike.html"));
		
		return is;
	}
}
