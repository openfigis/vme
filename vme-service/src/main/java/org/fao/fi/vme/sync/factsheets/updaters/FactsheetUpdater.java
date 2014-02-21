/**
 * (c) 2014 FAO / UN (project: vme-service)
 */
package org.fao.fi.vme.sync.factsheets.updaters;


/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 20 Feb 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 20 Feb 2014
 */
public interface FactsheetUpdater {
	void createFactsheets(Long... vmeIDs) throws Exception;
	void updateFactsheets(Long... vmeIDs) throws Exception;
	void deleteFactsheets(Long... vmeIDs) throws Exception;
}