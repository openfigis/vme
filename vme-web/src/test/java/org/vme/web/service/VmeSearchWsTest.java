package org.vme.web.service;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives(DummySearchService.class)
public class VmeSearchWsTest {

	@Inject
	VmeSearchWs vmeSearchWs;

	@Test
	public void testFind() throws Exception {
		vmeSearchWs.find("vme", "2010)", null, null, null);
		vmeSearchWs.find("vme", "2010", null, null, null);
		vmeSearchWs.find(null, null, "30", null, null);
		vmeSearchWs.find(null, null, null, "30", null);
		vmeSearchWs.find(null, null, null, null, "30");

	}

}
