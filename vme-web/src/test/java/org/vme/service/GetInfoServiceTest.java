package org.vme.service;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

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
import org.vme.service.dto.VmeSmResponse;

@RunWith(CdiRunner.class)
@AdditionalClasses({  VmeSearchDaoImpl.class })
@ActivatedAlternatives({ ReferenceDaoImpl.class, FigisTestPersistenceUnitConfiguration.class, FigisDataBaseProducer.class,
	VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class })
public class GetInfoServiceTest {
	
	@Inject
	GetInfoService service;
	
	private Vme vme = VmeMock.generateVme(3);;
	
	@Before
	public void before(){
	}
	
	@Test
	public void testFindInfoStringInt() {
		
		VmeSmResponse vmeSmResponse1 = service.findInfo(vme.getInventoryIdentifier(), 0);
		System.out.println(vmeSmResponse1.toString());
		assertNotNull(vmeSmResponse1.getUuid());
		
	}

}
