package org.vme.service.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;

public class VmeSmResponseTest {
	
	VmeSmResponse vmeSmResp = new VmeSmResponse(UUID.randomUUID());
	
	@Test
	public void testVmeSmResponse() {
		VmeSmResponse vmeSmResp1 = new VmeSmResponse(UUID.randomUUID());
		assertNotNull(vmeSmResp1);
		assertNotNull(vmeSmResp1.getResponseList());
	}

	@Test
	public void testGetUuid() {
		String s = vmeSmResp.getUuid().toString();
		assertEquals(s, vmeSmResp.getUuid());
	}

	@Test
	public void testGetResponseList() {
		assertTrue(vmeSmResp.getResponseList().isEmpty());
	}

	@Test
	public void testSetResponseList() {
		SpecificMeasureDto sm = new SpecificMeasureDto();
		ArrayList<SpecificMeasureDto> smList = new ArrayList<SpecificMeasureDto>();
		smList.add(sm);
		vmeSmResp.setResponseList(smList);
		assertTrue(1 == vmeSmResp.getResponseList().size());
	}

	@Test
	public void testGetVmeId() {
		vmeSmResp.setVmeId(1000L);
		assertTrue(1000 == vmeSmResp.getVmeId());
	}

	@Test
	public void testSetVmeId() {
		vmeSmResp.setVmeId(1000L);
		assertTrue(1000 == vmeSmResp.getVmeId());
	}

	@Test
	public void testGetLocalName() {
		vmeSmResp.setLocalName("Foo name");
		assertEquals("Foo name", vmeSmResp.getLocalName());
	}

	@Test
	public void testSetLocalName() {
		vmeSmResp.setLocalName("Foo name");
		assertEquals("Foo name", vmeSmResp.getLocalName());
	}

	@Test
	public void testGetInventoryIdentifier() {
		vmeSmResp.setInventoryIdentifier("FOO_INV_ID");
		assertEquals("FOO_INV_ID", vmeSmResp.getInventoryIdentifier());
	}

	@Test
	public void testSetInventoryIdentifier() {
		vmeSmResp.setInventoryIdentifier("FOO_INV_ID");
		assertEquals("FOO_INV_ID", vmeSmResp.getInventoryIdentifier());
	}

	@Test
	public void testGetGeoArea() {
		vmeSmResp.setGeoArea("FOO_GeoArea");
		assertEquals("FOO_GeoArea", vmeSmResp.getGeoArea());
	}

	@Test
	public void testSetGeoArea() {
		vmeSmResp.setGeoArea("FOO_GeoArea");
		assertEquals("FOO_GeoArea", vmeSmResp.getGeoArea());
	}

	@Test
	public void testGetOwner() {
		vmeSmResp.setOwner("FOO_Owner");
		assertEquals("FOO_Owner", vmeSmResp.getOwner());
	}

	@Test
	public void testSetOwner() {
		vmeSmResp.setOwner("FOO_Owner");
		assertEquals("FOO_Owner", vmeSmResp.getOwner());
	}

	@Test
	public void testGetVmeType() {
		vmeSmResp.setVmeType("FOO_type");
		assertEquals("FOO_type", vmeSmResp.getVmeType());
	}

	@Test
	public void testSetVmeType() {
		vmeSmResp.setVmeType("FOO_type");
		assertEquals("FOO_type", vmeSmResp.getVmeType());
	}

}
