/**
 * (c) 2013 FAO / UN (project: vme-reports-store-gateway)
 */
package org.fao.fi.vme.rsg.test;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.gcube.application.rsg.support.compiler.utils.ScanningUtils;
import org.junit.BeforeClass;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 25 Nov 2013   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 25 Nov 2013
 */
abstract public class AbstractTest {
	@BeforeClass
	static public void initializeTest() {
		ScanningUtils.registerPrimitiveType(MultiLingualString.class);
	}
}
