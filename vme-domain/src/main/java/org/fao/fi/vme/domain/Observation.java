package org.fao.fi.vme.domain;

import javax.persistence.MappedSuperclass;

/**
 * 
 * @author Erik van Ingen
 * 
 */

@MappedSuperclass
public class Observation {

	/**
	 * The year in which the observation has been performed. Or: the year in which the measures have been taken.
	 * */
	private int year;

}
