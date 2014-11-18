package org.fao.fi.vme.domain.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class MultiLingualStringValidatorTest {

	MultiLingualStringValidator v = new MultiLingualStringValidator();
	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void testValidate() {

		assertFalse(v.validate(u.english(" ")));
		assertFalse(v.validate(u.english("")));
		assertFalse(StringUtils.isBlank(v.getReport()));
		assertTrue(v.validate(u.english("g ")));

	}

}
