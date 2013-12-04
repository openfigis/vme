package org.fao.fi.vme.msaccess.model;

import java.util.HashMap;
import java.util.Map;


public class ObjectCollection extends Collection {

	private final static Map<String, Object> domainTableMap = new HashMap<String, Object>();

	public Map<String, Object> getDomainTableMap() {
		return domainTableMap;
	}

}
