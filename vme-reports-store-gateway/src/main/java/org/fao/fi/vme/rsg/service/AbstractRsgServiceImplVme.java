/**
 * (c) 2014 FAO / UN (project: vme-reports-store-gateway)
 */
package org.fao.fi.vme.rsg.service;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.VMEsHistory;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.sync.factsheets.listeners.FactsheetChangeListener;
import org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb.VmeModelChange;
import org.gcube.application.reporting.persistence.PersistenceManager;
import org.gcube.application.reporting.reader.ModelReader;
import org.gcube.application.rsg.service.dto.ReportType;
import org.gcube.application.rsg.service.dto.response.ServiceResponse;
import org.gcube.application.rsg.service.dto.response.ServiceResponseCode;
import org.gcube.application.rsg.service.dto.response.ServiceResponseMessage;
import org.gcube.application.rsg.service.util.RsgServiceUtil;
import org.gcube.application.rsg.support.builder.ReportBuilder;
import org.gcube.application.rsg.support.builder.annotations.Builder;
import org.gcube.application.rsg.support.builder.exceptions.ReportBuilderException;
import org.gcube.application.rsg.support.compiler.ReportCompiler;
import org.gcube.application.rsg.support.compiler.annotations.Compiler;
import org.gcube.application.rsg.support.compiler.annotations.Evaluator;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.ReferenceReport;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.Report;
import org.gcube.application.rsg.support.compiler.bridge.utilities.Utils;
import org.gcube.application.rsg.support.evaluator.ReportEvaluator;
import org.gcube.application.rsg.support.model.components.impl.CompiledReport;
import org.gcube.portlets.d4sreporting.common.shared.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.sources.vme.VmeDao;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 15 Sep 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 15 Sep 2014
 */
public class AbstractRsgServiceImplVme {
	final protected Logger LOG = LoggerFactory.getLogger(RsgServiceReadImplVme.class);

	@Inject
	@Builder
	protected ReportBuilder<Model> _builder;
	
	@Inject
	@Compiler
	protected ReportCompiler _compiler;
	
	@Inject
	@Evaluator
	protected ReportEvaluator _evaluator;
	
	@Inject
	protected FactsheetChangeListener _fsChangeListener;
	
	@Inject
	@Any
	protected Instance<ReferenceReport> _refReports;
	
	@Inject
	@Any
	protected Instance<Report> _reports;
	
	@Inject
	protected Event<VmeModelChange> aVmeModelChange;
	
	final protected MultiLingualStringUtil MLSu = new MultiLingualStringUtil();
	final protected RsgServiceUtil u = new RsgServiceUtil();
	
	@Inject
	protected VmeDao vmeDao;

	final protected void dumpModel(CompiledReport report) throws ReportBuilderException {
		String type = report.getType();
		String reportId = report.getId();
	
		if (reportId == null)
			reportId = "new" + System.currentTimeMillis();
	
		ReportType reportType = new ReportType(type.substring(type.lastIndexOf(".")));
	
		Model model = this._builder.buildReport(report);
	
		File folder = new File(this.getReportDumpPath() + reportType.getTypeIdentifier());
	
		folder.mkdir();
	
		folder = new File(folder.getAbsolutePath() + File.separator + reportType.getTypeIdentifier().toUpperCase()
				+ "_" + reportId);
	
		folder.mkdir();
	
		File file = new File(folder, reportType.getTypeIdentifier().toUpperCase() + "_" + reportId + ".d4st");
	
		new ModelReader(model);
	
		PersistenceManager.writeModel(model, file);
		PersistenceManager.readModel(file.getAbsolutePath());
	}

	protected Rfmo extractRfmo(Object source) {
		Rfmo parent = null;
	
		if (source != null) {
			if (source instanceof GeneralMeasure)
				parent = ((GeneralMeasure) source).getRfmo();
			else if (source instanceof InformationSource)
				parent = ((InformationSource) source).getRfmo();
			else if (source instanceof FisheryAreasHistory)
				parent = ((FisheryAreasHistory) source).getRfmo();
			else if (source instanceof VMEsHistory)
				parent = ((VMEsHistory) source).getRfmo();
		}
	
		return parent;
	}

	final protected String getReportDumpPath() {
		String path = Utils.coalesce(System.getProperty("rsg.reports.dump.path"), System.getProperty("java.io.tmpdir"));
	
		if (path != null && !path.endsWith(File.separator))
			path += File.separator;
	
		return path;
	}

	final protected ServiceResponse handleRollback(ServiceResponse current, EntityTransaction tx) {
		try {
			LOG.info("Rolling back transaction...");
	
			if (current != null) {
				if (current.getResponseMessageList() != null && !current.getResponseMessageList().isEmpty()) {
					LOG.info("Current response info:");
					for (ServiceResponseMessage entry : current.getResponseMessageList()) {
						LOG.info("{} : {}", entry.getResponseCode(), entry.getResponseMessage());
					}
				} else {
					LOG.warn("No previous response info!");
				}
			}
	
			if (tx.isActive())
				tx.rollback();
			else
				LOG.warn("No active transaction: unable to rollback");
		} catch (Throwable t) {
			String message = "Unable to rollback transaction: " + t.getClass().getSimpleName() + " [ " + t.getMessage()
					+ " ]";
	
			LOG.error(message);
	
			current.addEntry(new ServiceResponseMessage(ServiceResponseCode.NOT_SUCCEEDED, message));
		}
	
		return current;
	}

	@PostConstruct
	protected void postConstruct() {
		this._compiler.registerPrimitiveType(MultiLingualString.class);
	
		LOG.info("Available report types:");
		for (Object report : this._reports) {
			LOG.info("{}", report.getClass().getName());
		}
	
		LOG.info("Available reference report types:");
		for (Object refReport : this._refReports) {
			LOG.info("{}", refReport.getClass().getName());
		}
	
		// Using an in-memory database requires that data are
		// transferred from the original M$ Access DB into H2...
		// this.importer.importMsAccessData();
		//
		// this._referenceBatch.run();
	}
}
