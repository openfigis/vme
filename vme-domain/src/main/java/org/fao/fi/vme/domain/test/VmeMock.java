package org.fao.fi.vme.domain.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.History;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class VmeMock {

	public final static Long ID = 1000l;
	public final static Long VME_ID = 2000l;
	public final static int YEAR = 2000;
	public final static String INVENTORY_ID = "VME_RFMO_1";

	private static MultiLingualStringUtil u = new MultiLingualStringUtil();

	public static Vme create() {

		Vme vme = new Vme();
		vme.setValidityPeriod(ValidityPeriodMock.create());
		vme.setId(ID);
		vme.setName(u.english("Hard Corner Bugs "));
		vme.setGeoArea(u.english("Southern Pacific Ocean"));

		GeoRef g = new GeoRef();
		g.setYear(YEAR);
		g.setVme(vme);
		List<GeoRef> l = new ArrayList<GeoRef>();
		l.add(g);
		vme.setGeoRefList(l);
		
		vme.setAreaType("20");
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
		long id = 0;

		Vme vme = new Vme();

		List<InformationSource> informationSourceList = new ArrayList<InformationSource>();
		List<Profile> pList = new ArrayList<Profile>();
		List<SpecificMeasure> specificMeasureList = new ArrayList<SpecificMeasure>();
		List<GeneralMeasure> generalMeasureList = new ArrayList<GeneralMeasure>();
		List<GeoRef> geoRefList = new ArrayList<GeoRef>();
		List<FisheryAreasHistory> fishingHistoryList = new ArrayList<FisheryAreasHistory>();
		List<VMEsHistory> hasVmesHistory = new ArrayList<VMEsHistory>();

		for (int i = 0; i < nrOfyears; i++) {
			int year = startYear + i;
			InformationSource is = InformationSourceMock.create();
			is.setPublicationYear(year);
			is.setId(id++);
			informationSourceList.add(is);

			InformationSource isSm = InformationSourceMock.create();
			isSm.setPublicationYear(year);
			isSm.setId(id++);
			isSm.setCitation(u.english("This is a citation for a SpecificMeasure"));

			FisheryAreasHistory fahistory = new FisheryAreasHistory();
			// history.setId(id++);
			fahistory.setYear(year);
			fahistory.setHistory(u.english("History repeating"));
			
			VMEsHistory vmehistory = new VMEsHistory();
			// history.setId(id++);
			vmehistory.setYear(year);
			vmehistory.setHistory(u.english("History repeating"));
			
			fishingHistoryList.add(fahistory);
			hasVmesHistory.add(vmehistory);

			Profile profile = new Profile();

			profile.setDescriptionBiological(u.english("Hello World DescriptionBiological"));
			profile.setDescriptionImpact(u.english("Hello World DescriptionImpact"));
			profile.setDescriptionPhisical(u.english("Hello World DescriptionPhisical"));

			// profile.setId(id++);
			profile.setYear(year);
			pList.add(profile);

			SpecificMeasure specificMeasure = new SpecificMeasure();

			specificMeasure.setId(id++);
			specificMeasure.setYear(year);
			specificMeasure.setVmeSpecificMeasure(u.english("A specific measure for the year " + year));
			specificMeasure.setValidityPeriod(ValidityPeriodMock.create(year, year + 1));
			specificMeasure.setInformationSource(isSm);
			specificMeasureList.add(specificMeasure);

			GeoRef geoRef = new GeoRef();
			geoRef.setVme(vme);
			// geoRef.setId(id++);
			geoRef.setYear(year);
			geoRef.setGeographicFeatureID(INVENTORY_ID + "_" + YEAR);
			geoRefList.add(geoRef);

			GeneralMeasure gm = new GeneralMeasure();
			gm.setYear(year);
			gm.setId(id++);
			gm.setFishingAreas("a [FishingArea] general measure");
			gm.setExplorataryFishingProtocols(u.english("an [ExploratoryFishingProtocol] general measure"));
			gm.setVmeEncounterProtocols(u.english("a [VmeEncounterProtocols] general measure"));
			gm.setVmeThreshold(u.english("a [VmeThreshold] general measure"));
			gm.setVmeIndicatorSpecies(u.english("a [VmeIndicatorSpecies] general measure"));
			gm.setValidityPeriod(ValidityPeriodMock.create(year, year + 1));
			gm.setInformationSourceList(Arrays.asList(is));
			generalMeasureList.add(gm);

		}

		Rfmo rfmo = new Rfmo();
		String rfmoId = new Long(id++).toString();
		rfmo.setId(rfmoId);
		rfmo.setHasFisheryAreasHistory(fishingHistoryList);
		rfmo.setHasVmesHistory(hasVmesHistory);
		rfmo.setInformationSourceList(informationSourceList);
		rfmo.setGeneralMeasureList(generalMeasureList);

		vme.setId(new Long(VME_ID));
		vme.setInventoryIdentifier(INVENTORY_ID);
		vme.setName(u.english("Hard Corner Bugs "));
		vme.setRfmo(rfmo);
		vme.setProfileList(pList);
		vme.setSpecificMeasureList(specificMeasureList);
		vme.setGeoRefList(geoRefList);
		vme.setValidityPeriod(ValidityPeriodMock.create());
		vme.setAreaType("Established VME");
		vme.setCriteria("Uniqueness or rarity");
		vme.setGeoArea(u.english("Southern Pacific Ocean"));

		return vme;

	}
}
