package org.fao.fi.vme.domain.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.VMEsHistory;
import org.fao.fi.vme.domain.model.ValidityPeriod;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.reference.InformationSourceType;
import org.fao.fi.vme.domain.model.reference.VmeType;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class VmeMock {

	protected static Long ID = 1000L;
	public static final int YEAR = 2000;
	public static final String INVENTORY_ID = "VME_RFMO_1";
	public static final String INVENTORY_ID_YEAR = INVENTORY_ID + "_" + YEAR;
	public static final Long VME_SCOPE = 10L;
	public static final String VME_CODESYSTEM = "vme";
	public static final String NAME = "Hard Corner Bugs ";
	public static final String GEO_AREA = "Southern Pacific Ocean";
	public static final String HISTORY = "History repeating";
	public static final String SPEC_MEASURE_CIT = "This is a citation for a SpecificMeasure";
	public static final Map<Long, String> RFMO_MAP = new HashMap<Long, String>();

	static {
		RFMO_MAP.put(ID, "CCAMLR");
		RFMO_MAP.put(ID + 1, "GFCM");
		RFMO_MAP.put(ID + 2, "NAFO");
		RFMO_MAP.put(ID + 3, "NEAFC");
		RFMO_MAP.put(ID + 4, "NPFC");
		RFMO_MAP.put(ID + 5, "SEAFO");
		RFMO_MAP.put(ID + 6, "SPRFMO");
		RFMO_MAP.put(ID + 7, "WECAFC");
		RFMO_MAP.put(ID + 9, "x1");
		RFMO_MAP.put(ID + 10, "x2");
		RFMO_MAP.put(ID + 11, "x3");
		RFMO_MAP.put(ID + 12, "x4");
		RFMO_MAP.put(ID + 13, "x5");
		RFMO_MAP.put(ID + 14, "x6");
	}

	private static MultiLingualStringUtil u = new MultiLingualStringUtil();

	private VmeMock() {

	}

	public static Vme create() {
		Vme vme = new Vme();
		vme.setValidityPeriod(ValidityPeriodMock.create());
		vme.setName(u.english(NAME));
		vme.setGeoArea(u.english(GEO_AREA));
		vme.setScope(VME_SCOPE);
		vme.setInventoryIdentifier(INVENTORY_ID);

		List<GeoRef> l = new ArrayList<GeoRef>();

		for (int i = ValidityPeriodMock.BEGIN_YEAR; i <= ValidityPeriodMock.END_YEAR; i++) {
			GeoRef g = new GeoRef();
			g.setYear(i);
			g.setGeographicFeatureID(INVENTORY_ID + i);
			g.setVme(vme);
			l.add(g);
		}

		vme.setGeoRefList(l);

		List<Profile> gl = new ArrayList<Profile>();
		for (int i = ValidityPeriodMock.BEGIN_YEAR; i <= ValidityPeriodMock.END_YEAR; i++) {
			Profile g = new Profile();
			g.setYear(i);
			g.setVme(vme);
			gl.add(g);
		}
		vme.setProfileList(gl);

		VmeType vmeType = VmeTypeMock.create();

		vme.setAreaType(vmeType.getId());
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

		vme2.getGeoRefList().get(0).setYear(YEAR + 1);

		l.add(vme1);
		l.add(vme2);
		return l;
	}

	public static Vme generateVme(int nrOfyears) {
		int startYear = YEAR;
		int endYear = YEAR + nrOfyears - 1;
		ID = 1000L;

		Vme vme = new Vme();

		vme.setScope(VME_SCOPE);

		List<InformationSource> informationSourceList = new ArrayList<InformationSource>();
		List<Profile> pList = new ArrayList<Profile>();
		List<SpecificMeasure> specificMeasureList = new ArrayList<SpecificMeasure>();
		List<GeneralMeasure> generalMeasureList = new ArrayList<GeneralMeasure>();
		List<GeoRef> geoRefList = new ArrayList<GeoRef>();
		List<FisheryAreasHistory> fishingHistoryList = new ArrayList<FisheryAreasHistory>();
		List<VMEsHistory> hasVmesHistory = new ArrayList<VMEsHistory>();

		InformationSourceType ist = InformationSourceMock.createInformationSourceType();

		for (int i = 0; i < nrOfyears; i++) {
			int year = startYear + i;

			// this one is used for the genericMeasure
			InformationSource is = InformationSourceMock.create();
			is.setPublicationYear(year);
			is.setSourceType(ist);
			informationSourceList.add(is);

			// this one is used for the specificMeasure
			InformationSource isSm = InformationSourceMock.create();
			isSm.setPublicationYear(year);
			isSm.setCitation(u.english(SPEC_MEASURE_CIT));

			FisheryAreasHistory fahistory = new FisheryAreasHistory();
			fahistory.setYear(year);
			fahistory.setHistory(u.english(HISTORY));

			VMEsHistory vmehistory = new VMEsHistory();
			vmehistory.setYear(year);
			vmehistory.setHistory(u.english(HISTORY));

			fishingHistoryList.add(fahistory);
			hasVmesHistory.add(vmehistory);

			Profile profile = new Profile();
			profile.setGeoform(u.english("Geoform"));
			profile.setDescriptionBiological(u.english("Hello World DescriptionBiological"));
			profile.setDescriptionImpact(u.english("Hello World DescriptionImpact"));
			profile.setDescriptionPhisical(u.english("Hello World DescriptionPhisical"));
			profile.setVme(vme);

			profile.setYear(year);
			pList.add(profile);

			SpecificMeasure specificMeasure = new SpecificMeasure();

			specificMeasure.setYear(year);
			specificMeasure.setVmeSpecificMeasure(u.english("A specific measure for the year " + year));
			specificMeasure.setValidityPeriod(ValidityPeriodMock.create(year, year + 1));
			specificMeasure.setInformationSource(isSm);
			specificMeasure.setReviewYear(year + 1);
			specificMeasure.setVme(vme);
			specificMeasureList.add(specificMeasure);

			for (int k = 0; k < nrOfyears; k++) {
				GeoRef geoRef = new GeoRef();
				geoRef.setVme(vme);

				geoRef.setYear(year);
				geoRef.setGeographicFeatureID(INVENTORY_ID_YEAR);
				geoRefList.add(geoRef);
			}

			GeneralMeasure gm = new GeneralMeasure();
			gm.setYear(year);
			gm.setFishingArea(u.english("a [FishingArea] general measure"));
			gm.setExplorataryFishingProtocol(u.english("an [ExploratoryFishingProtocol] general measure"));
			gm.setVmeEncounterProtocol(u.english("a [VmeEncounterProtocols] general measure"));
			gm.setVmeThreshold(u.english("a [VmeThreshold] general measure"));
			gm.setVmeIndicatorSpecies(u.english("a [VmeIndicatorSpecies] general measure"));
			gm.setValidityPeriod(ValidityPeriodMock.create(year, year + 1));
			gm.setInformationSourceList(Arrays.asList(is));
			for (InformationSource is1 : gm.getInformationSourceList()) {
				is1.setCitation(u.english(SPEC_MEASURE_CIT));
			}
			generalMeasureList.add(gm);

		}

		Rfmo rfmo = new Rfmo();

		String rfmoId = RFMO_MAP.get(ID++);
		if (rfmoId == null) {
			rfmoId = "SPRFMO" + ID;
		}
		rfmo.setId(rfmoId);

		System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE=" + rfmoId);

		rfmo.setHasFisheryAreasHistory(fishingHistoryList);
		rfmo.setHasVmesHistory(hasVmesHistory);
		rfmo.setInformationSourceList(informationSourceList);
		rfmo.setGeneralMeasureList(generalMeasureList);

		vme.setMediaReferenceList(MediaReferenceMock.createList(vme));
		vme.setInventoryIdentifier(INVENTORY_ID);
		vme.setName(u.english(NAME));
		vme.setRfmo(rfmo);
		vme.setProfileList(pList);
		vme.setSpecificMeasureList(specificMeasureList);
		vme.setGeoRefList(geoRefList);
		ValidityPeriod vp = ValidityPeriodMock.create(startYear, endYear);
		vme.setValidityPeriod(vp);
		vme.setAreaType(VmeTypeMock.create().getId());
		vme.setCriteria(new ArrayList<Long>(Arrays.asList(VmeCriteriaMock.create().getId(), VmeCriteriaMock
				.createAnother().getId(), VmeCriteriaMock.createYetAnother().getId())));
		vme.setGeoArea(u.english(GEO_AREA));

		return vme;

	}
}
