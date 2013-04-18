package org.fao.fi.vme.domain;

import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class ValidityPeriod {

	protected Date begin;
	protected Date end;

}
