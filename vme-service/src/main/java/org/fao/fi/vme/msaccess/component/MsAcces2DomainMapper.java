package org.fao.fi.vme.msaccess.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fao.fi.vme.domain.FishingHistory;
import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.InformationSource;
import org.fao.fi.vme.domain.Rfmo;
import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.msaccess.mapping.TableDomainMapper;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.model.Table;
import org.fao.fi.vme.msaccess.tables.Measues_VME_Specific;
import org.fao.fi.vme.msaccess.tables.Measures_VME_General;
import org.fao.fi.vme.msaccess.tables.Meetings;
import org.fao.fi.vme.msaccess.tables.RFB_MetaData;
import org.fao.fi.vme.msaccess.tables.RFB_VME_Fishing_History;
import org.fao.fi.vme.msaccess.tables.VME;

public class MsAcces2DomainMapper {

	static Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();

	static {
		map.put(Measues_VME_Specific.class, SpecificMeasures.class);
		map.put(Measures_VME_General.class, GeneralMeasures.class);
		map.put(Meetings.class, InformationSource.class);
		map.put(RFB_VME_Fishing_History.class, FishingHistory.class);
		map.put(VME.class, Vme.class);
		map.put(RFB_MetaData.class, Rfmo.class);
	}

	public ObjectCollection map(Table table) {
		ObjectCollection c = new ObjectCollection();
		c.setClazz(map.get(table.getClazz()));

		List<Object> objects = new ArrayList<Object>();
		List<Object> records = table.getObjectList();
		for (Object tableRecord : records) {
			TableDomainMapper m = (TableDomainMapper) tableRecord;

			// map from table to domain
			Object domainObject = m.map();

			// adding the link 'for the record' in order to enable the Linker to do his work.
			c.getDomainTableMap().put(domainObject, tableRecord);

			// and add the result
			objects.add(domainObject);
		}
		c.setObjectList(objects);
		return c;
	}

	public Class<?> getDomainClass(Class<?> tableClass) {
		return map.get(tableClass);
	}
}
