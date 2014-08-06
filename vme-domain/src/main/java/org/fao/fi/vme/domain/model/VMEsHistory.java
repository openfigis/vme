/**
 * (c) 2013 FAO / UN (project: vme-domain)
 */
package org.fao.fi.vme.domain.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.interfaces.Year;
import org.fao.fi.vme.domain.support.MultiLingualStringConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReferenceReport;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGName;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGRichInput;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGSimpleReference;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGWeight;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.IntegerDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.LongDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.ReferenceReport;

/**
 * Place your class / interface description here.
 * 
 * History:
 * 
 * ------------- --------------- ----------------------- Date Author Comment
 * ------------- --------------- ----------------------- 15 Jan 2014 Fiorellato
 * Creation.
 * 
 * @version 1.0
 * @since 13 Nov 2013
 */
@RSGReferenceReport(name = "Regional History of VMEs")
@Entity(name = "VMES_HISTORY")
public class VMEsHistory implements ObjectId<Long>, History, Year<History>, ReferenceReport, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -83401529380589245L;

	/** 
	 * 
	 */
	@RSGIdentifier
	@RSGConverter(LongDataConverter.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * VMEsHistory has 0,1 Rfmo
	 */
	@RSGName("Authority")
	@RSGSimpleReference
	@RSGWeight(0)
	@ManyToOne
	@JoinColumn(name = "rfmo_id")
	private Rfmo rfmo;

	/**
	 *  
	 */
	@RSGName("Year")
	@RSGWeight(1)
	@RSGConverter(IntegerDataConverter.class)
	private Integer year;

	/**
	 *  
	 */
	@RSGName("History")
	@RSGConverter(MultiLingualStringConverter.class)
	@RSGWeight(1)
	@OneToOne(cascade = { CascadeType.ALL })
	private @RSGRichInput MultiLingualString history;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fao.fi.vme.domain.model.IHistory#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the 'rfmo' value
	 */
	public Rfmo getRfmo() {
		return this.rfmo;
	}

	/**
	 * @param rfmo
	 *            the 'rfmo' value to set
	 */
	public void setRfmo(Rfmo rfmo) {
		this.rfmo = rfmo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fao.fi.vme.domain.model.IHistory#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fao.fi.vme.domain.model.IHistory#getYear()
	 */
	@Override
	public Integer getYear() {
		return year;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fao.fi.vme.domain.model.IHistory#setYear(java.lang.Integer)
	 */
	@Override
	public void setYear(Integer year) {
		this.year = year;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fao.fi.vme.domain.model.IHistory#getHistory()
	 */
	@Override
	public MultiLingualString getHistory() {
		return history;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fao.fi.vme.domain.model.IHistory#setHistory(org.fao.fi.vme.domain
	 * .model.MultiLingualString)
	 */
	@Override
	public void setHistory(MultiLingualString history) {
		this.history = history;
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
		result = prime * result + ((this.history == null) ? 0 : this.history.hashCode());
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		result = prime * result + ((this.year == null) ? 0 : this.year.hashCode());
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
		VMEsHistory other = (VMEsHistory) obj;
		if (this.history == null) {
			if (other.history != null) {
				return false;
			}
		} else if (!this.history.equals(other.history)) {
			return false;
		}
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		if (this.year == null) {
			if (other.year != null) {
				return false;
			}
		} else if (!this.year.equals(other.year)) {
			return false;
		}
		return true;
	}
}