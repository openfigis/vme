/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.updaters.impl;

import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.sync.factsheets.updaters.FactsheetUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.sources.vme.VmeDao;

/**
 * Place your class / interface description here.
 * 
 * History:
 * 
 * ------------- --------------- ----------------------- Date Author Comment
 * ------------- --------------- ----------------------- 24 Feb 2014 Fiorellato
 * Creation.
 * 
 * @version 1.0
 * @since 24 Feb 2014
 */
public abstract class AbstractFactsheetUpdater implements FactsheetUpdater {
	static final protected Logger LOG = LoggerFactory.getLogger(FigisFactsheetUpdater.class);
	// static final protected String CACHE_RESET_ENDPOINT =
	// "http://figis02:8282/fiweb/servlet/CacheDeleteFactsheetDomain?domain=vme";

	@Inject
	protected VmeDao vmeDao;

	@Inject
	protected FigisCacheResetEndpoint figisCacheResetEndpoint;

	@Override
	public void refreshVme(Long vmeID) throws Exception {
		this.vmeDao.getEm().clear();
		this.vmeDao.getEntityById(this.vmeDao.getEm(), Vme.class, vmeID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fao.fi.vme.sync.factsheets.updaters.FactsheetUpdater#
	 * refreshGeneralMeasure(java.lang.Long)
	 */
	@Override
	public void refreshGeneralMeasure(Long gmID) throws Exception {
		EntityManager em = this.vmeDao.getEm();

		GeneralMeasure toRefresh = this.vmeDao.getEntityById(em, GeneralMeasure.class, gmID);
		Rfmo parent = toRefresh.getRfmo();

		if (parent != null) {
			em.refresh(parent);
		}

		em.refresh(toRefresh);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fao.fi.vme.sync.factsheets.updaters.FactsheetUpdater#
	 * refreshInformationSource(java.lang.Long)
	 */
	@Override
	public void refreshInformationSource(Long isID) throws Exception {
		EntityManager em = this.vmeDao.getEm();

		InformationSource toRefresh = this.vmeDao.getEntityById(em, InformationSource.class, isID);
		Rfmo parent = toRefresh.getRfmo();

		if (parent != null) {
			em.refresh(parent);
		}

		em.refresh(toRefresh);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fao.fi.vme.sync.factsheets.updaters.FactsheetUpdater#
	 * refreshFishingFootprint(java.lang.Long)
	 */
	@Override
	public void refreshFishingFootprint(Long ffID) throws Exception {
		EntityManager em = this.vmeDao.getEm();

		FisheryAreasHistory toRefresh = this.vmeDao.getEntityById(em, FisheryAreasHistory.class, ffID);
		Rfmo parent = toRefresh.getRfmo();

		if (parent != null) {
			em.refresh(parent);
		}

		em.refresh(toRefresh);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fao.fi.vme.sync.factsheets.updaters.FactsheetUpdater#
	 * refreshRegionalHistory(java.lang.Long)
	 */
	@Override
	public void refreshRegionalHistory(Long rhID) throws Exception {
		EntityManager em = this.vmeDao.getEm();

		VMEsHistory toRefresh = this.vmeDao.getEntityById(em, VMEsHistory.class, rhID);
		Rfmo parent = toRefresh.getRfmo();

		if (parent != null) {
			em.refresh(parent);
		}

		em.refresh(toRefresh);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fao.fi.vme.sync.factsheets.updaters.FactsheetUpdater#refreshRfmo(
	 * java.lang.String)
	 */
	@Override
	public void refreshRfmo(String rfmoID) throws Exception {
		this.vmeDao.getEm().clear();

		this.vmeDao.getEntityById(this.vmeDao.getEm(), Rfmo.class, rfmoID);

		// this.vmeDao.getEm().refresh(this.vmeDao.getEntityById(this.vmeDao.getEm(),
		// Rfmo.class, rfmoID));
	}

	protected void updateCache(Long vmeID) throws Exception {
		if (vmeID != null) {
			InputStream is = null;

			try {
				LOG.info("Sending factsheet cache reset request for VME ID {} to: {}", vmeID,
						figisCacheResetEndpoint.getCacheResetEndpoint());

				is = new URL(figisCacheResetEndpoint.getCacheResetEndpoint()).openStream();

				byte[] buffer = new byte[8192];

				int len = -1;

				StringBuilder result = new StringBuilder();

				while ((len = is.read(buffer)) != -1) {
					result.append(new String(buffer, 0, len, "UTF-8"));
				}

				LOG.info("Factsheet cache reset response for vme ID {} is: {}", vmeID, result.toString());

			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (Throwable t) {
						LOG.warn("Unable to close remote stream", t);
					}
				}
			}
		}
	}
}
