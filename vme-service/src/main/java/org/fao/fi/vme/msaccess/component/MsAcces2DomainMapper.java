package org.fao.fi.vme.msaccess.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.msaccess.mapping.TableDomainMapper;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.model.Table;
import org.fao.fi.vme.msaccess.tableextension.HistoryHolder;
import org.fao.fi.vme.msaccess.tables.Measues_VME_Specific;
import org.fao.fi.vme.msaccess.tables.Measures_VME_General;
import org.fao.fi.vme.msaccess.tables.Meetings;
import org.fao.fi.vme.msaccess.tables.RFB_MetaData;
import org.fao.fi.vme.msaccess.tables.RFB_VME_Fishing_History;
import org.fao.fi.vme.msaccess.tables.VME;

public class MsAcces2DomainMapper {

	static Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();

	static {
		map.put(Measues_VME_Specific.class, SpecificMeasure.class);
		map.put(Measures_VME_General.class, GeneralMeasure.class);
		map.put(Meetings.class, InformationSource.class);
		map.put(RFB_VME_Fishing_History.class, HistoryHolder.class);
		map.put(VME.class, Vme.class);
		map.put(RFB_MetaData.class, Rfmo.class);
	}

	// static public String buildKey(Object value) {
	// if (value == null)
	// return null;
	//
	// String key = value.getClass().getSimpleName() + "@";
	//
	// if (value instanceof Vme)
	// key += ((Vme) value).getId();
	// else if (value instanceof Rfmo)
	// key += ((Rfmo) value).getId();
	// else if (value instanceof SpecificMeasure)
	// key += ((SpecificMeasure) value).getId();
	// else if (value instanceof InformationSource)
	// key += ((InformationSource) value).getId();
	// else if (value instanceof GeneralMeasure)
	// key += ((GeneralMeasure) value).getId();
	// else if (value instanceof FisheryAreasHistory)
	// key += ((FisheryAreasHistory) value).getId();
	// else if (value instanceof VMEsHistory)
	// key += ((VMEsHistory) value).getId();
	// else if (value instanceof RFB_VME_Fishing_History)
	// key += ((RFB_VME_Fishing_History) value).getID();
	// else if (value instanceof Measures_VME_General)
	// key += ((Measures_VME_General) value).getID();
	// else if (value instanceof Measues_VME_Specific)
	// key += ((Measues_VME_Specific) value).getID();
	// else if (value instanceof RFB_MetaData)
	// key += ((RFB_MetaData) value).getID();
	// else if (value instanceof VME)
	// key += ((VME) value).getID();
	// else if (value instanceof Meetings)
	// key += ((Meetings) value).getID();
	// else
	// key += value.hashCode();
	//
	// System.out.println(key);
	// return key;
	// }

	// static public <E> boolean contains(List<E> list, E value) {
	// if (list.isEmpty())
	// return false;
	//
	// String key = buildKey(value);
	// String eKey;
	// for (E entry : list) {
	// eKey = buildKey(entry);
	//
	// if (areEqual(key, eKey))
	// return true;
	// }
	//
	// return false;
	// }

	static private <E> boolean areEqual(E first, E second) {
		return first == null ? (second == null ? true : false) : first.equals(second);
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

			// adding the link 'for the record' in order to enable the Linker to
			// do his work.
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
