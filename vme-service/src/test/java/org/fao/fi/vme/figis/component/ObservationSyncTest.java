package org.fao.fi.vme.figis.component;

import javax.inject.Inject;

import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FigisDataBaseProducer.class })
public class ObservationSyncTest {

	@Inject
	ObservationSync observationSync;

	@Before
	public void checkCdi() {
		assertNotNull(observationSync);

	}

	@Test
	@Ignore
	public void testSync() {
		// observationSync.sync();
	}

}
