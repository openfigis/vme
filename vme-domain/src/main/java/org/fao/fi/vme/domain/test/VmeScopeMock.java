package org.fao.fi.vme.domain.test;

import org.fao.fi.vme.domain.model.reference.VmeScope;

public class VmeScopeMock {
	
	private VmeScopeMock(){}

	public static VmeScope create() {
		VmeScope vmeScope = new VmeScope();
		vmeScope.setId(VmeMock.VME_SCOPE);
		vmeScope.setCodeSystem("vme");
		vmeScope.setName("VME");

		return vmeScope;
	}

}
