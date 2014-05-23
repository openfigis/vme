package org.vme.service.tabular;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.junit.Test;
import org.vme.service.tabular.record.VmeProfileRecord;

public class TabularGeneratorTest {

	TabularGenerator g = new TabularGenerator();

	@Test
	public void testGenerateVmeProfile() {
		Vme vme = VmeMock.generateVme(2);
		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(vme);
		List<List<Object>> tabular = g.generateVmeProfile(vmeList);
		for (List<Object> list : tabular) {
			for (Object object : list) {
				System.out.print(object);
				System.out.print('\t');
				assertNotNull(object);
			}
			System.out.println();

		}
		int l = new VmeProfileRecord().getHeaders().length;

		assertEquals(l, tabular.get(0).size());
		assertEquals(l, tabular.get(1).size());
		assertEquals(l, tabular.get(2).size());

	}

	// @Test
	public void testGenerateSpecificMeasure() {
		fail("Not yet implemented");
	}

	// @Test
	public void testGenerateGeneralMeasure() {
		fail("Not yet implemented");
	}

	// @Test
	public void testGenerateHistory() {
		fail("Not yet implemented");
	}

	// @Test
	public void testGenerateInfoSource() {
		fail("Not yet implemented");
	}

	// @Test
	public void testGenerateGeoRef() {
		fail("Not yet implemented");
	}

}
