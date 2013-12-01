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

import javax.inject.Inject;

import org.fao.fi.vme.msaccess.model.Table;
import org.fao.fi.vme.msaccess.tables.Measues_VME_Specific;
import org.fao.fi.vme.msaccess.tables.RFB_MetaData;
import org.fao.fi.vme.msaccess.tables.VME;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FilesystemMsAccessConnectionProvider.class })
public class VmeReaderTest {

	@Inject private VmeReader reader;

	public static Map<Class<?>, Integer> numberOfRecords = new HashMap<Class<?>, Integer>();
	static {
		numberOfRecords.put(VME.class, 209);
		numberOfRecords.put(RFB_MetaData.class, 6);
		numberOfRecords.put(Measues_VME_Specific.class, 181);
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

			if (table.getClazz().equals(Measues_VME_Specific.class)) {
				List<Object> records = table.getObjectList();
				int countMeetingId = 0;
				for (Object object : records) {
					Measues_VME_Specific r = (Measues_VME_Specific) object;
					if (r.getMeetings_ID() > 0) {
						countMeetingId++;
					}
				}
				assertEquals(132, countMeetingId, 10);
			}
		}
	}
}
