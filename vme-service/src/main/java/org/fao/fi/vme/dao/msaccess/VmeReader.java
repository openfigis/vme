package org.fao.fi.vme.dao.msaccess;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.dao.msaccess.tables.Measues_VME_Specific;
import org.fao.fi.vme.dao.msaccess.tables.Measures_VME_General;
import org.fao.fi.vme.dao.msaccess.tables.Meetings;
import org.fao.fi.vme.dao.msaccess.tables.RFB_MetaData;
import org.fao.fi.vme.dao.msaccess.tables.RFB_VME_Fishing_History;
import org.fao.fi.vme.dao.msaccess.tables.VME;

public class VmeReader {

	private final TableReader tableReader = new TableReader();
	private final ConnectionProvider connectionProvider = new ConnectionProvider();

	public VmeReader() {
		tableReader.setConnection(connectionProvider.getConnecton());
	}

	private final Class<?> tables[] = { VME.class, Measures_VME_General.class, Measues_VME_Specific.class,
			Meetings.class, RFB_VME_Fishing_History.class, RFB_MetaData.class };

	public List<Table> readObjects() {

		List<Table> tableList = new ArrayList<Table>();

		for (Class<?> clazz : tables) {

			Table table = tableReader.read(clazz);

			tableList.add(table);

		}
		return tableList;
	}

	public Class<?>[] getTables() {
		return tables;
	}

}
