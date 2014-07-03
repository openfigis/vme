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

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetUpdater#createFactsheets(java.lang.Long[])
	 */
	@Override
	public void createFactsheets(Long... vmeIDs) throws Exception {
		LOG.info("Creating factsheets for {} VMEs with ID {}", vmeIDs.length, vmeIDs);
		
		for (Long vmeId : vmeIDs) {
			LOG.info("Syncing FIGIS factsheets for VME with ID {}...", vmeId);
			
			syncBatch.syncFigisWithVme(vmeDao.findVme(vmeId));
			
			this.updateCache(vmeId);
			
			LOG.info("FIGIS factsheets for VME with ID {} has been synced", vmeId);
		}
	}

	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.FactsheetUpdater#updateFactsheets(java.lang.Long[])
	 */
	@Override
	public void updateFactsheets(Long... vmeIDs) throws Exception {
		LOG.info("Updating factsheets for {} VMEs with ID {}", vmeIDs.length, vmeIDs);
		
		for (Long vmeId : vmeIDs) {
			
			LOG.info("Syncing FIGIS factsheets for VME with ID {}...", vmeId);
			
			syncBatch.syncFigisWithVme(vmeDao.findVme(vmeId));
			
			this.updateCache(vmeId);
			
			LOG.info("FIGIS factsheets for VME with ID {} has been synced", vmeId);
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
		
		Observation o;
		
		Query removeObservationXml, removeObservation;
		
		try {
			for (Long vmeId : vmeIDs) {
				vmeObservations = figisDAO.findVmeObservationByVme(vmeId);
	
				for(VmeObservation in : vmeObservations) {
					observationId = in.getId();
		
					LOG.info("Removing VME observation with ID {} for VME with ID {} @ {}", observationId.getObservationId(), observationId.getVmeId(), observationId.getReportingYear());

//					//Rather clumsy, I know... ObservationXmls can't be loaded by ID as it is composite... Fix?
//					for(ObservationXml ox : _figisDAO.loadObjects(ObservationXml.class))
//						if(ox.getObservation().getId().equals(observationId.getObservationId())) {
//							LOG.info("Removing observation XML with ID {}", observationId.getObservationId());
//
//							_figisDAO.remove(ox);
//						}
//					
//					o = _figisDAO.getEntityById(_figisDAO.getEm(), Observation.class, observationId.getObservationId());
//
//					LOG.info("Removing observation with ID {}", observationId.getObservationId());
//					_figisDAO.remove(o);

					removeObservationXml = em.createQuery("delete from " + ObservationXml.class.getName() + " where id  = " + observationId.getObservationId());
					removeObservation = em.createQuery("delete from " + Observation.class.getName() + " where id = " + observationId.getObservationId());
					
					LOG.info("Removing observation XML with ID {}", observationId.getObservationId());
					removeObservationXml.executeUpdate();

					LOG.info("Removing observation with ID {}", observationId.getObservationId());
					removeObservation.executeUpdate();
				
					LOG.info("Removing observation with ID {}", observationId.getObservationId());
					figisDAO.remove(in);
				}
			}
			
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			
			throw e;
		}
	}
}