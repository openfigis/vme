package org.fao.fi.vme.figis;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.figis.component.ObservationSync;
import org.fao.fi.vme.figis.component.Sync;
import org.fao.fi.vme.figis.component.VmeObservationSync;
import org.fao.fi.vme.figis.component.VmeRefSync;
import org.fao.fi.vme.figis.component.XmlSync;

/**
 * Syncs the FIGIS Factsheet XML, Figis observation, Figis reference tables with the information from the VME domain
 * model.
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeDomainFigisSync {

	@Inject
	VmeRefSync vmeRefSync;

	@Inject
	ObservationSync observationSync;

	@Inject
	VmeObservationSync vmeObservationSync;

	@Inject
	XmlSync xmlSync;

	public void syncFigisWithVme() {
		List<Sync> syncList = composeList();
		for (Sync syncer : syncList) {
			syncer.sync();
		}
	}

	private List<Sync> composeList() {
		List<Sync> syncList = new ArrayList<Sync>();
		syncList.add(vmeRefSync);
		syncList.add(observationSync);
		syncList.add(vmeObservationSync);
		syncList.add(xmlSync);
		return syncList;
	}
}
