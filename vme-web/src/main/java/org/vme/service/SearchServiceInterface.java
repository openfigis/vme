package org.vme.service;

import org.vme.service.search.ObservationsRequest;
import org.vme.service.search.ReferencesRequest;
import org.vme.service.search.ServiceResponse;

public interface SearchServiceInterface {

	public abstract ServiceResponse<?> invoke(ObservationsRequest request)
			throws Exception;

	public abstract ServiceResponse<?> invoke(ReferencesRequest request)
			throws Exception;

}