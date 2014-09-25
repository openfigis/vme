package org.vme.service.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

public class VmeResponseTest {

	VmeResponse vmeResponse = new VmeResponse(UUID.randomUUID());
	
	@Test
	public void testVmeResponse() {
		VmeResponse vmeRes = new VmeResponse(UUID.randomUUID());
		assertNotNull(vmeRes);
		assertNotNull(vmeRes.getVmeDto());
	}

	@Test
	public void testGetVmeDto() {
		assertTrue(vmeResponse.getVmeDto().isEmpty());
	}

	@Test
	public void testSetVmeDto() {
		VmeDto vmeDto = new VmeDto();
		List<VmeDto> vmeDtoList = new ArrayList<VmeDto>();
		vmeDtoList.add(vmeDto);
		vmeResponse.setVmeDto(vmeDtoList);
		assertTrue(1 == vmeResponse.getVmeDto().size());
	}

	@Test
	public void testGetUuid() {
		String s = vmeResponse.getUuid().toString();
		assertEquals(s, vmeResponse.getUuid().toString());
	}

	@Test
	public void testSetUuid() {
		UUID u = UUID.randomUUID();
		vmeResponse.setUuid(u);
		assertEquals(u.toString(), vmeResponse.getUuid().toString());
	}

}
