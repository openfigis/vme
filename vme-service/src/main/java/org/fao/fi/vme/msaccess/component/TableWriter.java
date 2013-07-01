package org.fao.fi.vme.msaccess.component;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.InformationSource;
import org.fao.fi.vme.msaccess.model.ObjectCollection;

public class TableWriter {

	@Inject
	VmeDao vmeDao;
	IdGen4InformationSource idGen = new IdGen4InformationSource();

	public void write(ObjectCollection objectCollection) {

		for (Object object : objectCollection.getObjectList()) {

			if (objectCollection.getClazz().equals(GeneralMeasures.class)) {
				handleException4GeneralMeasures((GeneralMeasures) object);
			} else {
				vmeDao.persist(object);
			}
		}
	}

	/**
	 * the InformationSource relation in MsAccess is primarily through Rfmo. Since GeneralMeasures also has some without
	 * id, some ids are invented here.
	 * 
	 * @param object
	 */
	private void handleException4GeneralMeasures(GeneralMeasures object) {
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
			vmeDao.merge(object);
		}
	}

}
