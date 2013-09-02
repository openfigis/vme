package org.fao.fi.vme.sync2.mapping.xml;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AddWhenContentRuleTest {

	AddWhenContentRule r = new AddWhenContentRule();

	@Test
	public void testAddDsl() {
		List<Object> listToExtend = new ArrayList<Object>();
		testDelegate(null, null, listToExtend, 0);
		testDelegate("", null, listToExtend, 0);
		testDelegate(" ", null, listToExtend, 0);
		testDelegate("d", null, listToExtend, 0);
		testDelegate("d", "element", listToExtend, 1);

		new AddWhenContentRule().check("gh").check(null).beforeAdding("").to(listToExtend);
		assertEquals(2, listToExtend.size());

	}

	@Test
	public void testAddDslMultiple() {
		List<Object> listToExtend = new ArrayList<Object>();
		new AddWhenContentRule().check(null).check(null).beforeAdding("").beforeAdding("").to(listToExtend);
		assertEquals(0, listToExtend.size());
		new AddWhenContentRule().check("2").check(null).beforeAdding("").beforeAdding("").to(listToExtend);
		assertEquals(2, listToExtend.size());

	}

	private void testDelegate(Object content, String element, List<Object> listToExtend, int expected) {
		new AddWhenContentRule().check(content).check(content).beforeAdding(element).to(listToExtend);
		assertEquals(expected, listToExtend.size());
	}
}
