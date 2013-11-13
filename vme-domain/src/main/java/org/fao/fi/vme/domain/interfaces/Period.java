package org.fao.fi.vme.domain.interfaces;

import org.fao.fi.vme.domain.model.ValidityPeriod;

/**
 * 
 * Period always has a defined ValidityPeriod.
 * 
 * Rules to be found here: http://km.fao.org/FIGISwiki/index.php/VME_UML#Rules_for_the_validity_period_and_Year
 * 
 * @author Erik van Ingen
 * 
 */
public interface Period<T> {

	public ValidityPeriod getValidityPeriod();

	public void setValidityPeriod(ValidityPeriod validityPeriod);

}
