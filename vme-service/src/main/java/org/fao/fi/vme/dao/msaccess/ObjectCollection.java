package org.fao.fi.vme.dao.msaccess;

import java.util.HashMap;
import java.util.Map;

public class ObjectCollection extends Collection {

	private final Map<Object, Object> domainTableMap = new HashMap<Object, Object>();

	public Map<Object, Object> getDomainTableMap() {
		return domainTableMap;
	}

}
