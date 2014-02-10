package org.fao.fi.vme.batch.sync2.mapping;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.batch.sync2.mapping.DisseminationYearSlice;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.test.GeneralMeasureMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Test;

public class DisseminationYearSliceTest {

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void testEqualsObject() {
		DisseminationYearSlice s1 = new DisseminationYearSlice();
		DisseminationYearSlice s2 = new DisseminationYearSlice();

		s1.setGeneralMeasure(GeneralMeasureMock.create());
		s2.setGeneralMeasure(GeneralMeasureMock.create());
		assertTrue(s1.equals(s2));

		s1.getGeneralMeasure().setFishingArea(u.english("ghjg"));
		assertFalse(s1.equals(s2));

		s1.setGeneralMeasure(GeneralMeasureMock.create());
		assertTrue(s1.equals(s2));

		InformationSource is1 = new InformationSource();
		List<InformationSource> isList = new ArrayList<InformationSource>();
		isList.add(is1);
		s1.setInformationSourceList(isList);

		InformationSource is2 = new InformationSource();
		is2.setPublicationYear(5);
		List<InformationSource> isList2 = new ArrayList<InformationSource>();
		isList2.add(is2);
		s2.setInformationSourceList(isList2);

		assertFalse(s1.equals(s2));

	}

}
