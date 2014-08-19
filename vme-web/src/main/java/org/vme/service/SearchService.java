package org.vme.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.ReferenceYear;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.reference.InformationSourceType;
import org.fao.fi.vme.domain.model.reference.VmeCriteria;
import org.fao.fi.vme.domain.model.reference.VmeScope;
import org.fao.fi.vme.domain.model.reference.VmeType;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.ReferenceConcept;
import org.vme.dao.transaction.VmeDaoTransactional;
import org.vme.service.dto.DtoTranslator;
import org.vme.service.dto.VmeDto;
import org.vme.service.search.ObservationsRequest;
import org.vme.service.search.ReferencesRequest;
import org.vme.service.search.ServiceResponse;
import org.vme.web.cache.Cached;

public class SearchService extends AbstractService {

	@Inject
	private VmeDaoTransactional vDao;

	@Inject
	private DtoTranslator translator;

	@Cached
	public ServiceResponse<?> invoke(ObservationsRequest request) throws Exception {
		ServiceResponse<?> result = new ServiceResponse<VmeDto>(request);

		List<Vme> vmeList = vDao.loadVmes();
		List<VmeDto> vmeDtoList = new ArrayList<VmeDto>();

		if (request.getId() > 0) {
			filterVmePerId(vmeList, request.getId());
		} else if (request.hasInventoryIdentifier()) {
			filterVmePerInventoryIdentifier(vmeList, request.getInventoryIdentifier());
		} else if (request.hasGeographicFeatureId()) {
			filterVmePerGeographFeatureId(vmeList, request.getGeographicFeatureId());
		} else if (request.hasText() || request.hasAuthority() || request.hasType() || request.hasCriteria()) {
			filterByRequestParam(vmeList, request.getAuthority(), request.getType(), request.getCriteria(),
					request.getText());
		} else if (!request.hasText() && !request.hasAuthority() && !request.hasType() && !request.hasCriteria()) {
			vmeList.clear();
		}

		for (Vme vme : vmeList) {
			vmeDtoList.add(translator.doTranslate4Vme(vme, request.getYear()));
		}

		result.addElements(vmeDtoList);

		return result;
	}

	@Cached
	public ServiceResponse<?> invoke(ReferencesRequest request) throws Exception {
		Class<? extends ReferenceConcept> conceptClass = this.refDao.getConcept(request.getConcept());
		ServiceResponse<?> result = null;
		if (conceptClass.equals(Authority.class)) {
			result = new ServiceResponse<Authority>(request);
		} else if (conceptClass.equals(VmeCriteria.class)) {
			result = new ServiceResponse<VmeCriteria>(request);
		} else if (conceptClass.equals(VmeType.class)) {
			result = new ServiceResponse<VmeType>(request);
		} else if (conceptClass.equals(VmeScope.class)) {
			result = new ServiceResponse<VmeScope>(request);
		} else if (conceptClass.equals(InformationSourceType.class)) {
			result = new ServiceResponse<InformationSourceType>(request);
		} else if (conceptClass.equals(ReferenceYear.class)) {
			result = new ServiceResponse<ReferenceYear>(request);
		} else {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		result.addElements(this.refDao.getAllReferences(conceptClass));

		return result;
	}
}
