package org.fao.fi.vme.domain.logic;

import java.util.List;

import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.ValidityPeriod;
import org.fao.fi.vme.domain.interfacee.Period;

public class PeriodListValidator {

	public void validate(List<Period<?>> sortedPeriods) {
		int indexLastElement = sortedPeriods.size() - 1;
		for (int i = 0; i < sortedPeriods.size(); i++) {
			int indexNextElement = i + 1;
			if (i != indexLastElement) {
				ValidityPeriod vp1 = sortedPeriods.get(i).getValidityPeriod();
				ValidityPeriod vp2 = sortedPeriods.get(indexNextElement).getValidityPeriod();
				validatePeriod(vp1);
				validatePeriod(vp2);
				if (vp1.getEndYear() >= vp2.getBeginYear()) {
					throw new VmeException("Invalid ValidityPeriod, vp1.endYear is " + vp1.getEndYear()
							+ " and vp2.beginYear is " + vp2.getBeginYear());
				}
				int gap = vp2.getBeginYear() - vp1.getEndYear();
				if (gap != 1) {
					throw new VmeException("Gaps of more than 1 year between validityPeriods, vp1.endYear is "
							+ vp1.getEndYear() + " and vp2.beginYear is " + vp2.getBeginYear());
				}

			}
		}

	}

	private void validatePeriod(ValidityPeriod vp) {
		if (vp.getBeginYear() > vp.getEndYear()) {
			throw new VmeException("Invalid ValidityPeriod, beginYear is " + vp.getBeginYear() + " and endYear is "
					+ vp.getEndYear());
		}

	}
}
