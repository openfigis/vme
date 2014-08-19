package org.vme.web.cache;

import java.io.Serializable;

import org.vme.service.search.ObservationsRequest;

public class TestHelperCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Cached
	public TestHelperCache cachedMethod(ObservationsRequest o) {
		System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
		TestHelperCache t = new TestHelperCache();
		System.out.println(t);
		return t;
	}

	public TestHelperCache nonCachedMethod(ObservationsRequest o) {
		TestHelperCache t = new TestHelperCache();
		System.out.println(t);
		return t;
	}

}
