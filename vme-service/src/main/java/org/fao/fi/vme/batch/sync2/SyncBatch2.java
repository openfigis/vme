package org.fao.fi.vme.batch.sync2;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Syncs the FIGIS Factsheet XML, Figis observation, Figis reference tables with
 * the information from the VME domain model.
 * 
 * 
 * * See http://km.fao.org/FIGISwiki/index.php/VME_Components
 * 
 * SyncBatch2 performs a sync between VME and FIGIS. It would push the data from
 * VME to FIGIS and eventually read reference data from FIGIS RTMS into VME.
 * 
 * Pushing the data from VME to FIGIS means loading FIGIS and eventually
 * overwriting with updates. SyncBatch2 does not support updates through deltas.
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class SyncBatch2 {

	@Inject
	VmeRefSync vmeRefSync;

	@Inject
	GeoRefSync geoRefSync;

	@Inject
	ObservationSync observationSync;

	public void syncFigisWithVme() {
		List<Sync> syncList = composeList();
		for (Sync syncer : syncList) {
			syncer.sync();
		}
	}

	/**
	 * 
	 * 
	 * @return The order in which the domain objects are pushed into Figis.
	 */
	private List<Sync> composeList() {
		List<Sync> syncList = new ArrayList<Sync>();

		// reference data
		syncList.add(vmeRefSync);
		syncList.add(geoRefSync);

		// data
		syncList.add(observationSync);

		return syncList;
	}
}
