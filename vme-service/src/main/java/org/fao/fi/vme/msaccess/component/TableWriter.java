package org.fao.fi.vme.msaccess.component;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.reference.InformationSourceType;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.tableextension.HistoryHolder;
import org.vme.dao.sources.vme.VmeDao;

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

	public void persistNew(ObjectCollection objectCollection) {
		EntityTransaction tx = vmeDao.getEm().getTransaction();
		
		tx.begin();
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
				vmeDao.persistNoTx(object);
			}
		}
		tx.commit();
	}

	public void persist(ObjectCollection objectCollection) {

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
		vmeDao.persistNoTx(h.getFisheryAreasHistory());
		vmeDao.persistNoTx(h.getVmesHistory());
	}

	/**
	 * the InformationSource relation in MsAccess is primarily through Rfmo.
	 * Since GeneralMeasures also has some without id, some ids are invented
	 * here.
	 * 
	 * @param object
	 */
	private void handleException4GeneralMeasures(GeneralMeasure object) {
		InformationSourceType defaultIST = InformationSourceMock.createInformationSourceType();
		vmeDao.persistNoTx(defaultIST);
		
		if (object.getInformationSourceList() != null) {
			List<InformationSource> list = object.getInformationSourceList();
			for (InformationSource informationSource : list) {
				informationSource.setId(idGen.nextId());
				vmeDao.persistNoTx(informationSource);
			}
		}
		vmeDao.persistNoTx(object);
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
