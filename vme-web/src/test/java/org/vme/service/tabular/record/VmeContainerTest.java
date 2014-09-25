package org.vme.service.tabular.record;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Before;
import org.junit.Test;

public class VmeContainerTest {

	MultiLingualStringUtil u = new MultiLingualStringUtil();
	VmeContainer vmeContainer;
	VmeContainer vmeContainer2;
	
	@Before
	public void before(){
		List<VmeObservation> obsList = new ArrayList<VmeObservation>();
		obsList.add(new VmeObservation());
		vmeContainer = new VmeContainer(u.english("foo"), obsList);
	}
	
	@Test
	public void testGetName() {
		assertEquals("foo", u.getEnglish(vmeContainer.getName()));
	}

	@Test
	public void testVmeContainer() {
		vmeContainer2 = new VmeContainer(u.english("foo"), new ArrayList<VmeObservation>());
		assertNotNull(vmeContainer2);
	}

	@Test
	public void testFindVmeObservationByVme() {
		assertTrue(1 == vmeContainer.findVmeObservationByVme().size());
	}

}
