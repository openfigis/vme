package org.fao.fi.vme.msaccess.mapping;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.junit.Test;

public class RelationVmeGeoRefTest {

	RelationVmeGeoRef correction = new RelationVmeGeoRef();

	@Test
	public void testCorrectVmeWithDifferentGeoRef() {
		String geographicFeatureID1 = "1";
		String geographicFeatureID2 = "2";
		List<ObjectCollection> objectCollectionList = create(geographicFeatureID1, geographicFeatureID2);
		correction.correct(objectCollectionList);
		// 1 Vme to be found
		assertEquals(1, objectCollectionList.get(0).getObjectList().size());
		Vme vme = (Vme) objectCollectionList.get(0).getObjectList().get(0);
		// that Vme has 2 GeoRef
		assertEquals(2, vme.getGeoRefList().size());
	}

	@Test
	public void testCorrectVmeWithSameGeoRef() {
		String geographicFeatureID1 = "1";
		String geographicFeatureID2 = "1";
		List<ObjectCollection> objectCollectionList = create(geographicFeatureID1, geographicFeatureID2);
		correction.correct(objectCollectionList);
		// 1 Vme to be found
		assertEquals(1, objectCollectionList.get(0).getObjectList().size());
		Vme vme = (Vme) objectCollectionList.get(0).getObjectList().get(0);
		// that Vme has 1 GeoRef
		assertEquals(1, vme.getGeoRefList().size());
	}

	private List<ObjectCollection> create(String geographicFeatureID1, String geographicFeatureID2) {
		String inventoryIdentifier = "10";
		String[] ids = { geographicFeatureID1, geographicFeatureID2 };
		List<Object> objectList = new ArrayList<Object>();

		for (String geog : ids) {
			Vme v = new Vme();
			v.setInventoryIdentifier(inventoryIdentifier);
			v.setValidityPeriod(ValidityPeriodMock.create());
			GeoRef g1 = new GeoRef();
			g1.setGeographicFeatureID(geog);
			g1.setYear(2000);

			List<GeoRef> list1 = new ArrayList<GeoRef>();
			list1.add(g1);
			v.setGeoRefList(list1);
			objectList.add(v);

		}

		ObjectCollection oc = new ObjectCollection();
		oc.setClazz(Vme.class);

		// 1 vme are added to the list, with 2 GeoRef
		oc.setObjectList(objectList);

		// objectCollectionList has now only vme's
		List<ObjectCollection> objectCollectionList = new ArrayList<ObjectCollection>();
		objectCollectionList.add(oc);
		return objectCollectionList;
	}

}
