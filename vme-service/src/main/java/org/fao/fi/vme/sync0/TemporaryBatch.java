package org.fao.fi.vme.sync0;

import javax.inject.Inject;

import org.fao.fi.vme.msaccess.VmeAccessDbImport;
import org.fao.fi.vme.sync2.SyncBatch2;

/**
 * This batch is useful until decided that the MsAccess DB is not necessary
 * anymore.
 * 
 * This batch imports the data from MsAccess into the Oracle Vme Database
 * (VmeAccessDbImport), then pushes this data into the Figis database.
 * 
 * 
 * *
 * 
 * @author Erik van Ingen
 * 
 */
public class TemporaryBatch {

	@Inject
	VmeAccessDbImport vmeAccessDbImport;

	@Inject
	SyncBatch2 syncBatch2;

	/**
	 * Run the batch
	 */
	void run() {
		vmeAccessDbImport.importMsAccessData();

		syncBatch2.syncFigisWithVme();
	}
}
