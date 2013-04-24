package org.fao.fi.vme.domain;

import java.net.URL;

import javax.persistence.Embeddable;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@Embeddable
public class Source {

	/**
	 * The url where the document is to be found
	 */
	private URL url;

	/**
	 * The title
	 */
	private String citation;

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public String getCitation() {
		return citation;
	}

	public void setCitation(String citation) {
		this.citation = citation;
	}

}
