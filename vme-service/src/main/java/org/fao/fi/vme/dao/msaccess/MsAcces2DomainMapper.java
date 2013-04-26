package org.fao.fi.vme.dao.msaccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fao.fi.vme.dao.msaccess.mapping.TableDomainMapper;
import org.fao.fi.vme.dao.msaccess.tables.Measues_VME_Specific;
import org.fao.fi.vme.dao.msaccess.tables.Measures_VME_General;
import org.fao.fi.vme.dao.msaccess.tables.Meetings;
import org.fao.fi.vme.dao.msaccess.tables.RFB_VME_Fishing_History;
import org.fao.fi.vme.dao.msaccess.tables.VME;
import org.fao.fi.vme.domain.FishingHistory;
import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.Meeting;
import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.Vme;

public class MsAcces2DomainMapper {

	static Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();

	static {
		map.put(Measues_VME_Specific.class, SpecificMeasures.class);
		map.put(Measures_VME_General.class, GeneralMeasures.class);
		map.put(Meetings.class, Meeting.class);
		map.put(RFB_VME_Fishing_History.class, FishingHistory.class);
		map.put(VME.class, Vme.class);
	}

	public ObjectCollection map(Table table) {
		ObjectCollection c = new ObjectCollection();
		c.setClazz(table.getClazz());

		List<Object> objects = new ArrayList<Object>();
		List<Object> records = table.getObjectList();
		for (Object object : records) {
			TableDomainMapper m = (TableDomainMapper) object;
			objects.add(m.map());
		}
		c.setObjectList(objects);
		return c;
	}

	public Class<?> getDomainClass(Class<?> tableClass) {
		return map.get(tableClass);
	}
}
