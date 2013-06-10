package org.fao.fi.vme.msaccess.component;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.fao.fi.vme.dao.config.VmeDB;
import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.model.Table;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
public class TableWriterTest {

	VmeReader reader = new VmeReader();

	@Inject
	TableWriter tableWriter;

	MsAcces2DomainMapper mapper = new MsAcces2DomainMapper();

	@Inject
	@VmeDB
	EntityManager entityManager;

	@Before
	public void before() {
		assertNotNull(EntityManager.class.getSimpleName(), entityManager);
	}

	@Test
	@Ignore
	public void testWrite() {
		List<Table> tables = reader.readObjects();
		assertTrue(tables.size() > 0);
		for (Table table : tables) {
			System.out.println(table.getClazz().getSimpleName());
			assertTrue(table.getObjectList().size() > 0);
			ObjectCollection objectCollection = mapper.map(table);
			tableWriter.write(objectCollection);
			Class<?> clazz = mapper.getDomainClass(table.getClazz());
			assertNotNull(clazz);
			List<?> objects = generateTypedQuery(clazz).getResultList();
			assertEquals(table.getObjectList().size(), objects.size());
		}
	}

	TypedQuery<?> generateTypedQuery(Class<?> clazz) {
		String queryString = " select c from  " + clazz.getSimpleName() + " c ";
		System.out.println(queryString);
		TypedQuery<?> query = entityManager.createQuery(queryString, clazz);
		return query;

	}
}
