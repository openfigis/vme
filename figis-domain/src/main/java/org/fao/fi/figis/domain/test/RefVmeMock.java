package org.fao.fi.figis.domain.test;

import org.fao.fi.figis.domain.RefVme;

public class RefVmeMock {
	
	private RefVmeMock(){
		
	}

	public static RefVme create() {

		RefVme r = new RefVme();
		r.setId(456132L);
		r.setMeta(456);
		return r;

	}
}
