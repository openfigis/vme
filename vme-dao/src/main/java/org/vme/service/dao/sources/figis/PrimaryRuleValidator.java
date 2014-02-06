package org.vme.service.dao.sources.figis;

import java.util.List;

import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.vme.VmeException;

public class PrimaryRuleValidator {

	public void validate(VmeObservationDomain vod) {

		List<ObservationDomain> odList = vod.getObservationDomainList();

		int indexLast = odList.size() - 1;
		int indexOneBeforeTheLast = indexLast - 1;

		String message = null;

		// only the last should be primary
		if (odList.get(indexLast).isPrimary() == false) {
			message = message + "The last obervation should be the primary";
		}

		// all others should not be primary
		for (int i = 0; i < indexOneBeforeTheLast; i++) {
			if (odList.get(i).isPrimary() == true) {
				message = message + "Found primary observation, before the last, is not correct";
			}
		}
		if (message != null) {
			throw new VmeException(message);
		}

	}
}
