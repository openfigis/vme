package org.fao.fi.vme.msaccess.component;

import javax.enterprise.inject.Alternative;

@Alternative
public class FilesystemMsAccessConnectionProvider extends MsAccessConnectionProvider {
	// public static String dbLocation =
	// "../vme-service-integrationtest/src/test/resources/NAFO_ONLY_VME_DB_production.accdb";
	public static String dbLocation = "../vme-service-integrationtest/src/test/resources/VME_DB_production.accdb";

	public FilesystemMsAccessConnectionProvider() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fao.fi.vme.msaccess.component.AbstractMsAccessConnectionProvider#
	 * getDBLocation()
	 */
	@Override
	protected String getDBLocation() {
		return dbLocation;
	}
}
