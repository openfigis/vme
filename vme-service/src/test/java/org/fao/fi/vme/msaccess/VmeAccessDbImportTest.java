package org.fao.fi.vme.msaccess;

import javax.inject.Inject;

import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
public class VmeAccessDbImportTest {

	@Inject
	VmeAccessDbImport i;

	/**
	 * This is not yet working because of
	 * 
	 * Caused by: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save
	 * the transient instance before flushing: org.fao.fi.vme.domain.Vme.rfmo -> org.fao.fi.vme.domain.Rfmo
	 * 
	 * TODO: This can be solved immediately, or first the domain model can be updated according the latest insights of
	 * the VME model.
	 * 
	 * 
	 * 
	 */
	@Ignore
	@Test
	public void testImportMsAccessData() {
		i.importMsAccessData();
	}

}
