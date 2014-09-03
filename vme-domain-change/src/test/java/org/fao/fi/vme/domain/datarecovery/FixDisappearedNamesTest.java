package org.fao.fi.vme.domain.datarecovery;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class, ProduceTestList.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class FixDisappearedNamesTest {

	@Inject
	VmeDao vmeDao;

	@Inject
	ProduceList produceList;

	@Inject
	FixDisappearedNames f;

	@Test
	public void testFix() {
		assertEquals(0, vmeDao.count(MultiLingualString.class).intValue());

		vmeDao.persist(new MultiLingualString());
		vmeDao.persist(new MultiLingualString());

		f.fix();

		List<MultiLingualString> l = vmeDao.loadObjects(MultiLingualString.class);
		int fixed = 0;
		for (MultiLingualString m : l) {
			if (m.getStringMap() != null) {
				fixed++;
				assertEquals(1, m.getStringMap().size());
			}
		}
		assertEquals(2, fixed);

	}
}
