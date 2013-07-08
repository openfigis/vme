package org.fao.fi.vme.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Entity(name = "HISTORY")
public class History implements YearObject<History> {

	/** 
	 * 
	 */
	@Id
	private Integer id;

	/**
	 *  
	 */
	private Integer year;

	/**
	 *  
	 */
	@OneToOne(cascade = { CascadeType.ALL })
	private MultiLingualString history;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
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

	@Override
	public int hashCode() {
		final Integer prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		History other = (History) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
