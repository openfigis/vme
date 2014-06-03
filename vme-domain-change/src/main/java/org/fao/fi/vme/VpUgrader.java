package org.fao.fi.vme;

import java.util.Calendar;

import org.fao.fi.vme.domain.model.ValidityPeriod;

public class VpUgrader {

	public void upgrade(ValidityPeriod vp) {
		if (vp != null && vp.getBeginYear() != null) {
			Calendar c = Calendar.getInstance();
			c.set(vp.getBeginYear(), 0, 1);
			vp.setBeginDate(c.getTime());
		} else {
			System.out.println("reporting nulls");
		}

		if (vp != null && vp.getEndYear() != null) {
			Calendar c = Calendar.getInstance();
			c.set(vp.getEndYear(), 11, 31);
			vp.setEndDate(c.getTime());
		} else {
			System.out.println("reporting nulls");
		}

	}

}
