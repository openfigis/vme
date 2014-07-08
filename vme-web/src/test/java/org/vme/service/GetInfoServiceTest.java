package org.vme.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.impl.jpa.VmeSearchDaoImpl;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.service.dto.DtoTranslator;
import org.vme.service.dto.SpecificMeasureDto;

@RunWith(CdiRunner.class)
@AdditionalClasses({ ReferenceDaoImpl.class, VmeSearchDaoImpl.class })
@ActivatedAlternatives({ FigisTestPersistenceUnitConfiguration.class, FigisDataBaseProducer.class,
		VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class })
public class GetInfoServiceTest {

	@Inject
	private VmeDao vDao;
	
	@Inject
	private DtoTranslator translator;
	
	private Vme vme;
	
	@Before
	public void before(){
		vme = VmeMock.generateVme(3);
		vDao.saveVme(vme);
	}
	
	@Test
	public void testFindInfoStringInt() {
		
		List<SpecificMeasureDto> resultList = new ArrayList<SpecificMeasureDto>();

		VmeDto vmeDto = new VmeDto();
		vmeDto.setInventoryIdentifier(vDao.findVme(vme.getId()).getInventoryIdentifier());
		vmeDto.setVmeId(vme.getId());

		
		for (SpecificMeasure sm : vDao.findVme(vmeDto.getVmeId()).getSpecificMeasureList()) {
			if(sm.getYear() == 2000){
			resultList.add(translator.doTranslate4Sm(sm));
			}
		}
		
		assertTrue(vme.getSpecificMeasureList().size() != resultList.size());
		assertTrue(3 == vme.getSpecificMeasureList().size());
		assertTrue(1 == resultList.size());
	}

}
