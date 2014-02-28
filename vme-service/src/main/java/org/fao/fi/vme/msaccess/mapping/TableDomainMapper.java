package org.fao.fi.vme.msaccess.mapping;

import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.ReferenceConceptProvider;

/**
 * 
 * All MS-Access tables have an associated class. These classes all implement
 * TableDomainMapper in order to map the Access table to a nice vme domain
 * class.
 * 
 * @author Erik van Ingen
 * 
 */
public interface TableDomainMapper {

	/**
	 * 
	 * @param tableObject
	 * @return domainObject
	 */
	Object map();
}
