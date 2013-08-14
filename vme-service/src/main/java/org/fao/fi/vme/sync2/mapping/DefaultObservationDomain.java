package org.fao.fi.vme.sync2.mapping;

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
		o.setCollection(Figis.COLLECTION);
		o.setPrimary(Figis.PRIMARY);
		o.setReference(Figis.REFERENCE);
		o.setOrder(Figis.ORDER);
		return o;
	}

}
