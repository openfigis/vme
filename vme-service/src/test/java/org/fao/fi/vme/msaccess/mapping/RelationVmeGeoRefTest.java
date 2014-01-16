package org.fao.fi.vme.msaccess.mapping;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.junit.Test;

public class RelationVmeGeoRefTest {

	RelationVmeGeoRef correction = new RelationVmeGeoRef();

	@Test
	public void testCorrectVmeWithDifferentGeoRef() {

		String inventoryIdentifier = "10";
		String geographicFeatureID1 = "1";
		String geographicFeatureID2 = "1";

		Vme v1 = new Vme();
		v1.setInventoryIdentifier(inventoryIdentifier);

		GeoRef g1 = new GeoRef();
		g1.setGeographicFeatureID(geographicFeatureID1);
		g1.setYear(2000);
		GeoRef g2 = new GeoRef();
		g2.setGeographicFeatureID(geographicFeatureID2);
		g2.setYear(2001);

		List<GeoRef> list1 = new ArrayList<GeoRef>();
		list1.add(g1);
		list1.add(g2);
		v1.setGeoRefList(list1);

		List<Object> objectList = new ArrayList<Object>();
		objectList.add(v1);

		ObjectCollection oc = new ObjectCollection();
		oc.setClazz(Vme.class);

		// 1 vme are added to the list, with 2 GeoRef
		oc.setObjectList(objectList);

		// objectCollectionList has now only vme's
		List<ObjectCollection> objectCollectionList = new ArrayList<ObjectCollection>();
		objectCollectionList.add(oc);

		// 2 Vme are in the list, which should be 1 with 1 GeoRef
		assertEquals(2, v1.getGeoRefList().size());
		assertEquals(1, objectList.size());
		correction.correct(objectCollectionList);

		// 1 Vme to be found
		assertEquals(1, objectList.size());
		Vme vme = (Vme) objectList.get(0);
		// that Vme has 2 GeoRef
		assertEquals(2, vme.getGeoRefList().size());
	}

	@Test
	public void testCorrectVme() {

		String inventoryIdentifier = "567893423q";
		String geographicFeatureID = "fjhdksf";

		Vme v1 = new Vme();
		v1.setInventoryIdentifier(inventoryIdentifier);

		Vme v2 = new Vme();
		v2.setInventoryIdentifier(inventoryIdentifier);

		GeoRef g1 = new GeoRef();
		g1.setYear(2000);
		g1.setGeographicFeatureID(geographicFeatureID);

		GeoRef g2 = new GeoRef();
		g2.setYear(2001);
		g2.setGeographicFeatureID(geographicFeatureID);

		List<GeoRef> list1 = new ArrayList<GeoRef>();
		list1.add(g1);
		v1.setGeoRefList(list1);

		List<GeoRef> list2 = new ArrayList<GeoRef>();
		list2.add(g2);
		v2.setGeoRefList(list2);

		List<Object> objectList = new ArrayList<Object>();
		objectList.add(v1);
		objectList.add(v2);

		ObjectCollection oc = new ObjectCollection();
		oc.setClazz(Vme.class);

		// 2 vme are added to the list, each one with another GeoRef
		oc.setObjectList(objectList);

		// objectCollectionList has now only vme's
		List<ObjectCollection> objectCollectionList = new ArrayList<ObjectCollection>();
		objectCollectionList.add(oc);

		// 2 Vme are in the list, which should be 1 with 2 GeoRefs
		assertEquals(2, objectList.size());
		correction.correct(objectCollectionList);

		// 1 Vme to be found
		assertEquals(1, objectList.size());
		Vme vme = (Vme) objectList.get(0);
		// that Vme has 1 GeoRef
		assertEquals(2, vme.getGeoRefList().size());
	}
}
