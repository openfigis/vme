package org.vme.service.tabular;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class TabularGenerator {

	public static final String[] VMEPROFILE = { "Vme Name", "Area Type", "Geographic Reference", "Criteria",
			"Begin year", "End year", "Profile Year", "Geo Form", "Physical description", "Biological description",
			"Impact description" };

	private MultiLingualStringUtil u = new MultiLingualStringUtil();

	public List<List<Object>> generateVmeProfile(List<Vme> vmeList) {
		List<List<Object>> tabular = new ArrayList<List<Object>>();
		List<Object> firstRecord = new ArrayList<Object>(Arrays.asList(VMEPROFILE));
		tabular.add(firstRecord);
		for (Vme v : vmeList) {
			if (v.getProfileList() != null && !v.getProfileList().isEmpty()) {
				for (Profile p : v.getProfileList()) {
					List<Object> nextRecord = new ArrayList<Object>();
					doFirstLevelVmeProfile(v, nextRecord);
					doNextLevelVmeProfile(p, nextRecord);
					tabular.add(nextRecord);
				}
			} else {
				List<Object> nextRecord = new ArrayList<Object>();
				doFirstLevelVmeProfile(v, nextRecord);
				tabular.add(nextRecord);
				fillUp(nextRecord, VMEPROFILE.length);
			}
		}
		return tabular;
	}

	private void fillUp(List<Object> nextRecord, int length) {
		for (int i = nextRecord.size(); i < length; i++) {
			nextRecord.add(null);
		}
	}

	private void doFirstLevelVmeProfile(Vme v, List<Object> nextRecord) {
		nextRecord.add(u.getEnglish(v.getName()));
		nextRecord.add(v.getAreaType());
		nextRecord.add(u.getEnglish(v.getGeoArea()));
		nextRecord.add(v.getCriteria());
		nextRecord.add(v.getValidityPeriod().getBeginYear());
		nextRecord.add(v.getValidityPeriod().getEndYear());

	}

	private void doNextLevelVmeProfile(Profile p, List<Object> nextRecord) {

		nextRecord.add(p.getYear());
		nextRecord.add(u.getEnglish(p.getGeoform()));
		nextRecord.add(u.getEnglish(p.getDescriptionPhisical()));
		nextRecord.add(u.getEnglish(p.getDescriptionBiological()));
		nextRecord.add(u.getEnglish(p.getDescriptionImpact()));

	}

	public List<List<Object>> generateSpecificMeasure() {
		return null;
	};

	public List<List<Object>> generateGeneralMeasure() {
		return null;
	};

	public List<List<Object>> generateHistory() {
		return null;
	};

	public List<List<Object>> generateInfoSource() {
		return null;
	};

	public List<List<Object>> generateGeoRef() {
		return null;
	};

}
