package org.fao.fi.vme.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.domain.test.GeneralMeasureMock;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.RfmoMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Before;
import org.junit.Test;

public class RfmoTest {
	
	private Rfmo r1;
	private Rfmo r2;
	private MultiLingualStringUtil UTIL = new MultiLingualStringUtil();
	
	@Before
	public void before(){
		r1 = RfmoMock.create();
		r2 = RfmoMock.create();
	}

	@Test
	public void testHashCode() {
		assertTrue(r1.hashCode() != r2.hashCode());
	}

	@Test
	public void testGetId() {
		assertEquals("1000", r1.getId());
	}

	@Test
	public void testSetId() {
		r2.setId("2001");
		assertEquals("2001", r2.getId());
	}

	@Test
	public void testGetGeneralMeasureList() {
		assertTrue(2 == r1.getGeneralMeasureList().size());
	}

	@Test
	public void testSetGeneralMeasureList() {
		List<GeneralMeasure> gmList = new ArrayList<GeneralMeasure>();
		gmList.add(GeneralMeasureMock.create());
		r2.setGeneralMeasureList(gmList);
		assertTrue(1 == r2.getGeneralMeasureList().size());
	}

	@Test
	public void testGetListOfManagedVmes() {
		assertTrue(2 == r1.getListOfManagedVmes().size());
	}

	@Test
	public void testSetListOfManagedVmes() {
		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(VmeMock.generateVme(3));
		r2.setListOfManagedVmes(vmeList);
		assertTrue(1 == r2.getListOfManagedVmes().size());
	}

	@Test
	public void testGetInformationSourceList() {
		assertTrue(1 == r1.getInformationSourceList().size());
	}

	@Test
	public void testSetInformationSourceList() {
		List<InformationSource> infoSourceList = new ArrayList<InformationSource>();
		infoSourceList.add(InformationSourceMock.create());
		infoSourceList.add(InformationSourceMock.create());
		r2.setInformationSourceList(infoSourceList);
		assertTrue(2 == r2.getInformationSourceList().size());
	}

	@Test
	public void testGetHasFisheryAreasHistory() {
		assertTrue(1 == r1.getHasFisheryAreasHistory().size());
	}

	@Test
	public void testSetHasFisheryAreasHistory() {
		List<FisheryAreasHistory> hasFisheryAreasHistory = new ArrayList<FisheryAreasHistory>();
		FisheryAreasHistory fahistory = new FisheryAreasHistory();
		fahistory.setHistory(UTIL.english("Fishery Area history value"));
		fahistory.setYear(1999);
		hasFisheryAreasHistory.add(fahistory);
		FisheryAreasHistory fahistory2 = new FisheryAreasHistory();
		fahistory2.setHistory(UTIL.english("Fishery Area history value"));
		fahistory2.setYear(1999);
		hasFisheryAreasHistory.add(fahistory2);
		r2.setHasFisheryAreasHistory(hasFisheryAreasHistory);
		assertTrue(2 == r2.getHasFisheryAreasHistory().size());
	}

	@Test
	public void testGetHasVmesHistory() {
		assertTrue(1 == r1.getHasVmesHistory().size());
	}

	@Test
	public void testSetHasVmesHistory() {
		List<VMEsHistory> hasVmesHistory = new ArrayList<VMEsHistory>();
		VMEsHistory vhistory = new VMEsHistory();
		vhistory.setHistory(UTIL.english("VMEs history value"));
		vhistory.setYear(1999);
		hasVmesHistory.add(vhistory);
		VMEsHistory vhistory2 = new VMEsHistory();
		vhistory2.setHistory(UTIL.english("VMEs history value"));
		vhistory2.setYear(1999);
		hasVmesHistory.add(vhistory2);
		r2.setHasVmesHistory(hasVmesHistory);
		assertTrue(2 == r2.getHasVmesHistory().size());
	}

	@Test
	public void testEqualsObject() {
		assertNotEquals(r1, r2);
	}

}
