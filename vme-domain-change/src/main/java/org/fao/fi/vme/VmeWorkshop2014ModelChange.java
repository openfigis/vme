package org.fao.fi.vme;

import javax.inject.Inject;

public class VmeWorkshop2014ModelChange {

	@Inject
	private CriteriaMigrationElement criteriaMigrationElement;

	public void migrate() {
		criteriaMigrationElement.migrate();

	}

}
