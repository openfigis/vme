package org.vme.web.service;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import javax.inject.Inject;

import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.JpaDaoFactory;
import org.vme.service.dao.ObservationDAO;
import org.vme.service.dao.config.figis.FigisDataBaseProducer;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.web.service.io.ObservationsRequest;
import org.vme.web.service.io.ServiceResponse;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class, VmeDataBaseProducer.class, JpaDaoFactory.class })
public class ServiceInvokerTest {

	@Inject
	ObservationDAO service;

	@Test
	public void testInvokeObservationDAOObservationsRequest() throws Exception {
		ObservationsRequest request = new ObservationsRequest(UUID.randomUUID());
		ServiceResponse<?> response = ServiceInvoker.invoke(service, request);
		assertNotNull(response);
	}

	@Test
	public void testInvokeReferenceDAOReferencesRequest() {
		// fail("Not yet implemented");
	}

}
