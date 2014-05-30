package org.vme.service.tabular;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.fao.fi.vme.batch.sync2.mapping.VmeHistory;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.domain.test.RfmoMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.Lang;
import org.junit.Test;
import org.vme.service.XlsService;
import org.vme.service.tabular.record.VmeContainer;
import org.vme.service.tabular.record.VmeProfileRecord;

public class TabularGeneratorTest {
	
	@Inject
	private XlsService xlsService = new XlsService();

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

	@Test
	public void testGenerateGeneralMeasure() {
		Rfmo rfmo = RfmoMock.create();
		
		List<List<Object>> tabular = g.generateGeneralMeasure(rfmo);
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
	public void testGenerateFisheryAreaHistory() {
		Rfmo rfmo = RfmoMock.create();
		
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
		Rfmo rfmo = RfmoMock.create();
		
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

	@Test
	public void testGenerateInfoSource() {
		Rfmo rfmo = RfmoMock.create();
		
		List<List<Object>> tabular = g.generateInfoSource(rfmo);
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
	
	@Test
	public void testGenerateFactSheet() {
		
	}
	
	@Test
	public void testGenerateVmeProfileNull() {
		List<Vme> vmeList = new ArrayList<Vme>();
		List<List<Object>> tabular = g.generateVmeProfile(vmeList);
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
	public void testGenerateSpecificMeasureNull() {
		List<Vme> vmeList = new ArrayList<Vme>();
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
	
	@Test
	public void testGenerateGeneralMeasureNull() {
		Rfmo rfmo = null;
		
		List<List<Object>> tabular = g.generateGeneralMeasure(rfmo);
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
	public void testGenerateFisheryAreaHistoryNull() {
		Rfmo rfmo = null;
		
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
	public void testGenerateVmeHistoryNull() {
		Rfmo rfmo = null;
		
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
	
	@Test
	public void testGenerateInfoSourceNull() {
		Rfmo rfmo = null;
		
		List<List<Object>> tabular = g.generateInfoSource(rfmo);
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
	public void testGenerateGeoRefNull() {
		List<Vme> vmeList = new ArrayList<Vme>();
		
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
	
	@Test
	public void testGenerateFactSheetNull() {
		
		List<Vme> vmeList = new ArrayList<Vme>();
		List<VmeContainer> cList = xlsService.prepereList(vmeList);
		List<List<Object>> tabular = g.generateFactSheet(cList);
		
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
