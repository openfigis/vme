package org.fao.fi.vme.dao.msaccess;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VmeReaderTest {

	VmeReader reader = new VmeReader();

	@Test
	public void testReadObjects() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		List<Table> tables = reader.readObjects();
		for (Table table : tables) {
			System.out.println(table.getClazz().getSimpleName());
			assertTrue(table.getObjectList().size() > 2);
			Set<String> ids = new HashSet<String>();

			// checking unicity
			for (Object object : table.getObjectList()) {

				Method method = object.getClass().getDeclaredMethod("getID");
				Object id = method.invoke(object);
				System.out.println(id);
				assertFalse(ids.contains(id));
			}

		}
	}
}
