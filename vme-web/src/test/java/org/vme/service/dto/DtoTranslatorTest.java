package org.vme.service.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationPk;
import org.fao.fi.vme.domain.model.GeneralMeasure;
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
	private GeneralMeasureDto gmDto = new GeneralMeasureDto();
	private Vme vme;
	private VmeObservation vo;
	private VmeObservationPk voPk;
	
	private static final int YEAR = 2000;
	private static final MultiLingualStringUtil UTIL = new MultiLingualStringUtil();

	@Inject
	private VmeDao vDao;
	
	@Before
	public void before() {

		vme = VmeMock.generateVme(3);
		for (GeneralMeasure gm : vme.getRfmo().getGeneralMeasureList()) {
			gm.setRfmo(vme.getRfmo());
		}
		List<Vme> vmeList = new ArrayList<Vme>();
		vmeList.add(vme);
		vme.getRfmo().setListOfManagedVmes(vmeList);
		
		
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
		assertEquals("Southern Pacific Ocean", vmeDto.getGeoArea());
		assertEquals("VME_RFMO_1_2000", vmeDto.getgeographicFeatureId());
		assertEquals("VME_RFMO_1", vmeDto.getInventoryIdentifier());
		assertEquals("Hard Corner Bugs ", vmeDto.getLocalName());
		assertTrue(2002 == vmeDto.getYear());

	}
	
	@Test
	public void testDoTranslate4Gm() {
		gmDto = translator.doTranslate4Gm(vme.getRfmo().getGeneralMeasureList().get(0));
		assertEquals("an [ExploratoryFishingProtocol] general measure", gmDto.getExploratoryFishingProtocol());
		assertEquals("a [FishingArea] general measure", gmDto.getFishingArea());
		assertEquals("a [VmeThreshold] general measure", gmDto.getThreshold());
		assertEquals("a [VmeEncounterProtocols] general measure", gmDto.getVmeEncounterProtocol());
		assertEquals("a [VmeIndicatorSpecies] general measure", gmDto.getVmeIndicatorSpecies());
		assertTrue(2000 == gmDto.getYear());
		assertTrue(2000 == gmDto.getValidityPeriodStart());
		assertTrue(2000 == gmDto.getValidityPeriodEnd());
	}

}
