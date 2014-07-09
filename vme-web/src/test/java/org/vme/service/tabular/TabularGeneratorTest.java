package org.vme.service.tabular;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.RfmoMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.junit.Before;
import org.junit.Test;
import org.vme.service.tabular.record.GeneralMeasureRecord;
import org.vme.service.tabular.record.VmeProfileRecord;

public class TabularGeneratorTest {
	
	private VmeProfileRecord vmeProfRec;
	private GeneralMeasureRecord genMeasureRec;
	private List<Vme> vmeList;
	private List<Vme> vmeListEmpty;
	private Rfmo rfmo;
	private List<Rfmo> rfmoList;
	private TabularGenerator t = new TabularGenerator();
	
	@Before
	public <F, S, T> void before(){
		vmeProfRec = new VmeProfileRecord();
		genMeasureRec = new GeneralMeasureRecord();
		vmeList = VmeMock.create3();
		vmeListEmpty = new ArrayList<Vme>();
		rfmo = RfmoMock.create();
		rfmoList = new ArrayList<Rfmo>();
		rfmoList.add(rfmo);
	}
	
	@Test
	public void testGenerateOnlyHeaders() {
		List<List<Object>> objectList = t.generate(vmeListEmpty, vmeProfRec);
		assertEquals("Vme Name", objectList.get(0).get(0));
		assertEquals("Scope", objectList.get(0).get(1));
		assertEquals("Inventory Identifier", objectList.get(0).get(2));
		assertEquals("Area Type", objectList.get(0).get(3));
		assertEquals("Geographic Reference", objectList.get(0).get(4));
		assertEquals("Criteria", objectList.get(0).get(5));
		assertEquals("Begin date", objectList.get(0).get(6));
		assertEquals("End date", objectList.get(0).get(7));
		assertEquals("Year", objectList.get(0).get(8));
		assertEquals("Type of sea floor physiography", objectList.get(0).get(9));
		assertEquals("Physical description of the environment", objectList.get(0).get(10));
		assertEquals("General Biology", objectList.get(0).get(11));
		assertEquals("Impacts", objectList.get(0).get(12));
	}
	
	@Test
	public void testGenerate(){
		List<List<Object>> objectList = t.generate(rfmoList, genMeasureRec);
			assertTrue(10 == objectList.get(0).size());
			assertTrue(11 == objectList.get(1).size());
			assertTrue(3 == objectList.size());
		}

}
