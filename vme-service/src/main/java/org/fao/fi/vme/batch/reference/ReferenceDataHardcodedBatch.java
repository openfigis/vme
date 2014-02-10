/**
 * 
 */
package org.fao.fi.vme.batch.reference;

import java.util.Map;

import javax.inject.Inject;

import org.fao.fi.vme.domain.dto.ref.ReferenceYear;
import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.VmeCriteria;
import org.fao.fi.vme.domain.model.VmeType;
import org.vme.service.dao.sources.vme.VmeDao;

/**
 * @author Fabrizio Sibeni
 * 
 */
public class ReferenceDataHardcodedBatch {

	@Inject
	private VmeDao vmeDao;

	private Map<Long, Authority> repAuthority;
	private Map<Long, VmeCriteria> repVmeCriteria;
	private Map<Long, VmeType> repVmeType;
	private Map<Long, ReferenceYear> repYear;

	public void run() {

	}

	private void createAuthorities() {
		repAuthority.put((long) 20010, new Authority(20010, "CCAMLR",
				"Commission for the Conservation of Antarctic Marine Living Resources"));
		repAuthority.put((long) 22080, new Authority(22080, "GFCM",
				"General Fishery Commission for the Mediterranean sea"));
		repAuthority.put((long) 20220, new Authority(20220, "NAFO", "Northwest Atlantic Fisheries Organization"));
		repAuthority.put((long) 21580, new Authority(21580, "NEAFC", "North East Atlantic Fisheries Commission"));
		repAuthority.put((long) 22140, new Authority(22140, "SEAFO", "South East Atlantic Fisheries Organisation"));
		// repAuthority.put((long)90010, new
		// Authority(22140,"SIODFA","Southern Indian Ocean Deepsea Fishers' Association"));
	}

	private void createVmeCriterias() {
		repVmeCriteria.put((long) 10, new VmeCriteria(10, "Uniqueness or rarity"));
		repVmeCriteria.put((long) 20, new VmeCriteria(20, "Functional significance of the habitat"));
		repVmeCriteria.put((long) 30, new VmeCriteria(30, "Fragility"));
		repVmeCriteria.put((long) 40, new VmeCriteria(40, "Life-history traits"));
		repVmeCriteria.put((long) 50, new VmeCriteria(50, "Structural complexity"));
		repVmeCriteria.put((long) 60, new VmeCriteria(60, "Unspecified"));
	}

	private void createVmeTypes() {
		repVmeType.put((long) 10, new VmeType(10, "VME"));
		repVmeType.put((long) 20, new VmeType(20, "Risk area"));
		repVmeType.put((long) 30, new VmeType(30, "Other types of closed/restricted area"));
	}

	private void createYears() {
		repYear.put((long) 2013, new ReferenceYear(2013));
		repYear.put((long) 2012, new ReferenceYear(2012));
		repYear.put((long) 2011, new ReferenceYear(2011));
		repYear.put((long) 2010, new ReferenceYear(2010));
		repYear.put((long) 2009, new ReferenceYear(2009));
		repYear.put((long) 2008, new ReferenceYear(2008));
		repYear.put((long) 2007, new ReferenceYear(2007));
		repYear.put((long) 2006, new ReferenceYear(2006));
	}

}
