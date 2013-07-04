package org.fao.fi.vme.sync2;

import javax.inject.Inject;

/**
 * 
 * See http://km.fao.org/FIGISwiki/index.php/VME_Components
 * 
 * SyncBatch2 performs a sync between VME and FIGIS. It would push the data from VME to FIGIS and eventually read
 * reference data from FIGIS RTMS into VME.
 * 
 * Pushing the data from VME to FIGIS means loading FIGIS and eventually overwriting with updates. SyncBatch2 does not
 * support updates through deltas.
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class SyncBatch2 {

	@Inject
	PushVme pushVme;
	@Inject
	PushGeoRef pushGeoRef;
	@Inject
	PushFigisXml pushFigisXml;

	public void sync() {
		pushVme.push();
		pushGeoRef.push();
		pushFigisXml.push();
	}

}
