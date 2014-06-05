package org.fao.fi.vme.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGInstructions;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGName;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGWeight;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.DateDataConverter;

/**
 * 
 * Rules for the validity period
 * 
 * An object can have a validity period
 * 
 * 
 * Only one object with a certain validity period applies to a moment in time
 * 
 * Validity periods cannot overlap
 * 
 * Validaty periods are sequential A validity period has a begin and an end Date
 * 
 * If the end Date is open, 9999 will be used
 * 
 * The begin and end Date are inclusive Examples:
 * 
 * Objects={ Vme, SpecificMeasure, GeneralMeasure, }
 * 
 * ValidityPeriods for GeneralMeasureSet = {2000-2007, 2008-2009, 2010-9999}
 * 
 * 
 * See also
 * http://km.fao.org/FIGISwiki/index.php/VME_UML#Rules_for_the_validity_period
 * 
 * 
 * @author Erik van Ingen
 * 
 */
@Embeddable
public class ValidityPeriod implements Comparable<ValidityPeriod>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4888316652934717923L;

	/** 
	  
	  */
	@RSGName("Validity Period - Startdate")
	@RSGInstructions("Use the YYYY/MM/DD format")
	@RSGConverter(DateDataConverter.class)
	@RSGWeight(1)
	@Temporal(TemporalType.DATE)
	private Date beginDate;

	@RSGName("Validity Period - Enddate")
	@RSGInstructions("Use the YYYY/MM/DD format")
	@RSGConverter(DateDataConverter.class)
	@RSGWeight(2)
	@Temporal(TemporalType.DATE)
	private Date endDate;

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * This method does not validate
	 */
	@Override
	public int compareTo(ValidityPeriod vp) {
		int compare = 0;
		if (this.getEndDate().compareTo(vp.getBeginDate()) < 0) {
			compare = -1;
		}
		if (this.getBeginDate().compareTo(vp.getEndDate()) > 0) {
			compare = 1;
		}
		return compare;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.beginDate == null) ? 0 : this.beginDate.hashCode());
		result = prime * result + ((this.endDate == null) ? 0 : this.endDate.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ValidityPeriod other = (ValidityPeriod) obj;
		if (this.beginDate == null) {
			if (other.beginDate != null) {
				return false;
			}
		} else if (!this.beginDate.equals(other.beginDate)) {
			return false;
		}
		if (this.endDate == null) {
			if (other.endDate != null) {
				return false;
			}
		} else if (!this.endDate.equals(other.endDate)) {
			return false;
		}
		return true;
	}
}
