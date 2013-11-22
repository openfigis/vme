package org.fao.fi.vme.rsg.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.gcube.application.rsg.service.RsgService;
import org.gcube.application.rsg.service.dto.NameValue;
import org.gcube.application.rsg.service.dto.ReportEntry;
import org.gcube.application.rsg.service.dto.ReportType;
import org.gcube.application.rsg.service.dto.response.Response;
import org.gcube.application.rsg.service.util.RsgServiceUtil;
import org.gcube.application.rsg.support.compiler.ReportCompiler;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReferenceReport;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReport;
import org.gcube.application.rsg.support.compiler.impl.AnnotationBasedReportCompiler;
import org.gcube.application.rsg.support.model.components.CompiledReport;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.reflections.Reflections;

/**
 * 
 * @author Erik van Ingen
 * 
 */
@ActivatedAlternatives({ AnnotationBasedReportCompiler.class })
public class RsgServiceImplVme implements RsgService {
	private Reflections _reflections = new Reflections("org.fao.fi.vme.domain");

	@Named("TemplateCompiler")
	@Inject private ReportCompiler _templateCompiler;
	
	RsgServiceUtil u = new RsgServiceUtil();

	@Override
	public List<ReportType> getReportTypes() {
		u.create();
		
		for(Class<?> report : this._reflections.getTypesAnnotatedWith(RSGReport.class))
			u.add(report.getName());
		
		return u.getReportTypes();
	}

	@Override
	public List<ReportType> getRefReportTypes() {
		
		u.create();
		
		for(Class<?> report : this._reflections.getTypesAnnotatedWith(RSGReferenceReport.class))
			u.add(report.getName());
		
		return u.getReportTypes();
	}

	@Override
	public List<ReportEntry> listReports(ReportType reportType, NameValue... filters) {
		return null;
	}

	@Override
	public CompiledReport getReport(ReportType reportType, int reportId) {
		u.create();
		
		Class<?> identifiedReport = null;
		
		for(Class<?> report : this._reflections.getTypesAnnotatedWith(RSGReferenceReport.class))
			if(report.getName().equals(reportType.getTypeIdentifier()))
				identifiedReport = report;
			
		try {
			return this._templateCompiler.compile(identifiedReport);
		} catch (Throwable t) {
			return null;
		}
	}

	@Override
	public Response publishDelta(List<CompiledReport> modelList) {
		return null;
	}

	@Override
	public Response validateDelta(List<CompiledReport> modelList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response publishRef(CompiledReport refModel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response validateRef(CompiledReport refModel) {
		// TODO Auto-generated method stub
		return null;
	}
}