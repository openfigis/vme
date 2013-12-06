/**
 * 
 */
package org.vme.web.service;

import org.fao.fi.vme.msaccess.VmeAccessDbImport;

/**
 * @author SIBENI
 * 
 */
public class DbBootstrapper {

	/**
	 * Microsoft access database file
	 */
	public final static int EMBEDDED_ACCESS_DB = 0;

	private static boolean hasbooted = false;

	// @Inject
	private VmeAccessDbImport vmeAccessDbImport;

	public DbBootstrapper() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void bootDb() throws Exception {
		vmeAccessDbImport.importMsAccessData();
		hasbooted = true;
	}

	/**
	 * @return the hasbooted
	 */
	public static boolean hasbooted() {
		return hasbooted;
	}

}
