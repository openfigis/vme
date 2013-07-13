package org.fao.fi.vme.test;

import org.fao.fi.vme.dao.VmeDao;
import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.domain.History;
import org.fao.fi.vme.domain.InformationSource;
import org.fao.fi.vme.domain.Vme;

public class VmeDaoTestLogic {

	public void mockAndSaveVme(VmeDao vmeDao, int nrOfyears) {

		Vme vme = VmeMock.generateVme(nrOfyears);

		for (GeneralMeasures o : vme.getRfmo().getGeneralMeasuresList()) {
			vmeDao.persist(o);
		}

		for (History h : vme.getRfmo().getFishingHistoryList()) {
			vmeDao.persist(h);
		}

		for (InformationSource informationSource : vme.getRfmo().getInformationSourceList()) {
			vmeDao.persist(informationSource);
		}

		vmeDao.persist(vme.getRfmo());

		for (History h : vme.getHistoryList()) {
			vmeDao.persist(h);
		}
		for (GeoRef geoRef : vme.getGeoRefList()) {
			vmeDao.persist(geoRef);
		}

		vmeDao.persist(vme);

	}

}
