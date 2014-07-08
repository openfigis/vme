package org.vme.service.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Before;
import org.junit.Test;

public class DtoTranslatorTest {

	private DtoTranslator translator = new DtoTranslator();
	
	private SpecificMeasureDto smDto;
	private SpecificMeasure sm;
	private static final int YEAR = 2000;
	private MultiLingualStringUtil UTIL = new MultiLingualStringUtil();
	
	@Before
	public void before(){
		
		sm = new SpecificMeasure();

		sm.setYear(YEAR);
		sm.setVmeSpecificMeasure(UTIL.english("A specific measure for the year " + YEAR));
		sm.setValidityPeriod(ValidityPeriodMock.create(YEAR, YEAR + 1));
		sm.setInformationSource(InformationSourceMock.create());
		sm.setReviewYear(YEAR + 1);
		sm.setVme(VmeMock.create());
		
		smDto = new SpecificMeasureDto();
		
	}
	
	@Test
	public void testDoTranslate4Sm() {
		smDto = translator.doTranslate4Sm(sm);
		assertEquals("A specific measure for the year 2000", smDto.getText());
		assertTrue(2001 == smDto.getValidityPeriodEnd());
		assertTrue(2000 == smDto.getValidityPeriodStart());
		assertEquals("http://www.rfmo.org", smDto.getSourceURL());
		assertTrue(2000 == smDto.getYear());
	}

//	TODO ("Implement this test")
	@Test
	public void testDoTranslate4Vme() {
		fail("Not yet implemented");
	}

}
