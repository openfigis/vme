package org.fao.fi.vme.msaccess;

import javax.inject.Inject;

import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
public class VmeAccessDbImportTest {

	@Inject
	VmeAccessDbImport i;

	/**

	 * 
	 * 
	 * 
	 */

	@Test
	public void testImportMsAccessData() {
		i.importMsAccessData();
	}

}
