package org.vme.dao.sources.vme;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.fao.fi.vme.domain.model.ObjectId;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.junit.Test;

public class Update1nCardinalityTest {

	Update1nCardinality<Profile> u1n = new Update1nCardinality<Profile>();
	DummyEm em = new DummyEm();

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateProfile() {
		Vme vmeDto = VmeMock.create();
		Vme vmeEm = VmeMock.create();

		List<Profile> l = vmeEm.getProfileList();
		for (int i = 0; i < l.size(); i++) {
			Profile g = l.get(i);
			em.persist(g);
			vmeDto.getProfileList().get(i).setId(g.getId());
		}

		assertEquals(4, vmeDto.getProfileList().size());
		assertEquals(vmeDto.getProfileList().size(), vmeDto.getProfileList().size());
		u1n.update(em, vmeEm, vmeDto.getProfileList(), vmeEm.getProfileList());

		// test delete 1 element
		Profile toBeDeleted = vmeDto.getProfileList().get(0);
		vmeDto.getProfileList().remove(toBeDeleted);
		u1n.update(em, vmeEm, vmeDto.getProfileList(), vmeEm.getProfileList());
		assertEquals(3, vmeEm.getProfileList().size());
		assertEquals(toBeDeleted.getId(), ((ObjectId<Long>) em.getRemovedObject()).getId());

		// test change a property
		vmeDto.getProfileList().get(0).setYear(100);
		assertNotEquals(vmeEm.getProfileList().get(0).getYear(), vmeDto.getProfileList().get(0).getYear());
		u1n.update(em, vmeEm, vmeDto.getProfileList(), vmeEm.getProfileList());

		// TODO
		// assertEquals(100, vmeEm.getProfileList().get(0).getYear().intValue());

	}

	private void assertNotEquals(Integer year, Integer year2) {
		// TODO Auto-generated method stub

	}
}
