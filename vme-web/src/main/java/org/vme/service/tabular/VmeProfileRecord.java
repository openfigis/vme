package org.vme.service.tabular;

import java.lang.reflect.Method;
import java.util.List;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;

public class VmeProfileRecord implements RecordGenerator<Vme, Profile, Empty> {

	private MultiLingualStringUtil u = new MultiLingualStringUtil();

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
		try {
			return Vme.class.getMethod("getProfileList");
		} catch (NoSuchMethodException e) {
			throw new VmeException(e);
		} catch (SecurityException e) {
			throw new VmeException(e);
		}
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
