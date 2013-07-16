/**
 * 
 */
package org.vme.service.reference.impl;

import java.util.Hashtable;
import org.vme.service.reference.domain.Authority;
import org.vme.service.reference.domain.VmeCriteria;
import org.vme.service.reference.domain.VmeType;


/**
 * @author Fabrizio Sibeni
 * 
 */


public class ReferenceServiceHelper {
	private Hashtable<Long, Authority> repAuthority;
	private Hashtable<Long, VmeCriteria> repVmeCriteria;
	private Hashtable<Long, VmeType> repVmeType;
	
	

	public ReferenceServiceHelper() {
		super();
		repAuthority = new Hashtable<Long, Authority>();
		createAuthorities();
		repVmeCriteria = new Hashtable<Long, VmeCriteria>();
		createVmeCriterias();
		repVmeType = new Hashtable<Long, VmeType>();
		createVmeTypes();
	}


	
	public void createAuthorities() {
		repAuthority.put((long)20010, new Authority(20010,"CCAMLR",null));
		repAuthority.put((long)22080, new Authority(22080,"GFCM",null));
		repAuthority.put((long)20220, new Authority(20220,"NAFO",null));
		repAuthority.put((long)21580, new Authority(21580,"NEAFC",null));
		repAuthority.put((long)22140, new Authority(22140,"SEAFO",null));
	}


		
	
	public void createVmeCriterias() {
		repVmeCriteria.put((long)10, new VmeCriteria(10,"Uniqueness or rarity"));
		repVmeCriteria.put((long)20, new VmeCriteria(20,"Functional significance of the habitat"));
		repVmeCriteria.put((long)30, new VmeCriteria(30,"Fragility"));
		repVmeCriteria.put((long)40, new VmeCriteria(40,"Life-history traits"));
		repVmeCriteria.put((long)50, new VmeCriteria(50,"Structural complexity"));
		repVmeCriteria.put((long)60, new VmeCriteria(60,"Unspecified"));
	}	

	public void createVmeTypes() {
		repVmeType.put((long)10, new VmeType(10,"Established VME"));
		repVmeType.put((long)20, new VmeType(20,"Established VME"));
		repVmeType.put((long)30, new VmeType(30,"Temporary VME"));
		repVmeType.put((long)40, new VmeType(40,"Risk area"));
		repVmeType.put((long)50, new VmeType(50,"Benthic protected area"));
		repVmeType.put((long)60, new VmeType(60,""));
		repVmeType.put((long)70, new VmeType(70,"Voluntary closed area"));
		repVmeType.put((long)80, new VmeType(80,"Other types of managed area"));

	}
	
	
}
