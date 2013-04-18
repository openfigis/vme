package org.fao.fi.vme.dao;

import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(CdiRunner.class)
public class CdiTest {

	@Inject
	Piet piet;

	@Test
	public void test() {
		assertNotNull(piet);
	}

}
