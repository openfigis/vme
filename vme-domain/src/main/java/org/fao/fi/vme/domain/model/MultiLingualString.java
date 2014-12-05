package org.fao.fi.vme.domain.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * Not applied yet.
 * 
 * See also http://stackoverflow.com/questions/5964027/localizable-strings-in-jpa
 * 
 * 
 * http://hwellmann.blogspot.it/2010/07/jpa-20-querying-map.html
 * 
 * http://hwellmann.blogspot.it/2010/07/jpa-20-mapping-map.html
 * 
 * @author Erik van Ingen
 * 
 */
@Entity(name = "MULTILINGUAL_STRING")
public class MultiLingualString implements ObjectId<Long>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3541784570734810462L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * map of descriptions per language
	 * 
	 * Not using eager sometimes leads to problems with hibernate (Failed toString() invocation on an object of type
	 * java.lang.NullPointerException at
	 * org.hibernate.collection.internal.AbstractPersistentCollection$1ExtraLazyElementByIndexReader
	 * .doWork(AbstractPersistentCollection.java:359))
	 * 
	 * 
	 */
	@ElementCollection(fetch = FetchType.EAGER)
	@Lob
	@CollectionTable(name = "MULTILINGUALSTRING_STRINGMAP")
	private Map<Integer, String> stringMap;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Map<Integer, String> getStringMap() {
		return stringMap;
	}

	public void setStringMap(Map<Integer, String> stringMap) {
		this.stringMap = stringMap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((stringMap == null) ? 0 : stringMap.hashCode());
		return result;
	}

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
		MultiLingualString other = (MultiLingualString) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (stringMap == null) {
			if (other.stringMap != null) {
				return false;
			}
		} else if (!stringMap.equals(other.stringMap)) {
			return false;
		}
		return true;
	}

}