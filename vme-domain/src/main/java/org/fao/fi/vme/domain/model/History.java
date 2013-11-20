package org.fao.fi.vme.domain.model;

public interface History {

	public abstract Long getId();

	public abstract void setId(Long id);

	public abstract Integer getYear();

	public abstract void setYear(Integer year);

	public abstract MultiLingualString getHistory();

	public abstract void setHistory(MultiLingualString history);

}