package org.fao.fi.vme.domain.interfaces;

public interface PeriodYear extends Period<PeriodYear> {

	public abstract Integer getYear();

	public abstract void setYear(Integer year);
}
