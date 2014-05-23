package org.vme.service.tabular.record;

import java.lang.reflect.Method;
import java.util.List;

import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Vme;
import org.vme.service.tabular.Empty;
import org.vme.service.tabular.RecordGenerator;

public class VmeProfileRecord extends AbstractRecord implements RecordGenerator<Vme, Profile, Empty> {

	@Override
	public void doFirstLevel(Vme v, List<Object> nextRecord) {
		nextRecord.add(u.getEnglish(v.getName()));
		nextRecord.add(v.getAreaType());
		nextRecord.add(u.getEnglish(v.getGeoArea()));
		nextRecord.add(v.getCriteria());
		nextRecord.add(v.getValidityPeriod().getBeginYear());
		nextRecord.add(v.getValidityPeriod().getEndYear());

	}

	public void doSecondLevel(Profile p, List<Object> nextRecord) {

		nextRecord.add(p.getYear());
		nextRecord.add(u.getEnglish(p.getGeoform()));
		nextRecord.add(u.getEnglish(p.getDescriptionPhisical()));
		nextRecord.add(u.getEnglish(p.getDescriptionBiological()));
		nextRecord.add(u.getEnglish(p.getDescriptionImpact()));

	}

	@Override
	public void doThirdLevel(Empty p, List<Object> nextRecord) {
	}

	@Override
	public Method getSecondLevelMethod() {
		return getMethod(Vme.class, "getProfileList");
	}

	@Override
	public Method getThirdLevelMethod() {
		return null;
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "Vme Name", "Area Type", "Geographic Reference", "Criteria", "Begin year", "End year",
				"Profile Year", "Geo Form", "Physical description", "Biological description", "Impact description" };
	}
}
