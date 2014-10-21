package org.vme.dao.test;

import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.rule.Figis;

/**
 * The definition of how a default observation is created in the batch.
 * 
 * @author Erik van Ingen
 * 
 */
public class DefaultObservationDomain {

	public ObservationDomain defineDefaultObservation() {
		ObservationDomain o = new ObservationDomain();
		// o.setCollection(Figis.RFMO_COLLECTION.get("NAFO"));
		o.setPrimary(Figis.PRIMARY);
		o.setReference(Figis.REFERENCE);
		o.setOrder(Figis.ORDER);
		return o;
	}

}
