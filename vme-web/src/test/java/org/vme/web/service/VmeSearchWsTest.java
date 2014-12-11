package org.vme.web.service;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.SearchServiceInterface;

@RunWith(CdiRunner.class)
@ActivatedAlternatives(DummySearchService.class)
public class VmeSearchWsTest {

	@Inject
	VmeSearchWs vmeSearchWs;
	SearchServiceInterface dummySearchService;

	@Test
	public void testFind() throws Exception {
		vmeSearchWs.find("vme", "2010)", null, null, null, null);
		vmeSearchWs.find("vme", "2010", null, null, null, null);
		vmeSearchWs.find(null, null, "30", null, null, null);
		vmeSearchWs.find(null, null, null, "30", null, null);
		vmeSearchWs.find(null, null, null, null, "30", null);
		vmeSearchWs.find(null, null, null, null, null, "30");
		vmeSearchWs.find("vme", null, null, null, null, null);
	}

	@Test
	public void testFindYear() throws Exception {
		vmeSearchWs.find(null, null, null, null, "30", null);
		assertEquals(30, DummySearchService.year);

	}
}
