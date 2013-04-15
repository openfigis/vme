package org.fao.fi.vme.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.fao.fi.vme.domain.Vme;
import org.junit.BeforeClass;
import org.junit.Test;

public class VmeDaoTest extends VmeDao {

	public static EntityManagerFactory factory;

	@BeforeClass
	public static void setup() {
		// redirect logging to avoid file pollution
		System.setProperty("derby.stream.error.field", "java.lang.System.out");
		System.setProperty("org.slf4j.simpleLogger.log.openjpa.jdbc.SQL", "trace");
		factory = Persistence.createEntityManagerFactory("test");

	}

	@Test
	public void testPersistVme() {
		Vme vme = new Vme();
		String status = "go";
		vme.setStatus(status);

		VmeDao dao = new VmeDao();
		dao.persistVme(vme);

	}

}
