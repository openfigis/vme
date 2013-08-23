package org.fao.fi.vme.msaccess.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fao.fi.vme.msaccess.model.Table;
import org.fao.fi.vme.msaccess.tables.RFB_MetaData;
import org.fao.fi.vme.msaccess.tables.VME;
import org.junit.Test;

public class VmeReaderTest {

	VmeReader reader = new VmeReader();

	public static Map<Class<?>, Integer> numberOfRecords = new HashMap<Class<?>, Integer>();
	static {
		numberOfRecords.put(VME.class, 209);
		numberOfRecords.put(RFB_MetaData.class, 6);
	}

	@Test
	public void testReadObjects() throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		List<Table> tables = reader.readObjects();
		for (Table table : tables) {
			// System.out.println(table.getClazz().getSimpleName());
			assertTrue(table.getObjectList().size() > 2);
			Set<String> ids = new HashSet<String>();

			Class<?> tableClazz = table.getClazz();
			if (numberOfRecords.containsKey(table.getClazz())) {
				assertEquals(tableClazz.getSimpleName(), numberOfRecords.get(tableClazz).intValue(), table
						.getObjectList().size());
			}

			// checking unicity
			for (Object object : table.getObjectList()) {

				Method method = object.getClass().getDeclaredMethod("getID");
				Object id = method.invoke(object);
				// System.out.println(id);
				assertFalse(ids.contains(id));
			}

		}
	}
}
