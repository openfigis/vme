package org.vme.service.dao.sources.figis;

import org.fao.fi.figis.domain.VmeObservationDomain;

/**
 * Only one observation can be primary, can be the factsheet. Apply this rule.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class PrimaryRule {

	public void apply(VmeObservationDomain vod) {
		int indexLatesElement = vod.getObservationDomainList().size() - 1;
		for (int i = 0; i < vod.getObservationDomainList().size(); i++) {
			vod.getObservationDomainList().get(i).setPrimary(i == indexLatesElement);
		}
	}
}
