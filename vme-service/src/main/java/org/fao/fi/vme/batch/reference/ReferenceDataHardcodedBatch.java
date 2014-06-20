/**
 * 
 */
package org.fao.fi.vme.batch.reference;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.reference.InformationSourceType;
import org.fao.fi.vme.domain.model.reference.VmeCriteria;
import org.fao.fi.vme.domain.model.reference.VmeScope;
import org.fao.fi.vme.domain.model.reference.VmeType;
import org.vme.dao.ReferenceBatchDao;
import org.vme.dao.sources.vme.VmeDao;

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
	private ReferenceBatchDao dao;

	@Inject
	private VmeDao vmeDao;

	public void run() {
		createAuthorities();
		createVmeCriterias();
		createVmeTypes();
		createInformationSourceTypes();
		createVmeScopes();
		synchAuthorityWithRfmo();
	}

	private void createVmeScopes() {
		dao.syncStoreObject(new VmeScope(10L, "VME"));
		dao.syncStoreObject(new VmeScope(20L, "Regulatory"));
	}

	/**
	 * Temporary stuff in order to be able to use access. Delete when access is
	 * phased out.
	 * 
	 */
	public void runBefore() {
		createAuthorities();
		createVmeCriterias();
		createVmeTypes();
		createInformationSourceTypes();
		// createYears();

	}

	/**
	 * Temporary stuff in order to be able to use access. Delete when access is
	 * phased out.
	 * 
	 */
	public void runAfter() {

		synchAuthorityWithRfmo();
	}

	/**
	 * Fabrizio had created the Authority table in the VME DB in order to be
	 * able to search, this Authority table is a reference data table. The Rfmo
	 * table holds only a reference.
	 */
	private void synchAuthorityWithRfmo() {
		List<Authority> l = vmeDao.loadObjects(Authority.class);
		for (Authority authority : l) {
			Rfmo rfmo = vmeDao.getEm().find(Rfmo.class, authority.getAcronym());
			if (rfmo == null) {
				Rfmo newRfmo = new Rfmo();
				newRfmo.setId(authority.getAcronym());
				vmeDao.persist(newRfmo);
			}
		}
	}

	private void createAuthorities() {

		dao.syncStoreObject(new Authority(20010L, "CCAMLR",
				"Commission for the Conservation of Antarctic Marine Living Resources"));
		dao.syncStoreObject(new Authority(24561L, "GFCM", "General Fishery Commission for the Mediterranean sea"));
		dao.syncStoreObject(new Authority(20220L, "NAFO", "Northwest Atlantic Fisheries Organization"));
		dao.syncStoreObject(new Authority(21580L, "NEAFC", "North East Atlantic Fisheries Commission"));
		dao.syncStoreObject(new Authority(22140L, "SEAFO", "South East Atlantic Fisheries Organisation"));
		dao.syncStoreObject(new Authority(22150L, "WECAFC", "Western Central Atlantic Fishery Commission"));
		dao.syncStoreObject(new Authority(24558L, "SPRFMO", "South Pacific Regional Fisheries Management Organisation"));

		// repAuthority.put((long)90010, new
		// Authority(22140,"SIODFA","Southern Indian Ocean Deepsea Fishers' Association");
	}

	private void createVmeCriterias() {
		dao.syncStoreObject(new VmeCriteria(10L, "Uniqueness or rarity"));
		dao.syncStoreObject(new VmeCriteria(20L, "Functional significance of the habitat"));
		dao.syncStoreObject(new VmeCriteria(30L, "Fragility"));
		dao.syncStoreObject(new VmeCriteria(40L, "Life-history traits"));
		dao.syncStoreObject(new VmeCriteria(50L, "Structural complexity"));
		dao.syncStoreObject(new VmeCriteria(60L, "Unspecified"));
	}

	private void createVmeTypes() {
		// dao.syncStoreObject(new VmeType(10L, "VME"));
		// dao.syncStoreObject(new VmeType(20L, "Risk area"));
		// dao.syncStoreObject(new VmeType(30L,
		// "Other types of closed/restricted area"));

		//Changing VME Types (following a request by A. Gentile - June 20th 2014
		dao.syncStoreObject(new VmeType(10L, "CLOS_AREA_BTMFISH", "Area closed to bottom fishing (SPRFMO)"));
		dao.syncStoreObject(new VmeType(20L, "CLOS_VME", "VME closure (NEAFC)"));
		dao.syncStoreObject(new VmeType(30L, "CLOS_AREA", "Closed area (SEAFO)"));
		dao.syncStoreObject(new VmeType(40L, "AREA_SPOCOR", "Area of higher sponge and coral concentration (NAFO)"));
		dao.syncStoreObject(new VmeType(41L, "CLOS_CORAL", "Coral closure (NAFO)"));
		dao.syncStoreObject(new VmeType(42L, "CLOS_SEAM", "Seamount closure (NAFO)"));
		dao.syncStoreObject(new VmeType(50L, "AREA_RESTRICTED", "Fisheries Restricted Area (GFCM)"));
		dao.syncStoreObject(new VmeType(60L, "AREA_RISK", "Risk Area (CCAMLR)"));
		dao.syncStoreObject(new VmeType(61L, "VME", "Registered VME (CCAMLR)"));
	}

	private void createInformationSourceTypes() {
		dao.syncStoreObject(new InformationSourceType(1L, "Book", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
		dao.syncStoreObject(new InformationSourceType(2L, "Meeting documents", InformationSourceType.IS_A_MEETING_DOCUMENT));
		dao.syncStoreObject(new InformationSourceType(3L, "Journal", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
		dao.syncStoreObject(new InformationSourceType(4L, "Project", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
		dao.syncStoreObject(new InformationSourceType(6L, "CD-ROM/DVD", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
		dao.syncStoreObject(new InformationSourceType(99L, "Other", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
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
