/**
 * 
 */
package org.vme.web.service.search;

import java.util.UUID;

/**
 * @author Fabrizio Sibeni
 * 
 */
public class ReferencesRequest extends ServiceRequest {

	private String concept;

	private String lang;

	/**
	 * @param uuid
	 */
	public ReferencesRequest(UUID uuid) {
		super(uuid);
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

}
