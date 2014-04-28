package org.fao.fi.vme.msaccess;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.batch.reference.ReferenceDataHardcodedBatch;
import org.fao.fi.vme.msaccess.component.HistoryHolderCorrection;
import org.fao.fi.vme.msaccess.component.IdCorrection;
import org.fao.fi.vme.msaccess.component.Linker;
import org.fao.fi.vme.msaccess.component.MsAcces2DomainMapper;
import org.fao.fi.vme.msaccess.component.ProfileCorrection;
import org.fao.fi.vme.msaccess.component.VmeReader;
import org.fao.fi.vme.msaccess.component.VmeWriter;
import org.fao.fi.vme.msaccess.mapping.RelationVmeGeoRef;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.model.Table;

/**
 * 
 * This class is the main responsible for importing data from access into an
 * oracle DB.
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeAccessDbImport {

	@Inject
	private VmeWriter writer;
	@Inject
	private VmeReader reader;

	@Inject
	private MsAcces2DomainMapper m;

	@Inject
	ReferenceDataHardcodedBatch rData;

	private final Linker linker = new Linker();

	private final IdCorrection idCorrection = new IdCorrection();

	private final HistoryHolderCorrection historyHolderCorrection = new HistoryHolderCorrection();
	private final ProfileCorrection profileCorrection = new ProfileCorrection();

	private RelationVmeGeoRef relationVmeGeoRef = new RelationVmeGeoRef();

	/**
	 * import the data from MS-Access into the VME RDBMS. The VME RDBMS is most
	 * likely to be Oracle but could also be Postgres.
	 */
	public void importMsAccessData() {
		// load first the DB with referenceData in order to provide the
		// referenceDataProviders with data.
		rData.runBefore();

		// read from MSAccess
		List<Table> tables = reader.readObjects();

		// convert the table objects to domain objects
		List<ObjectCollection> objectCollectionList = new ArrayList<ObjectCollection>();
		for (Table table : tables) {
			objectCollectionList.add(m.map(table));
		}

		// correct the relation Vme - GeoRef
		relationVmeGeoRef.correct(objectCollectionList);

		// connect/link the domain objects, also using the original information
		// from the table objects
		linker.link(objectCollectionList, tables);

		historyHolderCorrection.correct(objectCollectionList);

		profileCorrection.correct(objectCollectionList);

		// set all the ids to null, because the DB needs to generate the ids
		idCorrection.correct(objectCollectionList);

		// write the object graph in the DB
		writer.persistNew(objectCollectionList);

		rData.runAfter();

	}
}
