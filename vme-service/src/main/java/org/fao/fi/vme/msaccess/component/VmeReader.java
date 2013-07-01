package org.fao.fi.vme.msaccess.component;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.msaccess.model.Table;
import org.fao.fi.vme.msaccess.tables.Measues_VME_Specific;
import org.fao.fi.vme.msaccess.tables.Measures_VME_General;
import org.fao.fi.vme.msaccess.tables.Meetings;
import org.fao.fi.vme.msaccess.tables.RFB_MetaData;
import org.fao.fi.vme.msaccess.tables.RFB_VME_Fishing_History;
import org.fao.fi.vme.msaccess.tables.VME;

public class VmeReader {

	private final TableReader tableReader = new TableReader();
	private final MsAccessConnectionProvider connectionProvider = new MsAccessConnectionProvider();

	public VmeReader() {
		tableReader.setConnection(connectionProvider.getConnecton());
	}

	// private final Class<?> tables[] = { Meetings.class, RFB_MetaData.class, VME.class, Measues_VME_Specific.class,
	// Measures_VME_General.class, RFB_VME_Fishing_History.class };
	private final Class<?> tables[] = { Meetings.class, RFB_MetaData.class, VME.class, Measues_VME_Specific.class,
			Measures_VME_General.class, RFB_VME_Fishing_History.class };

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
