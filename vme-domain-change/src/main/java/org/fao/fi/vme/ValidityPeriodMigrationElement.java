package org.fao.fi.vme;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.Period;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.vme.dao.sources.vme.VmeDao;

public class ValidityPeriodMigrationElement implements MigrationElement {

	@Inject
	private VmeDao vmeDao;

	private VpUgrader u = new VpUgrader();

	@Override
	public void migrate() {
		correct(Vme.class);
		correct(GeneralMeasure.class);
		correct(SpecificMeasure.class);
	}

	private <T> void correct(Class<T> clazz) {
		List<T> sList = vmeDao.loadObjects(clazz);
		for (T anyP : sList) {
			Period p = (Period) anyP;
			u.upgrade(p.getValidityPeriod());

			// strange hack to get this to work. The merge in Hibernate does not
			// work well for the many to many. See also
			// http://stackoverflow.com/questions/10889386/unsupportedoperationexception-when-merging-an-existing-hibernate-model-object
			if (p instanceof GeneralMeasure) {
				((GeneralMeasure) p).setInformationSourceList(null);
			}
			vmeDao.merge(p);
		}

	}
}
