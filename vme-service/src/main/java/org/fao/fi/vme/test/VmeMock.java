package org.fao.fi.vme.test;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.GeoRef;
import org.fao.fi.vme.domain.History;
import org.fao.fi.vme.domain.InformationSource;
import org.fao.fi.vme.domain.Profile;
import org.fao.fi.vme.domain.Rfmo;
import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.Vme;

public class VmeMock {

	public final static Long ID = 1000l;
	public final static int YEAR = 2013;

	public static Vme create() {

		Vme vme = new Vme();
		vme.setValidityPeriod(ValidityPeriodMock.create());
		vme.setId(ID);

		GeoRef g = new GeoRef();
		g.setYear(YEAR);
		List<GeoRef> l = new ArrayList<GeoRef>();
		l.add(g);
		vme.setGeoRefList(l);
		return vme;

	}

	/**
	 * vme1 plain vme; vme2 is another with 2 observations for 2 yearObjectList; vme3 is
	 * 
	 * 
	 * 
	 * @return list with 3 vmes
	 */
	public static List<Vme> create3() {
		List<Vme> l = new ArrayList<Vme>();
		Vme vme1 = create();
		Vme vme2 = create();

		vme2.setId(ID + 1);
		vme2.getGeoRefList().get(0).setYear(YEAR + 1);

		l.add(vme1);
		l.add(vme2);
		return l;
	}

	public static Vme generateVme(int nrOfyears) {
		int startYear = YEAR;
		int id = 0;

		List<InformationSource> informationSourceList = new ArrayList<InformationSource>();
		List<Profile> pList = new ArrayList<Profile>();
		List<SpecificMeasures> specificMeasureList = new ArrayList<SpecificMeasures>();
		List<GeneralMeasures> generalMeasuresList = new ArrayList<GeneralMeasures>();
		List<History> vmeHistoryList = new ArrayList<History>();
		List<GeoRef> geoRefList = new ArrayList<GeoRef>();
		List<History> fishingHistoryList = new ArrayList<History>();

		for (int i = 0; i < nrOfyears; i++) {
			int year = startYear + i;
			InformationSource is = new InformationSource();
			is.setId(id++);
			informationSourceList.add(is);
			History rfmoHistory = new History();
			rfmoHistory.setId(id++);
			rfmoHistory.setYear(year);
			fishingHistoryList.add(rfmoHistory);

			Profile profile = new Profile();
			profile.setId(id++);
			profile.setYear(year);
			pList.add(profile);

			SpecificMeasures specificMeasures = new SpecificMeasures();
			specificMeasures.setId(id++);
			specificMeasures.setYear(year);
			specificMeasureList.add(specificMeasures);

			History vmeHistory = new History();
			vmeHistory.setId(id++);
			vmeHistory.setYear(year);
			vmeHistoryList.add(vmeHistory);

			GeoRef geoRef = new GeoRef();
			// geoRef.setId(id++);
			geoRef.setYear(year);
			geoRefList.add(geoRef);

			GeneralMeasures gm = new GeneralMeasures();
			gm.setYear(year);
			gm.setId(id++);
			generalMeasuresList.add(gm);

		}

		Rfmo rfmo = new Rfmo();
		rfmo.setId(id++);
		rfmo.setFishingHistoryList(fishingHistoryList);
		rfmo.setInformationSourceList(informationSourceList);
		rfmo.setGeneralMeasuresList(generalMeasuresList);

		Vme vme = new Vme();
		vme.setId(new Long(id++));
		vme.setRfmo(rfmo);
		vme.setProfileList(pList);
		vme.setSpecificMeasureList(specificMeasureList);
		vme.setHistoryList(vmeHistoryList);
		vme.setGeoRefList(geoRefList);
		return vme;
	}
}
