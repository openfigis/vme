package org.vme.dao.sources.vme;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.ObjectId;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.junit.Test;

public class Update1nCardinalityTest {

	Update1nCardinality<GeoRef> u1n = new Update1nCardinality<GeoRef>();
	DummyEm em = new DummyEm();

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateGeoRef() {
		Vme vmeDto = VmeMock.create();
		Vme vmeEm = VmeMock.create();

		List<GeoRef> l = vmeEm.getGeoRefList();
		for (int i = 0; i < l.size(); i++) {
			GeoRef g = l.get(i);
			em.persist(g);
			vmeDto.getGeoRefList().get(i).setId(g.getId());
		}

		assertEquals(4, vmeDto.getGeoRefList().size());
		assertEquals(vmeDto.getGeoRefList().size(), vmeDto.getGeoRefList().size());
		// u1n.update(em, vmeEm, vmeDto.getGeoRefList(), vmeEm.getGeoRefList());

		// test delete 1 element
		GeoRef toBeDeleted = vmeDto.getGeoRefList().get(0);
		vmeDto.getGeoRefList().remove(toBeDeleted);
		u1n.update(em, vmeEm, vmeDto.getGeoRefList(), vmeEm.getGeoRefList());
		assertEquals(3, vmeEm.getGeoRefList().size());

		assertEquals(toBeDeleted.getId(), ((ObjectId<Long>) em.getRemovedObject()).getId());

	}
}
