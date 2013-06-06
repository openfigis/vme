package org.fao.fi.vme.domain;

import javax.persistence.Id;

/**
 * 
 * @author Erik van Ingen
 * 
 */
public class MultiLingualString {

	@Id
	private long id;
	private String string;
	private int language;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

}
