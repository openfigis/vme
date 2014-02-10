/**
 * 
 */
package org.fao.fi.vme.batch.reference;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.VmeCriteria;
import org.fao.fi.vme.domain.model.VmeType;
import org.vme.dao.VmeReferenceDao;

/**
 * Batch will which load or update the reference data
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class ReferenceDataHardcodedBatch {

	@Inject
	private VmeReferenceDao dao;

	public void run() {
		createAuthorities();
		createVmeCriterias();
		createVmeTypes();
		// createYears();
	}

	private void createAuthorities() {
		dao.syncStoreObject(new Authority(20010, "CCAMLR",
				"Commission for the Conservation of Antarctic Marine Living Resources"), 20010);
		dao.syncStoreObject(new Authority(22080, "GFCM", "General Fishery Commission for the Mediterranean sea"), 22080);
		dao.syncStoreObject(new Authority(20220, "NAFO", "Northwest Atlantic Fisheries Organization"), 20220);
		dao.syncStoreObject(new Authority(21580, "NEAFC", "North East Atlantic Fisheries Commission"), 21580);
		dao.syncStoreObject(new Authority(22140, "SEAFO", "South East Atlantic Fisheries Organisation"), 22140);
		// repAuthority.put((long)90010, new
		// Authority(22140,"SIODFA","Southern Indian Ocean Deepsea Fishers' Association"),);
	}

	private void createVmeCriterias() {
		dao.syncStoreObject(new VmeCriteria(10, "Uniqueness or rarity"), 10);
		dao.syncStoreObject(new VmeCriteria(20, "Functional significance of the habitat"), 20);
		dao.syncStoreObject(new VmeCriteria(30, "Fragility"), 30);
		dao.syncStoreObject(new VmeCriteria(40, "Life-history traits"), 40);
		dao.syncStoreObject(new VmeCriteria(50, "Structural complexity"), 50);
		dao.syncStoreObject(new VmeCriteria(60, "Unspecified"), 60);
	}

	private void createVmeTypes() {
		dao.syncStoreObject(new VmeType(10, "VME"), 10);
		dao.syncStoreObject(new VmeType(20, "Risk area"), 20);
		dao.syncStoreObject(new VmeType(30, "Other types of closed/restricted area"), 30);
	}

	// private void createYears() {
	// dao.syncStoreObject(new ReferenceYear(2013), 2013);
	// dao.syncStoreObject(new ReferenceYear(2012), 2012);
	// dao.syncStoreObject(new ReferenceYear(2011), 2011);
	// dao.syncStoreObject(new ReferenceYear(2010), 2010);
	// dao.syncStoreObject(new ReferenceYear(2009), 2009);
	// dao.syncStoreObject(new ReferenceYear(2008), 2008);
	// dao.syncStoreObject(new ReferenceYear(2007), 2007);
	// dao.syncStoreObject(new ReferenceYear(2006), 2006);
	// }
}
