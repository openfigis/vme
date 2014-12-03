package org.fao.fi.vme.msaccess;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.MediaReference;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.VMEsHistory;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.reference.InformationSourceType;
import org.vme.dao.sources.vme.Update1nCardinality;
import org.vme.dao.sources.vme.VmeDao;

public class VmeClean {
	public void clean(VmeDao vmeDao) {

		// delete first the relations
		List<Rfmo> rfmoList = (List<Rfmo>) vmeDao.loadObjects(Rfmo.class);
		for (Rfmo rfmo : rfmoList) {
			rfmo.setHasFisheryAreasHistory(null);
			rfmo.setInformationSourceList(null);
			rfmo.setListOfManagedVmes(null);

			List<GeneralMeasure> list = rfmo.getGeneralMeasureList();
			for (GeneralMeasure generalMeasures : list) {
				generalMeasures.setRfmo(null);
				vmeDao.merge(generalMeasures);
			}
			rfmo.setGeneralMeasureList(null);
			vmeDao.merge(rfmo);

			if (rfmo.getHasFisheryAreasHistory() != null) {
				List<FisheryAreasHistory> fahList = rfmo.getHasFisheryAreasHistory();
				for (FisheryAreasHistory fisheryAreasHistory : fahList) {
					fisheryAreasHistory.setRfmo(null);
					vmeDao.merge(fisheryAreasHistory);
				}
			}

			if (rfmo.getHasVmesHistory() != null) {
				List<VMEsHistory> vhList = rfmo.getHasVmesHistory();
				for (VMEsHistory vh : vhList) {
					vh.setRfmo(null);
					vmeDao.merge(vh);
				}
			}
			rfmo.setHasFisheryAreasHistory(null);
			rfmo.setHasVmesHistory(null);
			vmeDao.merge(rfmo);

		}

		List<InformationSource> informationSourceList = (List<InformationSource>) vmeDao
				.loadObjects(InformationSource.class);
		for (InformationSource is : informationSourceList) {
			is.setGeneralMeasureList(null);
			is.setSpecificMeasureList(null);
			is.setRfmo(null);
			vmeDao.merge(is);
		}

		List<Vme> vmeList = (List<Vme>) vmeDao.loadObjects(Vme.class);
		for (Vme vme : vmeList) {
			vme.setGeoRefList(null);
			Update1nCardinality u = new Update1nCardinality();
			u.update(vmeDao.getEm(), vme, new ArrayList<GeoRef>(), vme.getGeoRefList());
			u.update(vmeDao.getEm(), vme, new ArrayList<MediaReference>(), vme.getMediaReferenceList());
			u.update(vmeDao.getEm(), vme, new ArrayList<SpecificMeasure>(), vme.getSpecificMeasureList());
			u.update(vmeDao.getEm(), vme, new ArrayList<Profile>(), vme.getProfileList());
			vme.setRfmo(null);
			vmeDao.merge(vme);
		}

		// VMEsHistory.class

		// now delete the actual objects
		Class<?>[] classes = { VMEsHistory.class, FisheryAreasHistory.class, GeneralMeasure.class,
				SpecificMeasure.class, InformationSource.class, GeoRef.class, Profile.class, Vme.class, Rfmo.class,
				InformationSourceType.class };
		for (Class<?> clazz : classes) {
			List<?> list = vmeDao.loadObjects(clazz);
			for (Object object : list) {
				vmeDao.remove(object);
			}
		}
	}
}
