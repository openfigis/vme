/**
 * (c) 2013 FAO / UN (project: vme-reports-store-gateway-test)
 */
package org.vme.test.mock;

import org.fao.fi.vme.domain.model.extended.VMEsHistory;

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
public class VMEsHistoryMocker extends AbstractMocker {
	static public VMEsHistory getMock1() {
		VMEsHistory mock = new VMEsHistory();
		mock.setId(1L);
		mock.setYear(2013);
		mock.setHistory(MLSu.english("Lupus in fabula"));
		
		return mock;
	}
	
	static public VMEsHistory getMock2() {
		VMEsHistory mock = new VMEsHistory();
		mock.setId(2L);
		mock.setYear(2002);
		mock.setHistory(MLSu.english("Tertium non datur"));
		
		return mock;
	}
	
	static public VMEsHistory getMock3() {
		VMEsHistory mock = new VMEsHistory();
		mock.setId(3L);
		mock.setYear(2005);
		mock.setHistory(MLSu.english("O tepora, o mores!"));
		
		return mock;
	}}
