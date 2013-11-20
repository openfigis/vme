/**
 * (c) 2013 FAO / UN (project: vme-domain)
 */
package org.fao.fi.vme.domain.model.extended;

import javax.persistence.Entity;

import org.fao.fi.vme.domain.model.History;
import org.gcube.application.rsg.support.annotations.RSGReferenceReport;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 13/nov/2013   Fabio     Creation.
 *
 * @version 1.0
 * @since 13/nov/2013
 */
@RSGReferenceReport(name="Fishery Areas History")
@Entity(name = "HISTORY")
public class FisheryAreasHistory extends History {

	/**
	 * Class constructor
	 *
	 */
	public FisheryAreasHistory() {
	}
}
