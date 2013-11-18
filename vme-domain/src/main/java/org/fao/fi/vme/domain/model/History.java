package org.fao.fi.vme.domain.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.fao.fi.vme.domain.interfaces.Year;
import org.fao.fi.vme.domain.support.MultiLingualStringConverter;
import org.gcube.application.rsg.support.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.annotations.fields.RSGIdentifier;
import org.gcube.application.rsg.support.annotations.fields.RSGName;
import org.gcube.application.rsg.support.converters.impl.IntegerDataConverter;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity(name = "HISTORY")
public class History implements Year<History> {

	/** 
	 * 
	 */
	@RSGIdentifier
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 *  
	 */
	@RSGName("Year")
	@RSGConverter(IntegerDataConverter.class)
	private Integer year;

	/**
	 *  
	 */
	@RSGName("History")
	@RSGConverter(MultiLingualStringConverter.class)
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString history;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Integer getYear() {
		return year;
	}

	@Override
	public void setYear(Integer year) {
		this.year = year;
	}

	public MultiLingualString getHistory() {
		return history;
	}

	public void setHistory(MultiLingualString history) {
		this.history = history;
	}

	// @Override
	// public int hashCode() {
	// final int prime = 31;
	// int result = 1;
	// result = prime * result + ((history == null) ? 0 : history.hashCode());
	// result = prime * result + ((id == null) ? 0 : id.hashCode());
	// result = prime * result + ((year == null) ? 0 : year.hashCode());
	// return result;
	// }
	//
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		History other = (History) obj;
		if (history == null) {
			if (other.history != null)
				return false;
		} else if (!history.equals(other.history))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

}
