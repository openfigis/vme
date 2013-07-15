package org.fao.fi.vme.domain;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;

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
public class MultiLingualString {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * map of descriptions per language
	 * 
	 */

	
	@ElementCollection
	@Column(length=4096)
	private Map<Integer, String> stringMap;

	public Long getId() {
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
