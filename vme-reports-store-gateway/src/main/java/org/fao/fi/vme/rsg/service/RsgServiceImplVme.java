package org.fao.fi.vme.rsg.service;

import java.util.List;

import org.gcube.application.rsg.service.RsgService;
import org.gcube.application.rsg.service.dto.NameValue;
import org.gcube.application.rsg.service.dto.ReportEntry;
import org.gcube.application.rsg.service.dto.ReportType;
import org.gcube.application.rsg.service.dto.response.Response;
import org.gcube.application.rsg.service.util.RsgServiceUtil;
import org.gcube.application.rsg.support.annotations.RSGReferenceReport;
import org.gcube.application.rsg.support.annotations.RSGReport;
import org.gcube.portlets.d4sreporting.common.shared.Model;
import org.reflections.Reflections;

/**
 * 
 * @author Erik van Ingen
 * 
 */
public class RsgServiceImplVme implements RsgService {
	RsgServiceUtil u = new RsgServiceUtil();

	@Override
	public List<ReportType> getReportTypes() {
		Reflections reflections = new Reflections("org.fao.fi.vme.domain");
		
		u.create();
		
		for(Class<?> report : reflections.getTypesAnnotatedWith(RSGReport.class))
			u.add(report.getName());
		
		return u.getReportTypes();
	}

	@Override
	public List<ReportType> getRefReportTypes() {
		Reflections reflections = new Reflections("org.fao.fi.vme.domain");
		
		u.create();
		
		for(Class<?> report : reflections.getTypesAnnotatedWith(RSGReferenceReport.class))
			u.add(report.getName());
		
		return u.getReportTypes();
		
//		u.create().add(GeneralMeasure.class.getSimpleName()).add(InformationSource.class.getSimpleName())
//				.add("FisheryAreas" + History.class.getSimpleName()).add("Vmes" + History.class.getSimpleName());
//		return u.getReportTypes();
	}

	@Override
	public List<ReportEntry> listReports(ReportType reportType, NameValue... filters) {
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
