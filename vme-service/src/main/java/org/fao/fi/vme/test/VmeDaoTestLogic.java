package org.fao.fi.vme.test;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.vme.service.dao.sources.vme.VmeDao;

public class VmeDaoTestLogic {

	public void mockAndSaveVme(VmeDao vmeDao, int nrOfyears) {
		Vme vme = VmeMock.generateVme(nrOfyears);
		vmeDao.saveVme(vme);
		// saveVme(vme, vmeDao);
	}

	// public void saveVme(Vme vme, VmeDao vmeDao) {
	//
	// for (GeneralMeasure o : vme.getRfmo().getGeneralMeasureList()) {
	// vmeDao.persist(o);
	// }
	//
	// for (History h : vme.getRfmo().getHasFisheryAreasHistory()) {
	// vmeDao.persist(h);
	// }
	//
	// for (InformationSource informationSource : vme.getRfmo().getInformationSourceList()) {
	// vmeDao.persist(informationSource);
	// }
	//
	// vmeDao.persist(vme.getRfmo());
	//
	// for (GeoRef geoRef : vme.getGeoRefList()) {
	// vmeDao.persist(geoRef);
	// }
	//
	// vmeDao.persist(vme);
	//
	// }

}
