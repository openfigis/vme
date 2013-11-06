/**
 * 
 */
package org.vme.service.hardcoded.impl;

import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.vme.service.dto.ref.Authority;
import org.vme.service.dto.ref.VmeCriteria;
import org.vme.service.dto.ref.VmeType;
import org.vme.service.dto.ref.Year;


/**
 * @author Fabrizio Sibeni
 * 
 */


public class ReferenceDAOHardcodedHelper {
	
	
	private Map<Long, Authority> repAuthority;
	private Map<Long, VmeCriteria> repVmeCriteria;
	private Map<Long, VmeType> repVmeType;
	private Map<Long, Year> repYear;
	

	public ReferenceDAOHardcodedHelper() {
		super();
		repAuthority = new LinkedHashMap<Long, Authority>();
		createAuthorities();
		repVmeCriteria = new LinkedHashMap<Long, VmeCriteria>();
		createVmeCriterias();
		repVmeType = new LinkedHashMap<Long, VmeType>();
		createVmeTypes();
		repYear = new LinkedHashMap<Long, Year>();
		createYears();
	}


	
	
	public Authority getAuthority(Long key) {
		return repAuthority.get(key);
	}
	
	public Collection<Authority> getAllAuthorities(){
		return repAuthority.values();
	}
	
	
	public VmeCriteria getVmeCriteria(Long key) {
		return repVmeCriteria.get(key);
	}
	
	public Collection<VmeCriteria> getAllVmeCriterias(){
		return repVmeCriteria.values();
	}

	public VmeType getVmeType(Long key) {
		return repVmeType.get(key);
	}
	
	public Collection<VmeType> getAllVmeTypes(){
		return repVmeType.values();
	}

	public Year getYear(Long key) {
		return repYear.get(key);
	}
	
	public Collection<Year> getAllYears(){
		return repYear.values();
	}
	
	private void createAuthorities() {
		repAuthority.put((long)20010, new Authority(20010,"CCAMLR","Commission for the Conservation of Antarctic Marine Living Resources"));
		repAuthority.put((long)22080, new Authority(22080,"GFCM","General Fishery Commission for the Mediterranean sea"));
		repAuthority.put((long)20220, new Authority(20220,"NAFO","Northwest Atlantic Fisheries Organization"));
		repAuthority.put((long)21580, new Authority(21580,"NEAFC","North East Atlantic Fisheries Commission"));
		repAuthority.put((long)22140, new Authority(22140,"SEAFO","South East Atlantic Fisheries Organisation"));
		//repAuthority.put((long)90010, new Authority(22140,"SIODFA","Southern Indian Ocean Deepsea Fishers' Association"));
	}

	private void createVmeCriterias() {
		repVmeCriteria.put((long)10, new VmeCriteria(10,"Uniqueness or rarity"));
		repVmeCriteria.put((long)20, new VmeCriteria(20,"Functional significance of the habitat"));
		repVmeCriteria.put((long)30, new VmeCriteria(30,"Fragility"));
		repVmeCriteria.put((long)40, new VmeCriteria(40,"Life-history traits"));
		repVmeCriteria.put((long)50, new VmeCriteria(50,"Structural complexity"));
		repVmeCriteria.put((long)60, new VmeCriteria(60,"Unspecified"));
	}	

	private void createVmeTypes() {
		repVmeType.put((long)10, new VmeType(10,"VME (Adopted)"));
		repVmeType.put((long)20, new VmeType(20,"Risk area (closed area)"));
		repVmeType.put((long)30, new VmeType(30,"Benthic Protected Area (voluntary / industry closed areas)"));
		repVmeType.put((long)40, new VmeType(40,"Other types of managed area"));
	}

	
	private void createYears() {
		repYear.put((long)2013, new Year(2013));
		repYear.put((long)2012, new Year(2012));
		repYear.put((long)2011, new Year(2011));
		repYear.put((long)2010, new Year(2010));
		repYear.put((long)2009, new Year(2009));
		repYear.put((long)2008, new Year(2008));
		repYear.put((long)2007, new Year(2007));
		repYear.put((long)2006, new Year(2006));
	}
	
	
}
