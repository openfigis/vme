package org.fao.fi.vme.dao.msaccess;

import java.util.List;

public class Table {

	private Class<?> clazz;
	private List<Object> records;

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public List<Object> getRecords() {
		return records;
	}

	public void setRecords(List<Object> records) {
		this.records = records;
	}

}
