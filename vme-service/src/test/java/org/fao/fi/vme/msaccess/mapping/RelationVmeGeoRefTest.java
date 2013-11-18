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
	public void testCorrectVme() {

		String inventoryIdentifier = "567893423q";
		String geographicFeatureID = "hfsdkfhsefh";

		Vme v1 = new Vme();
		v1.setInventoryIdentifier(inventoryIdentifier);

		Vme v2 = new Vme();
		v2.setInventoryIdentifier(inventoryIdentifier);
		GeoRef g1 = new GeoRef();
		g1.setGeographicFeatureID(geographicFeatureID);
		GeoRef g2 = new GeoRef();
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
		oc.setObjectList(objectList);

		List<ObjectCollection> objectCollectionList = new ArrayList<ObjectCollection>();
		objectCollectionList.add(oc);

		assertEquals(2, objectList.size());
		correction.correct(objectCollectionList);
		assertEquals(1, objectList.size());
		Vme vme = (Vme) objectList.get(0);
		assertEquals(1, vme.getGeoRefList().size());
	}
}
