package org.fao.fi.vme.batch.sync2.mapping.xml;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.fao.fi.figis.devcon.ObjectFactory;
import org.junit.Test;

public class AddWhenContentRuleTest {

	AddWhenContentRule<Object> r = new AddWhenContentRule<Object>();
	ObjectFactory f = new ObjectFactory();
	CdataUtil ut = new CdataUtil();

	@Test
	public void testGenerics() {
		AddWhenContentRuleTest anyObject = new AddWhenContentRuleTest();

		List<String> ssss = new ArrayList<String>();
		List<Serializable> table = new ArrayList<Serializable>();

		assertEquals(0, table.size());
		new AddWhenContentRule<Serializable>().check(anyObject).beforeAdding((Serializable) ssss).to(table);
		assertEquals(1, table.size());

	}

	@Test
	public void testAddDsl() {
		List<Object> listToExtend = new ArrayList<Object>();
		testDelegate(null, null, listToExtend, 0);
		testDelegate("", null, listToExtend, 0);
		testDelegate(" ", null, listToExtend, 0);
		testDelegate("d", null, listToExtend, 0);
		testDelegate("d", "element", listToExtend, 1);

		new AddWhenContentRule<Object>().check("gh").check(null).beforeAdding("").to(listToExtend);
		assertEquals(2, listToExtend.size());

	}

	@Test
	public void testAddDslMultiple() {
		List<Object> listToExtend = new ArrayList<Object>();
		new AddWhenContentRule<Object>().check(null).check(null).beforeAdding("").beforeAdding("").to(listToExtend);
		assertEquals(0, listToExtend.size());
		new AddWhenContentRule<Object>().check("2").check(null).beforeAdding("").beforeAdding("").to(listToExtend);
		assertEquals(2, listToExtend.size());

	}

	private void testDelegate(Object content, String element, List<Object> listToExtend, int expected) {
		new AddWhenContentRule<Object>().check(content).check(content).beforeAdding(element).to(listToExtend);
		assertEquals(expected, listToExtend.size());
	}
}
