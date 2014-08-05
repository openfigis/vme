package org.vme.service.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationPk;
import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, FigisTestPersistenceUnitConfiguration.class,
		FigisDataBaseProducer.class, VmeTestPersistenceUnitConfiguration.class,
		VmeDataBaseProducerApplicationScope.class })
public class DtoTranslatorTest {

	@Inject
	private DtoTranslator translator;

	private SpecificMeasureDto smDto;
	private SpecificMeasure sm;
	private Vme vme;
	private VmeObservation vo;
	private VmeObservationPk voPk;
	private static final int YEAR = 2000;
	private MultiLingualStringUtil UTIL = new MultiLingualStringUtil();

	@Inject
	private VmeDao vDao;
	
	@Before
	public void before() {

		vme = VmeMock.create();
		
		sm = new SpecificMeasure();

		sm.setYear(YEAR);
		sm.setVmeSpecificMeasure(UTIL.english("A specific measure for the year " + YEAR));
		sm.setValidityPeriod(ValidityPeriodMock.create(YEAR, YEAR + 1));
		sm.setInformationSource(InformationSourceMock.create());
		sm.setReviewYear(YEAR + 1);
		sm.setVme(vme);

		smDto = new SpecificMeasureDto();
		vDao.saveVme(vme);
		
		vo = new VmeObservation();
		voPk = new VmeObservationPk();
		voPk.setVmeId(vme.getId());
		vo.setId(voPk);
		voPk.setObservationId(1000L);
		voPk.setReportingYear(String.valueOf(YEAR));
		
	}

	@Test
	public void testDoTranslate4Sm() {
		smDto = translator.doTranslate4Sm(sm);
		assertEquals("A specific measure for the year 2000", smDto.getText());
		assertEquals("http://www.rfmo.org", smDto.getSourceURL());
		assertTrue(2000 == smDto.getYear());
	}

	@Test
	public void testDoTranslate4Vme() {
		VmeDto vmeDto = translator.doTranslate4Vme(vme, 2000);
		assertTrue(1 == vmeDto.getVmeId());

	}

}
