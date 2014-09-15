/**
 * (c) 2014 FAO / UN (project: vme-reports-store-gateway-web)
 */
package org.fao.fi.vme.rsg.test;

import org.gcube.application.rsg.client.RsgReadClient;
import org.gcube.application.rsg.service.dto.NameValue;
import org.gcube.application.rsg.service.dto.ReportEntry;
import org.gcube.application.rsg.service.dto.ReportType;
import org.junit.Test;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 14 May 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 14 May 2014
 */
public class TestWS {
	@Test
	public void testList() throws Throwable {
		RsgReadClient client = new RsgReadClient("http://figisapps.fao.org/figis/secure-reports-store-gateway/rest/");
		client.securedWithEncryptedToken("http://figisapps.fao.org/figis/secure-reports-store-gateway/security/token/encrypted/request", 
										 "http://dev.d4science.org/pgp-security/imarine.skr", 
										 "d45c13nc3", 
										 "classpath://pgp/keys/owned/vme.pkr");
		
		for(ReportEntry in : client.listReports(new ReportType("Vme"), (NameValue[])null))
			System.out.println(in.getId() + " : " + in.getIdentifier() + " : " + in.getOwner() + " : " + in.getReportType().getTypeIdentifier());
	}
}
