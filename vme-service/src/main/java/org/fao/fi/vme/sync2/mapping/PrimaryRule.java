package org.fao.fi.vme.sync2.mapping;

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
		if (indexLatesElement >= 0) {
			vod.getObservationDomainList().get(indexLatesElement).setPrimary(true);
		}
	}
}
