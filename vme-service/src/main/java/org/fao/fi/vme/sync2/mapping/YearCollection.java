package org.fao.fi.vme.sync2.mapping;

import java.util.List;

import org.fao.fi.vme.domain.interfacee.Year;

/**
 * 
 * 
 * 
 * YearCollection is the collection of yearInformation objects for a given year.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class YearCollection {

	protected Integer year;
	protected List<Year<?>> yearObjectList;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<Year<?>> getYearObjectList() {
		return yearObjectList;
	}

	public void setYearObjectList(List<Year<?>> years) {
		this.yearObjectList = years;
	}

}
