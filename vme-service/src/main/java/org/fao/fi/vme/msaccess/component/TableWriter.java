package org.fao.fi.vme.msaccess.component;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.tableextension.HistoryHolder;
import org.vme.service.dao.sources.vme.VmeDao;

/**
 * Writes a series of objects of the same class to the vme DB.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class TableWriter {

	@Inject
	private VmeDao vmeDao;
	private final IdGen4InformationSource idGen = new IdGen4InformationSource();

	public void write(ObjectCollection objectCollection) {

		for (Object object : objectCollection.getObjectList()) {
			boolean regular = true;

			if (objectCollection.getClazz().equals(GeneralMeasure.class)) {
				handleException4GeneralMeasures((GeneralMeasure) object);
				regular = false;
			}
			if (objectCollection.getClazz().equals(HistoryHolder.class)) {
				handleException4HistoryHolder((HistoryHolder) object);
				regular = false;
			}
			if (regular) {
				vmeDao.persist(object);
			}
		}
	}

	private void handleException4HistoryHolder(HistoryHolder h) {
		vmeDao.persist(h.getFisheryAreasHistory());
		vmeDao.persist(h.getVmesHistory());
	}

	/**
	 * the InformationSource relation in MsAccess is primarily through Rfmo. Since GeneralMeasures also has some without
	 * id, some ids are invented here.
	 * 
	 * @param object
	 */
	private void handleException4GeneralMeasures(GeneralMeasure object) {
		if (object.getInformationSourceList() != null) {
			List<InformationSource> list = object.getInformationSourceList();
			for (InformationSource informationSource : list) {
				informationSource.setId(idGen.nextId());
				vmeDao.persist(informationSource);
			}
		}
		vmeDao.persist(object);
	}

	public void merge(ObjectCollection objectCollection) {
		for (Object object : objectCollection.getObjectList()) {
			if (object instanceof HistoryHolder) {
				HistoryHolder h = (HistoryHolder) object;
				vmeDao.merge(h.getFisheryAreasHistory());
				vmeDao.merge(h.getVmesHistory());
			} else {
				vmeDao.merge(object);
			}
		}
	}
}
