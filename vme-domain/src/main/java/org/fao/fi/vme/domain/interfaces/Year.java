package org.fao.fi.vme.domain.interfaces;

/**
 * An YearObject is the holder of information for a given year. Or: an observation done in a given year.
 * 
 * 
 * @author Erik van Ingen
 * 
 */

public interface Year<E> {

	Integer getYear();

	void setYear(Integer year);

}