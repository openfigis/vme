/**
 * (c) 2013 FAO / UN (project: vme-reports-store-gateway-test)
 */
package org.vme.test.mock;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 25/nov/2013   Fabio     Creation.
 *
 * @version 1.0
 * @since 25/nov/2013
 */
public class FisheryAreasHistoryMocker extends AbstractMocker {
	static public FisheryAreasHistory getMock1() {
		FisheryAreasHistory mock = new FisheryAreasHistory();
		mock.setId(1L);
		mock.setYear(1999);
		mock.setHistory(MLSu.english("Semel in anno licet insanire"));
		
		return mock;
	}
	
	static public FisheryAreasHistory getMock2() {
		FisheryAreasHistory mock = new FisheryAreasHistory();
		mock.setId(2L);
		mock.setYear(2009);
		mock.setHistory(MLSu.english("Si vis pacem para bellum"));
		
		return mock;
	}
	
	static public FisheryAreasHistory getMock3() {
		FisheryAreasHistory mock = new FisheryAreasHistory();
		mock.setId(3L);
		mock.setYear(2010);
		mock.setHistory(MLSu.english("Alea iacta est"));
		
		return mock;
	}
}
