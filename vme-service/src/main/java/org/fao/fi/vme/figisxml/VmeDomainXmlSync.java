package org.fao.fi.vme.figisxml;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.dao.FigisDao;
import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.figisxml.component.Sync;
import org.fao.fi.vme.figisxml.component.VmeRefSync;

/**
 * Syncs the FIGIS Factsheet XML, Figis observation, Figis reference tables with the information from the VME domain
 * model.
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeDomainXmlSync {

	@Inject
	VmeDao vmeDao;
	@Inject
	FigisDao figisDao;

	@Inject
	VmeRefSync vmeRefSync;

	public void syncFigisVmeXml() {
		List<Sync> syncList = composeList();
		for (Sync syncer : syncList) {
			syncer.sync();
		}
	}

	private List<Sync> composeList() {
		List<Sync> syncList = new ArrayList<Sync>();
		syncList.add(vmeRefSync);
		return syncList;
	}
}
