package org.fao.fi.vme.dao;

import javax.inject.Inject;

import org.fao.fi.vme.domain.Vme;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(CdiRunner.class)
public class VmeDaoTest {

	@Inject
	VmeDao dao;

	// @BeforeClass
	// public static void setup() {
	// // redirect logging to avoid file pollution
	// System.setProperty("derby.stream.error.field", "java.lang.System.out");
	// System.setProperty("org.slf4j.simpleLogger.log.openjpa.jdbc.SQL", "trace");
	// factory = Persistence.createEntityManagerFactory("test");
	//
	// }

	@Test
	public void testPersistVme() {
		assertNotNull(dao);

		Vme vme = new Vme();
		String status = "go";
		vme.setStatus(status);
		// dao.persistVme(vme);

	}

}
