package org.fao.fi.vme.batch.sync2;

import org.fao.fi.vme.domain.model.Vme;

/**
 * Synchronising the FIGIS DB with the information from the VME domain.
 * Synchronising means in this case always pushing data from vme to figis.
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public interface Sync {

	public void sync();

	public void sync(Vme vme);

}
