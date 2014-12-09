package org.fao.fi.vme.batch.sync2.mapping;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Test;

public class DisseminationYearSliceTest {

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void testEqualsObjectSpecificMeasure() {
		DisseminationYearSlice ds1 = new DisseminationYearSlice();
		DisseminationYearSlice ds2 = new DisseminationYearSlice();

		List<SpecificMeasure> list1 = new ArrayList<SpecificMeasure>();
		List<SpecificMeasure> list2 = new ArrayList<SpecificMeasure>();

		SpecificMeasure s1 = new SpecificMeasure();
		s1.setVmeSpecificMeasure(u.english("1"));
		SpecificMeasure s2 = new SpecificMeasure();
		s2.setVmeSpecificMeasure(u.english("2"));

		list1.add(s1);
		list2.add(s1);

		ds1.setSpecificMeasureList(list1);
		ds2.setSpecificMeasureList(list2);
		assertTrue(ds1.equals(ds2));

		list2.add(s2);
		assertFalse(ds1.equals(ds2));

	}

	@Test
	public void testEqualsObject() {
		DisseminationYearSlice s1 = new DisseminationYearSlice();
		DisseminationYearSlice s2 = new DisseminationYearSlice();

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
