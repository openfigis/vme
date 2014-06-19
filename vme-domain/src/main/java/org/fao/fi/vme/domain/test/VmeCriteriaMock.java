package org.fao.fi.vme.domain.test;

import org.fao.fi.vme.domain.model.reference.VmeCriteria;

public class VmeCriteriaMock {

	public static VmeCriteria create() {

		return new VmeCriteria(40L, "Life-history traits");
	}
	
	public static VmeCriteria createAnother() {

		return new VmeCriteria(50L, "Structural complexity");
	}

	public static VmeCriteria createYetAnother() {

		return new VmeCriteria(30L, "Fragility");
	}
}
