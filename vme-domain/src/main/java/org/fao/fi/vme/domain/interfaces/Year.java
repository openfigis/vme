package org.fao.fi.vme.domain.interfaces;

/**
 * An YearObject is the holder of information for a given year. Or: an observation done in a given year.
 * 
 * 
 * @author Erik van Ingen
 * 
 */

public interface Year<E> {

	public abstract Integer getYear();

	public abstract void setYear(Integer year);

}