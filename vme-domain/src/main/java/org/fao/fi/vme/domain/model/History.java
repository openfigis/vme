package org.fao.fi.vme.domain.model;

public interface History {
	Long getId();

	void setId(Long id);

	Integer getYear();

	void setYear(Integer year);

	MultiLingualString getHistory();

	void setHistory(MultiLingualString history);

	public boolean equals(Object obj);

}