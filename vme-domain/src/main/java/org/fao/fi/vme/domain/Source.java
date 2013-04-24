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
}
