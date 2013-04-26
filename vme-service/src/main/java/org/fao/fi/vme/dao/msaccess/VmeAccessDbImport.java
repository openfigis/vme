package org.fao.fi.vme.dao.msaccess;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class VmeAccessDbImport {

	@Inject
	private VmeWriter writer;

	private final MsAcces2DomainMapper m = new MsAcces2DomainMapper();
	private final Linker linker = new Linker();

	private final VmeReader reader = new VmeReader();

	/**
	 * import the data from MS-Access into the VME RDBMS. The VME RDBMS is most likely to be Oracle but could also be
	 * Postgres.
	 */
	public void importMsAccessData() {
		// read from MSAccess
		List<Table> tables = reader.readObjects();

		// convert the table objects to domain objects
		List<ObjectCollection> objectCollectionList = new ArrayList<ObjectCollection>();
		for (Table table : tables) {
			objectCollectionList.add(m.map(table));
		}

		// connect/link the domain objects, also using the original information from the table objects
		linker.link(objectCollectionList, tables);

		// update the RFB with ids from FIGIS
		// TODO

		// write the domain objects to the DB
		writer.write(objectCollectionList);
	}
}
