package org.vme.web.service;

import javax.enterprise.inject.Alternative;

import org.vme.service.SearchServiceInterface;
import org.vme.service.dto.VmeDto;
import org.vme.service.search.ObservationsRequest;
import org.vme.service.search.ReferencesRequest;
import org.vme.service.search.ServiceResponse;

@Alternative
public class DummySearchService implements SearchServiceInterface {

	public static int year;

	@Override
	public ServiceResponse<?> invoke(ObservationsRequest request) throws Exception {
		ServiceResponse<?> result = new ServiceResponse<VmeDto>(request);
		this.year = request.getYear();

		return result;
	}

	@Override
	public ServiceResponse<?> invoke(ReferencesRequest request) throws Exception {
		ServiceResponse<?> result = new ServiceResponse<VmeDto>(request);

		return result;
	}

}
