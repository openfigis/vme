package org.fao.fi.vme.domain;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;

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
@Entity
public class MultiLingualString {

	@Id
	private long id;

	/**
	 * map of descriptions per language
	 * 
	 */
	private Map<Integer, String> stringMap;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Map<Integer, String> getStringMap() {
		return stringMap;
	}

	public void setStringMap(Map<Integer, String> stringMap) {
		this.stringMap = stringMap;
	}

}
