package org.vme.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.service.dto.VmeDto;
import org.vme.service.search.ObservationsRequest;
import org.vme.service.search.ServiceResponse;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoImpl.class, VmePersistenceUnitConfiguration.class,
		FigisPersistenceUnitConfiguration.class })
@AdditionalClasses({ FigisDataBaseProducer.class, VmeDataBaseProducerApplicationScope.class })
public class SearchServiceTest {

	@Inject
	private VmeDao vDao;

	@Inject
	private SearchService service;

	private Map<Integer, ObservationsRequest> obsRequestList;
	private ServiceResponse<VmeDto> response;
	private Vme vme;
	private Vme vme2;

	@Before
	public void before() {
		obsRequestList = new HashMap<Integer, ObservationsRequest>();

		vme = VmeMock.generateVme(3);
		vme2 = VmeMock.generateVme(5);
		vme2.setAreaType(20L);
		vme2.setCriteria(new ArrayList<Long>());
		vDao.saveVme(vme);
		vDao.saveVme(vme2);

		/*
		 * 1st case: id not null
		 */

		ObservationsRequest request1 = new ObservationsRequest(UUID.randomUUID());
		request1.setId(1);

		obsRequestList.put(1, request1);

		/*
		 * 2nd case: id null
		 */

		ObservationsRequest request2 = new ObservationsRequest(UUID.randomUUID());

		obsRequestList.put(2, request2);

		/*
		 * 3rd case: id not null and year not null
		 */

		ObservationsRequest request3 = new ObservationsRequest(UUID.randomUUID());
		request3.setId(1);
		request3.setYear(2000);

		obsRequestList.put(3, request3);

		/*
		 * 4th case: wrong id request
		 */

		ObservationsRequest request4 = new ObservationsRequest(UUID.randomUUID());
		request4.setId(3);

		obsRequestList.put(4, request4);

		/*
		 * 5th case: inv_id not null
		 */

		ObservationsRequest request5 = new ObservationsRequest(UUID.randomUUID());
		request5.setInventoryIdentifier("VME_RFMO_1");

		obsRequestList.put(5, request5);

		/*
		 * 6th case: wrong inv_id
		 */

		ObservationsRequest request6 = new ObservationsRequest(UUID.randomUUID());
		request6.setInventoryIdentifier("Foo Inv Id");

		obsRequestList.put(6, request6);

		/*
		 * 7th case: geoArea not null
		 */

		ObservationsRequest request7 = new ObservationsRequest(UUID.randomUUID());
		request7.setGeographicFeatureId("VME_RFMO_1_2000");

		obsRequestList.put(7, request7);

		/*
		 * 8th case: wrong geoArea
		 */

		ObservationsRequest request8 = new ObservationsRequest(UUID.randomUUID());
		request8.setGeographicFeatureId("Foo Geo Id");

		obsRequestList.put(8, request8);

		/*
		 * TODO (solve ReferenceDaoMock in order to implement this part of test
		 * which involve ReferenceDAO)
		 * 
		 * Note: Commented out the case to solve
		 */

		// /*
		// * 9th case: authority
		// */
		//
		// ObservationsRequest request8 = new
		// ObservationsRequest(UUID.randomUUID());
		// request8.setAuthority(1001);
		//
		// obsRequestList.add(request8);
		//
		// /*
		// * 10th case: authority + type
		// */
		//
		// request8.setAuthority(1001);
		//
		// /*
		// * 11th case: authority + criteria
		// */
		//
		// request8.setAuthority(1001);
		//
		// /*
		// * 12th case: authority + text
		// */
		//
		// request8.setAuthority(1001);

		/*
		 * 13th case: type
		 */

		ObservationsRequest request13 = new ObservationsRequest(UUID.randomUUID());
		request13.setType(150);

		obsRequestList.put(13, request13);

		/*
		 * 14th case: type + criteria
		 */

		ObservationsRequest request14 = new ObservationsRequest(UUID.randomUUID());
		request14.setType(150);
		request14.setCriteria(40);

		obsRequestList.put(14, request14);

		/*
		 * 15th case: type + text
		 */

		ObservationsRequest request15 = new ObservationsRequest(UUID.randomUUID());
		request15.setType(150);
		request15.setText("VME");

		obsRequestList.put(15, request15);

		/*
		 * 16th case: criteria
		 */

		ObservationsRequest request16 = new ObservationsRequest(UUID.randomUUID());
		request16.setCriteria(40);

		obsRequestList.put(16, request16);

		/*
		 * 17th case: criteria + text
		 */

		ObservationsRequest request17 = new ObservationsRequest(UUID.randomUUID());
		request17.setCriteria(40);
		request17.setText("VME");

		obsRequestList.put(17, request17);

		/*
		 * 18th case: text
		 */

		ObservationsRequest request18 = new ObservationsRequest(UUID.randomUUID());
		request18.setText("VME");

		obsRequestList.put(18, request18);

		// /*
		// * 19th case: authority + type + criteria
		// */
		//
		// request8.setAuthority(1001);
		//
		// /*
		// * 20th case: authority + type + text
		// */
		//
		// request8.setAuthority(1001);
		//
		// /*
		// * 21st case: authority + criteria + text
		// */
		//
		// request8.setAuthority(1001);

		/*
		 * 22nd case: type + criteria + text
		 */

		ObservationsRequest request22 = new ObservationsRequest(UUID.randomUUID());
		request22.setType(150);
		request22.setCriteria(40);
		request22.setText("VME");

		obsRequestList.put(22, request22);

		// /*
		// * 23rd case: authority + type + criteria + text
		// */
		//
		// request8.setAuthority(1001);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testInvoke() throws Exception {

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(1));
		assertEquals("Hard Corner Bugs ", response.getResultList().get(0).getLocalName());
		assertEquals(1, response.getResultList().get(0).getVmeId());
		assertTrue(1 == response.getTotalResult());

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(2));
		assertTrue(0 == response.getTotalResult());

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(3));
		String expected = response.getResultList().get(0).getFactsheetUrl();
		assertTrue(1 == response.getTotalResult());
		assertEquals(expected, response.getResultList().get(0).getFactsheetUrl());

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(4));
		assertTrue(0 == response.getTotalResult());

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(5));
		assertTrue(2 == response.getTotalResult());

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(6));
		assertTrue(0 == response.getTotalResult());

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(7));
		assertTrue(2 == response.getTotalResult());

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(8));
		assertTrue(0 == response.getTotalResult());

		/*
		 * Note: to implement test logic for commented out cases in before
		 * method
		 */

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(13));
		assertTrue(1 == response.getTotalResult());

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(14));
		assertTrue(1 == response.getTotalResult());

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(15));
		assertTrue(1 == response.getTotalResult());

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(16));
		assertTrue(1 == response.getTotalResult());

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(17));
		assertTrue(1 == response.getTotalResult());

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(18));
		assertTrue(2 == response.getTotalResult());

		/*
		 * Note: to implement test logic for commented out cases in before
		 * method
		 */

		response = (ServiceResponse<VmeDto>) service.invoke(obsRequestList.get(22));
		assertTrue(1 == response.getTotalResult());

		/*
		 * Note: to implement test logic for commented out cases in before
		 * method
		 */

	}

}
