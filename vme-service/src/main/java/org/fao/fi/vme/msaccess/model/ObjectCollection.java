package org.fao.fi.vme.msaccess.model;

import java.util.IdentityHashMap;
import java.util.Map;

public class ObjectCollection extends Collection {

	private final static Map<Object, Object> domainTableMap = new IdentityHashMap<Object, Object>();

	public Map<Object, Object> getDomainTableMap() {
		return domainTableMap;
	}

}
