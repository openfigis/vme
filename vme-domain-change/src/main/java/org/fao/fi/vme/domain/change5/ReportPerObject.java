package org.fao.fi.vme.domain.change5;

import java.util.ArrayList;
import java.util.List;

public class ReportPerObject {

	private Class<?> clazz;
	private Long id;
	private List<Long> ids = new ArrayList<Long>();

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

}
