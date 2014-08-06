package org.fao.fi.vme.msaccess.component;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.VMEsHistory;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.tableextension.HistoryHolder;
import org.junit.Test;

public class HistoryHolderCorrectionTest {

	@Test
	public void testCorrect() {
		HistoryHolderCorrection hhc = new HistoryHolderCorrection();
		ObjectCollection oc = new ObjectCollection();
		oc.setClazz(HistoryHolder.class);

		FisheryAreasHistory fisheryAreasHistory = new FisheryAreasHistory();
		VMEsHistory vmesHistory = new VMEsHistory();

		HistoryHolder hh = new HistoryHolder();
		hh.setFisheryAreasHistory(fisheryAreasHistory);
		hh.setVmesHistory(vmesHistory);

		List<Object> objects = new ArrayList<Object>();
		objects.add(hh);

		oc.setObjectList(objects);
		List<ObjectCollection> objectCollectionList = new ArrayList<ObjectCollection>();
		objectCollectionList.add(oc);

		assertEquals(1, objectCollectionList.size());
		hhc.correct(objectCollectionList);
		assertEquals(2, objectCollectionList.size());

	}
}
