/**
 * 
 */
package org.fao.fi.vme.batch.reference;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.InformationSourceType;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.VmeCriteria;
import org.fao.fi.vme.domain.model.VmeType;
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
		// createYears();

		synchAuthorityWithRfmo();
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

		dao.syncStoreObject(new Authority(20010l, "CCAMLR",
				"Commission for the Conservation of Antarctic Marine Living Resources"));
		dao.syncStoreObject(new Authority(24561l, "GFCM", "General Fishery Commission for the Mediterranean sea"));
		dao.syncStoreObject(new Authority(20220l, "NAFO", "Northwest Atlantic Fisheries Organization"));
		dao.syncStoreObject(new Authority(21580l, "NEAFC", "North East Atlantic Fisheries Commission"));
		dao.syncStoreObject(new Authority(22140l, "SEAFO", "South East Atlantic Fisheries Organisation"));
		dao.syncStoreObject(new Authority(22150l, "WECAFC", "Western Central Atlantic Fishery Commission"));
		dao.syncStoreObject(new Authority(24558l, "SPRFMO", "South Pacific Regional Fisheries Management Organisation"));

		// repAuthority.put((long)90010, new
		// Authority(22140,"SIODFA","Southern Indian Ocean Deepsea Fishers' Association");
	}

	private void createVmeCriterias() {
		dao.syncStoreObject(new VmeCriteria(10l, "Uniqueness or rarity"));
		dao.syncStoreObject(new VmeCriteria(20l, "Functional significance of the habitat"));
		dao.syncStoreObject(new VmeCriteria(30l, "Fragility"));
		dao.syncStoreObject(new VmeCriteria(40l, "Life-history traits"));
		dao.syncStoreObject(new VmeCriteria(50l, "Structural complexity"));
		dao.syncStoreObject(new VmeCriteria(60l, "Unspecified"));
	}

	private void createVmeTypes() {
		dao.syncStoreObject(new VmeType(10l, "VME"));
		dao.syncStoreObject(new VmeType(20l, "Risk area"));
		dao.syncStoreObject(new VmeType(30l, "Other types of closed/restricted area"));
	}

	private void createInformationSourceTypes() {
		dao.syncStoreObject(new InformationSourceType(1l, "Book", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
		dao.syncStoreObject(new InformationSourceType(2l, "Meeting documents",
				InformationSourceType.IS_A_MEETING_DOCUMENT));
		dao.syncStoreObject(new InformationSourceType(3l, "Journal", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
		dao.syncStoreObject(new InformationSourceType(4l, "Project", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
		dao.syncStoreObject(new InformationSourceType(6l, "CD-ROM/DVD", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
		dao.syncStoreObject(new InformationSourceType(99l, "Other", InformationSourceType.IS_NOT_A_MEETING_DOCUMENT));
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
