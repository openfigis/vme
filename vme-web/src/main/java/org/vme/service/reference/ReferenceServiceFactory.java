/**
 * 
 */
package org.vme.service.reference;

import org.vme.service.reference.impl.ReferenceServiceImpl;



/**
 * Static factory class for obtaining a reference service instance.
 * @author  Francesco
 */
public class ReferenceServiceFactory {
	/**
	 * The reference service singleton instance.
	 */
	private static ReferenceService service;
	
	/**
	 * Return the reference service singleton instance.
	 * @return the reference service instance
	 */
	public static ReferenceService getService() {
		if (service == null) {
			service = new ReferenceServiceImpl();
		}
		return service;
	}
}
