package org.fao.fi.vme;

import javax.inject.Inject;

public class VmeWorkshop2014ModelChange {

	@Inject
	private CriteriaMigrationElement criteriaMigrationElement;

	@Inject
	private ValidityPeriodMigrationElement validityPeriodMigrationElement;

	public void migrate() {
		criteriaMigrationElement.migrate();
		validityPeriodMigrationElement.migrate();
	}

}
