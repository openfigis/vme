package org.vme.service.tabular;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.domain.test.RfmoMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.junit.Test;
import org.vme.service.WritableWorkbookFactory;
import org.vme.service.WritableWorkbookFactoryTest;
import org.vme.service.XlsServiceTest;
import org.vme.service.tabular.record.FactSheetRecord;
import org.vme.service.tabular.record.FisheryAreasHistoryRecord;
import org.vme.service.tabular.record.GeneralMeasureRecord;
import org.vme.service.tabular.record.GeoReferenceRecord;
import org.vme.service.tabular.record.InformationSourceRecord;
import org.vme.service.tabular.record.SpecificMeasureRecord;
import org.vme.service.tabular.record.VmeContainer;
import org.vme.service.tabular.record.VmeProfileRecord;
import org.vme.service.tabular.record.VmesHistoryRecord;

public class TabularGeneratorTest {
	
	@Inject
	XlsServiceTest xlsServiceTest = new XlsServiceTest();
	
	@Inject
	WritableWorkbookFactoryTest workbookFactoryTest = new WritableWorkbookFactoryTest();

	TabularGenerator g = new TabularGenerator();
	private WritableWorkbookFactory f = new WritableWorkbookFactory();
	
	@Test
	public void testGenerateVmeProfile() {
		Vme vme = VmeMock.generateVme(2);
		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(vme);
		RecordGenerator<Vme, Profile, Empty> r = new VmeProfileRecord();
		List<List<Object>> tabular = g.generate(vmeList, r);
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
		RecordGenerator<Vme, SpecificMeasure, Empty> r = new SpecificMeasureRecord();
		List<List<Object>> tabular = g.generate(vmeList, r);
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
		RecordGenerator<Rfmo, GeneralMeasure, InformationSource> r = new GeneralMeasureRecord();
		List<Rfmo> rfmoList = new ArrayList<Rfmo>();
		rfmoList.add(rfmo);
		List<List<Object>> tabular = g.generate(rfmoList, r);
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
		RecordGenerator<Rfmo, FisheryAreasHistory, Empty> r = new FisheryAreasHistoryRecord();
		List<Rfmo> rfmoList = new ArrayList<Rfmo>();
		rfmoList.add(rfmo);
		List<List<Object>> tabular = g.generate(rfmoList, r);
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
		RecordGenerator<Rfmo, VMEsHistory, Empty> r = new VmesHistoryRecord();
		List<Rfmo> rfmoList = new ArrayList<Rfmo>();
		rfmoList.add(rfmo);
		List<List<Object>> tabular = g.generate(rfmoList, r);
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
		RecordGenerator<Rfmo, InformationSource, Empty> r = new InformationSourceRecord();
		List<Rfmo> rfmoList = new ArrayList<Rfmo>();
		rfmoList.add(rfmo);
		List<List<Object>> tabular = g.generate(rfmoList, r);
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
		RecordGenerator<Vme, GeoRef, Empty> r = new GeoReferenceRecord();
		List<List<Object>> tabular = g.generate(vmeList, r);
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
		RecordGenerator<VmeContainer, VmeObservation, Empty> r = new FactSheetRecord();
		Vme vme = VmeMock.generateVme(2);
		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(vme);
		List<VmeContainer> cList = workbookFactoryTest.prepereListMock(vmeList);
		List<List<Object>> tabular = g.generate(cList, r);
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
	public void testGenerateVmeProfileNull() {
		List<Vme> vmeList = new ArrayList<Vme>();
		RecordGenerator<Vme, Profile, Empty> r = new VmeProfileRecord();
		List<List<Object>> tabular = g.generate(vmeList, r);
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
		RecordGenerator<Vme, SpecificMeasure, Empty> r = new SpecificMeasureRecord();
		List<List<Object>> tabular = g.generate(vmeList, r);
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
		RecordGenerator<Rfmo, GeneralMeasure, InformationSource> r = new GeneralMeasureRecord();
		List<Rfmo> rfmoList = new ArrayList<Rfmo>();
		List<List<Object>> tabular = g.generate(rfmoList, r);
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
		RecordGenerator<Rfmo, FisheryAreasHistory, Empty> r = new FisheryAreasHistoryRecord();
		List<Rfmo> rfmoList = new ArrayList<Rfmo>();
		List<List<Object>> tabular = g.generate(rfmoList, r);
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
		RecordGenerator<Rfmo, VMEsHistory, Empty> r = new VmesHistoryRecord();
		List<Rfmo> rfmoList = new ArrayList<Rfmo>();
		List<List<Object>> tabular = g.generate(rfmoList, r);
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
		RecordGenerator<Rfmo, InformationSource, Empty> r = new InformationSourceRecord();
		List<Rfmo> rfmoList = new ArrayList<Rfmo>();
		List<List<Object>> tabular = g.generate(rfmoList, r);
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
		RecordGenerator<Vme, GeoRef, Empty> r = new GeoReferenceRecord();
		List<List<Object>> tabular = g.generate(vmeList, r);
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
		RecordGenerator<VmeContainer, VmeObservation, Empty> r = new FactSheetRecord();
		List<Vme> vmeList = new ArrayList<Vme>();
		List<VmeContainer> cList = f.prepereList(vmeList);
		List<List<Object>> tabular = g.generate(cList, r);
		
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
