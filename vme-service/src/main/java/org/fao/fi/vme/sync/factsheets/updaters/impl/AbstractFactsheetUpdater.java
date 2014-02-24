/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.updaters.impl;

import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
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
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 24 Feb 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 24 Feb 2014
 */
public abstract class AbstractFactsheetUpdater implements FactsheetUpdater {
	static final protected Logger LOG = LoggerFactory.getLogger(FigisFactsheetUpdater.class);
	static final protected String CACHE_RESET_ENDPOINT = "http://figis02:8282/fishery/admin/reset-system-cache";
	@Inject
	protected VmeDao vmeDao;
	
	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.updaters.FactsheetUpdater#refreshVme(java.lang.Long)
	 */
	@Override
	public void refreshVme(Long vmeID) throws Exception {
		this.vmeDao.getEm().refresh(this.vmeDao.getEntityById(this.vmeDao.getEm(), Vme.class, vmeID));
	}
	
	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.updaters.FactsheetUpdater#refreshGeneralMeasure(java.lang.Long)
	 */
	@Override
	public void refreshGeneralMeasure(Long gmID) throws Exception {
		this.vmeDao.getEm().refresh(this.vmeDao.getEntityById(this.vmeDao.getEm(), GeneralMeasure.class, gmID));
	}
	
	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.updaters.FactsheetUpdater#refreshInformationSource(java.lang.Long)
	 */
	@Override
	public void refreshInformationSource(Long isID) throws Exception {
		this.vmeDao.getEm().refresh(this.vmeDao.getEntityById(this.vmeDao.getEm(), InformationSource.class, isID));
	}
	
	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.updaters.FactsheetUpdater#refreshFishingFootprint(java.lang.Long)
	 */
	@Override
	public void refreshFishingFootprint(Long ffID) throws Exception {
		this.vmeDao.getEm().refresh(this.vmeDao.getEntityById(this.vmeDao.getEm(), FisheryAreasHistory.class, ffID));		
	}
	
	/* (non-Javadoc)
	 * @see org.fao.fi.vme.sync.factsheets.updaters.FactsheetUpdater#refreshRegionalHistory(java.lang.Long)
	 */
	@Override
	public void refreshRegionalHistory(Long rhID) throws Exception {
		this.vmeDao.getEm().refresh(this.vmeDao.getEntityById(this.vmeDao.getEm(), VMEsHistory.class, rhID));
	}
	
	protected void updateCache(Long vmeID) throws Exception {
		if(vmeID != null) {
			InputStream is = null;
			
			try {
				LOG.info("Sending factsheet cache reset request for VME ID {} to: {}", vmeID, CACHE_RESET_ENDPOINT);
			
			is = new URL(CACHE_RESET_ENDPOINT).openStream();
			byte[] buffer = new byte[8192];
			
			int len = -1;
			
			StringBuilder result = new StringBuilder();
			
			while((len = is.read(buffer)) != -1) {
				result.append(new String(buffer, 0, len, "UTF-8"));
			}
			
			LOG.info("Factsheet cache reset response for vme ID {} is: {}", vmeID, result.toString());
			
			} finally {
				if(is != null)
					try {
						is.close();
					} catch(Throwable t) {
						LOG.warn("Unable to close remote stream", t);
					}
			}
		}
	}
}
