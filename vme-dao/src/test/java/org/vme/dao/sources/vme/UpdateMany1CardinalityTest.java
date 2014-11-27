package org.vme.dao.sources.vme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.ObjectId;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.junit.Test;

public class UpdateMany1CardinalityTest {

	UpdateMany1Cardinality u = new UpdateMany1Cardinality();
	DummyEm em = new DummyEm();

	@Test
	public void testUpdate() {
		InformationSource informationSourceManaged = new InformationSource();
		informationSourceManaged.setId(1l);
		em.persist(informationSourceManaged);

		InformationSource informationSourceDto = new InformationSource();
		informationSourceDto.setId(20l);

		SpecificMeasure parentManaged = new SpecificMeasure();
		parentManaged.setId(11l);
		em.persist(parentManaged);

		SpecificMeasure parentDto = new SpecificMeasure();

		List<SpecificMeasure> listManaged = new ArrayList<SpecificMeasure>();
		List<SpecificMeasure> listDto = new ArrayList<SpecificMeasure>();

		informationSourceManaged.setSpecificMeasureList(listManaged);
		informationSourceDto.setSpecificMeasureList(listDto);

		// test 0 to 0
		u.update(em, parentManaged, parentDto, listManaged, listDto);
		assertTrue(parentManaged.getInformationSource() == null);

		// test 0 to 1
		parentDto.setInformationSource(informationSourceDto);
		u.update(em, parentManaged, parentDto, listManaged, listDto);

		delelegateTest1(informationSourceManaged, informationSourceDto, informationSourceDto.getId(),
				informationSourceManaged.getId(), listManaged, parentManaged);

		// test 1 to 0
		parentDto.setInformationSource(null);
		u.update(em, parentManaged, parentDto, listManaged, listDto);
		assertNull(parentManaged.getInformationSource());
		assertEquals(0, informationSourceManaged.getSpecificMeasureList().size());

		// test 1 to 1
		parentDto.setInformationSource(informationSourceDto);
		u.update(em, parentManaged, parentDto, listManaged, listDto);
		parentDto.setInformationSource(informationSourceDto);
		u.update(em, parentManaged, parentDto, listManaged, listDto);
		delelegateTest1(informationSourceManaged, informationSourceDto, informationSourceDto.getId(),
				informationSourceManaged.getId(), listManaged, parentManaged);

		// test 1 to another 1
		InformationSource isAnotherManaged = new InformationSource();
		isAnotherManaged.setId(50l);
		em.persist(isAnotherManaged);
		InformationSource isAnotherDto = new InformationSource();
		isAnotherDto.setId(50l);

		parentDto.setInformationSource(isAnotherDto);
		u.update(em, parentManaged, parentDto, listManaged, listDto);
		delelegateTest1(informationSourceManaged, isAnotherDto, isAnotherDto.getId(), informationSourceManaged.getId(),
				listManaged, parentManaged);

	}

	void delelegateTest1(ObjectId<Long> informationSourceManaged, ObjectId<Long> informationSourceDto,
			Long informationSourceIdDto, Long informationSourceIdManaged, List<SpecificMeasure> listManaged,
			SpecificMeasure parentManaged) {
		assertNotNull(informationSourceManaged);
		assertEquals(informationSourceIdDto, informationSourceIdManaged);
		assertEquals(informationSourceManaged, em.find(informationSourceDto.getClass(), informationSourceDto.getId()));
		assertEquals(1, listManaged.size());
		assertEquals(parentManaged, listManaged.get(0));
	}

}
