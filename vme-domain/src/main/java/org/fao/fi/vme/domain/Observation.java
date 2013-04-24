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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
