package org.vme.service;

import static org.junit.Assert.assertNotNull;

import java.io.StringWriter;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.webservice.SpecificMeasureList;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.service.dto.VmeSmResponse;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, FigisTestPersistenceUnitConfiguration.class, FigisDataBaseProducer.class,
	VmeTestPersistenceUnitConfiguration.class, VmeDataBaseProducerApplicationScope.class })
public class GetInfoServiceTest {
	
	@Inject
	private GetInfoService service;
	
	@Inject
	private VmeDao vDao;
	
	private Vme vme;
	private MultiLingualStringUtil UTIL = new MultiLingualStringUtil();
	private Logger log = (Logger) LoggerFactory.getLogger(GetInfoServiceTest.class);
	
	@Before
	public void before(){
		vme = VmeMock.generateVme(3);
		vDao.saveVme(vme);
		
	}
	
	@Test
	public void vmeIdentifier2SpecificMeasureTest() {
		
		VmeSmResponse vmeSmResponse1 = service.vmeIdentifier2SpecificMeasure(vme.getInventoryIdentifier(), 0);
		System.out.println();
		System.out.println(vmeSmResponse1.toString());
		System.out.println();
		assertNotNull(vmeSmResponse1.getUuid());
		
	}
	
	/*
	 * 	29/07/2014 Roberto`s Note:
	 * 	This test simulate the behaviour for the Xml service for a SpecificMeasure whit more languages and replies only the English as expected.
	 */
	
	@Test
	public void xmlResponseForMultiLingualSpecificMeasureTest(){
		for (SpecificMeasure sm : vme.getSpecificMeasureList()) {
			UTIL.addFrench(sm.getVmeSpecificMeasure(), "Foo French Specific Measure");
		}
		
		SpecificMeasureList myBean = service.vmeIdentifier2SpecificmeasureXML(vme.getInventoryIdentifier(), 0);

		JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance(SpecificMeasureList.class);
		} catch (JAXBException e1) {
			log.error("JAXB Exception when creating new Istance for SpecificMeasureList");
		}
		StringWriter writer = new StringWriter();
		try {
			jaxbContext.createMarshaller().marshal(myBean, writer);
		} catch (JAXBException e) {
			log.error("JAXB Exception when creating marshaller and then in marshalling");
		}
		
		String xmlString = writer.toString();
		System.out.println();
		System.out.println(xmlString);
		System.out.println();
		
	}

}
