package org.fao.fi.vme;

import javax.inject.Inject;

public class VmeWorkshop2014ModelChange {

	@Inject
	private CriteriaMigrationElement criteriaMigrationElement;

	@Inject
	private ValidityPeriodMigrationElement validityPeriodMigrationElement;

	public void migrate() {
		System.out.println("VmeWorkshop2014ModelChange - start");

		System.out.println("VmeWorkshop2014ModelChange - start criteriaMigrationElement");
		criteriaMigrationElement.migrate();
		System.out.println("VmeWorkshop2014ModelChange - done criteriaMigrationElement");

		System.out.println("VmeWorkshop2014ModelChange - start validityPeriodMigrationElement");
		validityPeriodMigrationElement.migrate();
		System.out.println("VmeWorkshop2014ModelChange - done validityPeriodMigrationElement");

		System.out.println("VmeWorkshop2014ModelChange - done");

	}

}
