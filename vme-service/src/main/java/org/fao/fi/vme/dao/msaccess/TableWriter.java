package org.fao.fi.vme.dao.msaccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;

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

public class TableWriter {

	@Inject
	EntityManager entityManager;

	static Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();

	static {
		map.put(Measues_VME_Specific.class, SpecificMeasures.class);
		map.put(Measures_VME_General.class, GeneralMeasures.class);
		map.put(Meetings.class, Meeting.class);
		map.put(RFB_VME_Fishing_History.class, FishingHistory.class);
		map.put(VME.class, Vme.class);
	}

	public void write(Table table) {
		List<Object> records = table.getRecords();
		for (Object object : records) {
			TableDomainMapper m = (TableDomainMapper) object;
			entityManager.persist(m.map());
		}
	}

	public Class<?> getDomainClass(Class<?> tableClass) {
		return map.get(tableClass);
	}
}
