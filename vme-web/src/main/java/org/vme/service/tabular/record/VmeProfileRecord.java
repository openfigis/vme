package org.vme.service.tabular.record;

import java.lang.reflect.Method;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.VmeCriteria;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.vme.dao.ReferenceDAO;
import org.vme.service.tabular.Empty;
import org.vme.service.tabular.RecordGenerator;

public class VmeProfileRecord extends AbstractRecord implements RecordGenerator<Vme, Profile, Empty> {

	@Inject
	@ConceptProvider
	private ReferenceDAO referenceDAO;

	@Override
	public void doFirstLevel(Vme v, List<Object> nextRecord) {
		nextRecord.add(u.getEnglish(v.getName()));
		nextRecord.add(v.getInventoryIdentifier());
		nextRecord.add(v.getAreaType());
		nextRecord.add(u.getEnglish(v.getGeoArea()));

		List<Long> criteria = v.getCriteria();
		String criteriaString = "";
		for (Long criteriaLong : criteria) {
			try {
				VmeCriteria c = referenceDAO.getReferenceByID(VmeCriteria.class, criteriaLong);
				if (c != null) {
					criteriaString = criteriaString + '\n' + c.getName();

				}
			} catch (Exception e) {
				throw new VmeException(e);
			}
		}
		System.out.println(criteriaString);
		nextRecord.add(criteriaString);

		if (v.getValidityPeriod() == null) {
			nextRecord.add("");
			nextRecord.add("");
		} else {
			nextRecord.add(v.getValidityPeriod().getBeginDate());
			nextRecord.add(v.getValidityPeriod().getEndDate());
		}
	}

	public void doSecondLevel(Profile p, List<Object> nextRecord) {
		nextRecord.add(p.getYear());
		nextRecord.add(u.getEnglish(p.getGeoform()));
		nextRecord.add(u.getEnglish(p.getDescriptionPhisical()));
		nextRecord.add(u.getEnglish(p.getDescriptionBiological()));
		nextRecord.add(u.getEnglish(p.getDescriptionImpact()));
	}

	@Override
	public Method getSecondLevelMethod() {
		return getMethod(Vme.class, "getProfileList");
	}

	@Override
	public String[] getHeaders() {
		return new String[] { "Vme Name", "Inventory Identifier", "Area Type", "Geographic Reference", "Criteria",
				"Begin date", "End date", "Year", "Type of sea floor physiography",
				"Physical description of the environment", "General Biology", "Impacts" };
	}

	@Override
	public void doThirdLevel(Empty p, List<Object> nextRecord) {
		/*
		 * Unusued method
		 */
	}

	@Override
	public Method getThirdLevelMethod() {
		return null;
	}
}
