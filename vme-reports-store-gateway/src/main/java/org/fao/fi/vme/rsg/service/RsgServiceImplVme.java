package org.fao.fi.vme.rsg.service;

import java.util.List;

import org.fao.fi.vme.domain.GeneralMeasure;
import org.fao.fi.vme.domain.History;
import org.fao.fi.vme.domain.InformationSource;
import org.fao.fi.vme.domain.Vme;
import org.gcube.application.rsg.service.RsgService;
import org.gcube.application.rsg.service.dto.Model;
import org.gcube.application.rsg.service.dto.ReportEntry;
import org.gcube.application.rsg.service.dto.ReportType;
import org.gcube.application.rsg.service.dto.response.Response;
import org.gcube.application.rsg.service.util.RsgServiceUtil;

/**
 * 
 * @author Erik van Ingen
 * 
 */
public class RsgServiceImplVme implements RsgService {
	RsgServiceUtil u = new RsgServiceUtil();

	@Override
	public List<ReportType> getReportTypes() {
		u.create().add(Vme.class.getSimpleName());
		return u.getReportTypes();
	}

	@Override
	public List<ReportType> getRefReportTypes() {
		u.create().add(GeneralMeasure.class.getSimpleName()).add(InformationSource.class.getSimpleName())
				.add("FisheryAreas" + History.class.getSimpleName()).add("Vmes" + History.class.getSimpleName());
		return u.getReportTypes();
	}

	@Override
	public List<ReportEntry> listReports(ReportType reportType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model getReport(ReportType reportType, int reportId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response publishDelta(List<Model> modelList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response validateDelta(List<Model> modelList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response publishRef(Model refModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response validateRef(Model refModel) {
		// TODO Auto-generated method stub
		return null;
	}

}
