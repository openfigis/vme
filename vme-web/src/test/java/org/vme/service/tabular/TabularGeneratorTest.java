package org.vme.service.tabular;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fao.fi.vme.batch.sync2.mapping.VmeHistory;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.Lang;
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

	@Test
	public void testGenerateSpecificMeasure() {
		Vme vme = VmeMock.generateVme(2);
		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(vme);
		List<List<Object>> tabular = g.generateSpecificMeasure(vmeList);
		for (List<Object> list : tabular) {
			for (Object object : list) {
				System.out.print(object);
				System.out.print('\t');
				assertNotNull(object);
			}
			System.out.println();

		}
	}

	// @Test
	public void testGenerateGeneralMeasure() {
		fail("Not yet implemented");
	}

	@Test
	public void testGenerateFisheryAreaHistory() {
		Rfmo rfmo = new Rfmo();
		List<FisheryAreasHistory> hasFisheryAreasHistory = new ArrayList<FisheryAreasHistory>();
		FisheryAreasHistory fahistory = new FisheryAreasHistory();
		MultiLingualString mshistory = new MultiLingualString();
		Map<Integer, String> stringMap = new HashMap<Integer, String>();
		stringMap.put(Lang.EN, "Fishery Area history value");
		mshistory.setStringMap(stringMap);
		fahistory.setHistory(mshistory);
		fahistory.setYear(1999);
		hasFisheryAreasHistory.add(fahistory);
		rfmo.setHasFisheryAreasHistory(hasFisheryAreasHistory);
		List<List<Object>> tabular = g.generateFisheryHistory(rfmo);
		for (List<Object> list : tabular) {
			for (Object object : list) {
				System.out.print(object);
				System.out.print('\t');
				assertNotNull(object);
			}
			System.out.println();

		}
	}
	
	@Test
	public void testGenerateVmeHistory() {
		Rfmo rfmo = new Rfmo();
		List<VMEsHistory> hasVmesHistory = new ArrayList<VMEsHistory>();
		VMEsHistory vhistory = new VMEsHistory();
		MultiLingualString mshistory = new MultiLingualString();
		Map<Integer, String> stringMap = new HashMap<Integer, String>();
		stringMap.put(Lang.EN, "VMEs history value");
		mshistory.setStringMap(stringMap);
		vhistory.setHistory(mshistory);
		vhistory.setYear(1999);
		hasVmesHistory.add(vhistory);
		rfmo.setHasVmesHistory(hasVmesHistory);
		List<List<Object>> tabular = g.generateVMEHistory(rfmo);
		for (List<Object> list : tabular) {
			for (Object object : list) {
				System.out.print(object);
				System.out.print('\t');
				assertNotNull(object);
			}
			System.out.println();

		}
	}

	// @Test
	public void testGenerateInfoSource() {
		fail("Not yet implemented");
	}

	@Test
	public void testGenerateGeoRef() {
		Vme vme = VmeMock.generateVme(2);
		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(vme);
		List<List<Object>> tabular = g.generateGeoRef(vmeList);
		for (List<Object> list : tabular) {
			for (Object object : list) {
				System.out.print(object);
				System.out.print('\t');
				assertNotNull(object);
			}
			System.out.println();

		}
		
	}

}
