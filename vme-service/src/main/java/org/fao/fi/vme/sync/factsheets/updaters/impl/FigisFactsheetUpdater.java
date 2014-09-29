/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.updaters.impl;

import java.util.List;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationPk;
import org.fao.fi.vme.batch.sync2.SyncBatch2;
import org.vme.dao.sources.figis.FigisDao;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 21 Feb 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 21 Feb 2014
 */
@Alternative
public class FigisFactsheetUpdater extends AbstractFactsheetUpdater {
	@Inject private SyncBatch2 syncBatch;
	
	@Inject private FigisDao figisDAO;
	
	public static final String SYNCING = "Syncing FIGIS factsheets for VME with ID {}...";
	public static final String FACTSHEET_SYNCED = "FIGIS factsheets for VME with ID {} has been synced";
	public static final String REMOVE_WITH_ID = "Removing observation with ID {}";
	public static final String DELETE = "delete from ";

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetUpdater#createFactsheets(java.lang.Long[])
	 */
	@Override
	public void createFactsheets(Long... vmeIDs) throws Exception {
		LOG.info("Creating factsheets for {} VMEs with ID {}", vmeIDs.length, vmeIDs);
		
		for (Long vmeId : vmeIDs) {
			LOG.info(SYNCING, vmeId);
			
			syncBatch.syncFigisWithVme(vmeDao.findVme(vmeId));
			
			this.updateCache(vmeId);
			
			LOG.info(FACTSHEET_SYNCED, vmeId);
		}
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetUpdater#updateFactsheets(java.lang.Long[])
	 */
	@Override
	public void updateFactsheets(Long... vmeIDs) throws Exception {
		LOG.info("Updating factsheets for {} VMEs with ID {}", vmeIDs.length, vmeIDs);
		
		for (Long vmeId : vmeIDs) {
			
			LOG.info(SYNCING, vmeId);
			
			syncBatch.syncFigisWithVme(vmeDao.findVme(vmeId));
			
			this.updateCache(vmeId);
			
			LOG.info(FACTSHEET_SYNCED, vmeId);
		}
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetUpdater#deleteFactsheets(java.lang.Long[])
	 */
	@Override
	public void deleteFactsheets(Long... vmeIDs) throws Exception {
		LOG.info("Deleting factsheets for {} VMEs with ID {}", vmeIDs.length, vmeIDs);

		this.doDeleteFactsheets(vmeIDs);
		
		for(Long vmeId : vmeIDs) {
			this.updateCache(vmeId);
		}
	}
	
	private void doDeleteFactsheets(Long... vmeIDs) throws Exception {
		EntityManager em = vmeDao.getEm();
		
		em.clear();
		
		EntityTransaction tx = em.getTransaction();

		List<VmeObservation> vmeObservations;
		
		VmeObservationPk observationId;
		
		tx.begin();
		
		Query removeObservationXml, removeObservation;
		
		try {
			for (Long vmeId : vmeIDs) {
				vmeObservations = figisDAO.findVmeObservationByVme(vmeId);
	
				for(VmeObservation in : vmeObservations) {
					observationId = in.getId();
		
					LOG.info("Removing VME observation with ID {} for VME with ID {} @ {}", observationId.getObservationId(), observationId.getVmeId(), observationId.getReportingYear());

					removeObservationXml = em.createQuery(DELETE + ObservationXml.class.getName() + " where id  = " + observationId.getObservationId());
					removeObservation = em.createQuery(DELETE + Observation.class.getName() + " where id = " + observationId.getObservationId());
					
					LOG.info("Removing observation XML with ID {}", observationId.getObservationId());
					removeObservationXml.executeUpdate();

					LOG.info(REMOVE_WITH_ID, observationId.getObservationId());
					removeObservation.executeUpdate();
				
					LOG.info(REMOVE_WITH_ID, observationId.getObservationId());
					figisDAO.remove(in);
				}
			}
			
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}
}