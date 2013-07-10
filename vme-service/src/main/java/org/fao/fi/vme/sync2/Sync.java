package org.fao.fi.vme.sync2;

/**
 * Synchronising the FIGIS DB with the information from the VME domain. Synchronising means in this case always pushing
 * data from vme to figis.
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public interface Sync {

	public void sync();

}
