package org.fao.fi.vme.msaccess.component;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.junit.Test;

public class IdCorrectionTest {

	IdCorrection idCorrection = new IdCorrection();

	@Test
	public void testCorrect() {

		Vme vme = new Vme();
		vme.setId(456l);
		ObjectCollection oc = new ObjectCollection();
		List<Object> objectList = new ArrayList<Object>();
		oc.setObjectList(objectList);
		oc.getObjectList().add(vme);
		List<ObjectCollection> objectCollectionList = new ArrayList<ObjectCollection>();
		objectCollectionList.add(oc);
		idCorrection.correct(objectCollectionList);
		assertNull(vme.getId());

	}
}
