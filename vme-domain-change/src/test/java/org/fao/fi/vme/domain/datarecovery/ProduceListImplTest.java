package org.fao.fi.vme.domain.datarecovery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Map;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.util.Lang;
import org.junit.Test;

public class ProduceListImplTest {

	ProduceList p = new ProduceListImpl();

	@Test
	public void testLoadFromCsv() {
		Map<Long, MultiLingualString> m = p.loadFromCsv();

		Collection<MultiLingualString> c = m.values();
		for (MultiLingualString multiLingualString : c) {
			System.out.println("----------------");
			System.out.println(multiLingualString.getStringMap().get(Lang.EN));

			assertNotNull(multiLingualString.getId());
			assertEquals(1, multiLingualString.getStringMap().size());
		}

		assertEquals(6073, m.size());
	}

}
