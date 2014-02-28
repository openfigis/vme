package org.fao.fi.vme.msaccess.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.msaccess.mapping.ReferenceDependentTableDomainMapper;
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
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;

public class MsAcces2DomainMapper {
	@Inject @ConceptProvider private ReferenceDaoImpl _conceptProvider;
	
	static Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();

	static {
		map.put(Measues_VME_Specific.class, SpecificMeasure.class);
		map.put(Measures_VME_General.class, GeneralMeasure.class);
		map.put(Meetings.class, InformationSource.class);
		map.put(RFB_VME_Fishing_History.class, HistoryHolder.class);
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
			Object domainObject = m instanceof ReferenceDependentTableDomainMapper ? ((ReferenceDependentTableDomainMapper)m).map(this._conceptProvider) : m.map();

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
