package org.fao.fi.vme.rsg.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Alternative;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.VMEsHistory;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb.VmeModelChange;
import org.gcube.application.rsg.service.RsgServiceRead;
import org.gcube.application.rsg.service.dto.NameValue;
import org.gcube.application.rsg.service.dto.ReportEntry;
import org.gcube.application.rsg.service.dto.ReportType;
import org.gcube.application.rsg.service.dto.response.ServiceResponse;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReferenceReport;
import org.gcube.application.rsg.support.compiler.bridge.annotations.RSGReport;
import org.gcube.application.rsg.support.compiler.bridge.annotations.fields.RSGConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.DataConverter;
import org.gcube.application.rsg.support.compiler.bridge.utilities.ScanningUtils;
import org.gcube.application.rsg.support.compiler.exceptions.ReportEvaluationException;
import org.gcube.application.rsg.support.model.components.impl.CompiledReport;

/**
 * Place your class / interface description here.
 * 
 * History:
 * 
 * ------------- --------------- ----------------------- Date Author Comment
 * ------------- --------------- ----------------------- 28/nov/2013 EVanIngen,
 * FFiorellato Creation.
 * 
 * @version 1.0
 * @since 28/nov/2013
 */
@Alternative
public class RsgServiceReadImplVme extends AbstractRsgServiceImplVme implements RsgServiceRead {
	private Class<?> getTemplateOfType(Class<? extends Annotation> marker, ReportType reportType) {
		if (marker.equals(RSGReport.class)) {
			for (Object report : this._reports)
				if (report.getClass().getSimpleName().equals(reportType.getTypeIdentifier()))
					return report.getClass();
		} else if (marker.equals(RSGReferenceReport.class)) {
			for (Object refReport : this._refReports)
				if (refReport.getClass().getSimpleName().equals(reportType.getTypeIdentifier()))
					return refReport.getClass();
		}

		return null;
	}

	@Override
	public ReportType[] getReportTypes() {
		u.create();

		aVmeModelChange.fire(new VmeModelChange());

		for (Object report : this._reports)
			u.add(report.getClass().getSimpleName());

		// for(Object report : this._refReports)
		// u.add(report.getClass().getSimpleName());

		return u.getReportTypes().toArray(new ReportType[0]);
	}

	@Override
	public ReportType[] getRefReportTypes() {
		u.create();

		for (Object report : this._refReports)
			u.add(report.getClass().getSimpleName());

		return u.getReportTypes().toArray(new ReportType[0]);
	}

	@Override
	public ReportEntry[] listReports(ReportType reportType, NameValue... filters) {
		final String typeId = reportType.getTypeIdentifier();

		Map<String, Object> criteria = new HashMap<String, Object>();

		if (filters != null)
			for (NameValue filter : filters) {
				criteria.put(filter.getName(), filter.getValue());
			}

		if ("Vme".equals(typeId))
			return this.listVMEs(criteria);
		else if ("Rfmo".equals(typeId))
			return this.listRFMOs(criteria);
		else if ("InformationSource".equals(typeId))
			return this.listInformationSources(criteria);
		else if ("GeneralMeasure".equals(typeId))
			return this.listGeneralMeasure(criteria);
		else if ("VMEsHistory".equals(typeId))
			return this.listVMEsHistory(criteria);
		else if ("FisheryAreasHistory".equals(typeId))
			return this.listFisheryAreasHistory(criteria);
		return null;
	}

	private ReportEntry[] listVMEs(Map<String, Object> criteria) {
		Collection<Vme> vmes = this.vmeDao.filterEntities(this.vmeDao.getEm(), Vme.class, criteria);

		List<ReportEntry> results = new ArrayList<ReportEntry>();

		ReportEntry entry;
		Rfmo owner;
		for (Vme vme : vmes) {
			owner = vme.getRfmo();
			entry = new ReportEntry();
			entry.setNameValueList(new ArrayList<NameValue>());

			entry.setId(vme.getId());
			entry.setReportType(new ReportType("Vme"));
			entry.setOwned(owner != null);
			entry.setOwner(owner == null ? "[ NOT SET]" : owner.getId());

			entry.setIdentifier(MLSu.getEnglish(vme.getName()));

			entry.getNameValueList().add(new NameValue("InventoryIdentifier", vme.getInventoryIdentifier()));
			entry.getNameValueList().add(new NameValue("Rfmo", owner == null ? "[ NOT SET]" : owner.getId()));
			entry.getNameValueList().add(new NameValue("Name", MLSu.getEnglish(vme.getName())));

			results.add(entry);
		}

		return results.toArray(new ReportEntry[0]);
	}

	private ReportEntry[] listRFMOs(Map<String, Object> criteria) {
		Collection<Rfmo> rfmos = this.vmeDao.filterEntities(this.vmeDao.getEm(), Rfmo.class, criteria);

		List<ReportEntry> results = new ArrayList<ReportEntry>();

		ReportEntry entry;
		for (Rfmo rfmo : rfmos) {
			entry = new ReportEntry();
			entry.setNameValueList(new ArrayList<NameValue>());

			entry.setId(rfmo.getId());
			entry.setReportType(new ReportType("Rfmo"));
			entry.setIdentifier(rfmo.getId());

			entry.getNameValueList().add(new NameValue("Name", rfmo.getId()));
			entry.getNameValueList().add(
					new NameValue("Number of VMEs", String.valueOf(rfmo.getListOfManagedVmes().size())));

			results.add(entry);
		}

		return results.toArray(new ReportEntry[0]);
	}

	private ReportEntry[] listInformationSources(Map<String, Object> criteria) {
		Collection<InformationSource> informationSources = this.vmeDao.filterEntities(this.vmeDao.getEm(),
				InformationSource.class, criteria);

		List<ReportEntry> results = new ArrayList<ReportEntry>();

		ReportEntry entry;
		Rfmo owner;
		for (InformationSource informationSource : informationSources) {
			owner = informationSource.getRfmo();
			entry = new ReportEntry();
			entry.setNameValueList(new ArrayList<NameValue>());

			entry.setId(informationSource.getId());
			entry.setReportType(new ReportType("InformationSource"));
			entry.setOwned(owner != null);
			entry.setOwner(owner == null ? "[ NOT SET]" : owner.getId());
			entry.setIdentifier(entry.getOwner()
					+ " - "
					+ (informationSource.getPublicationYear() == null ? "[ YEAR UNKNOWN ]" : informationSource
							.getPublicationYear().toString()) + " - "
					+ MLSu.getEnglish(informationSource.getCitation()));

			entry.getNameValueList().add(new NameValue("Committee", MLSu.getEnglish(informationSource.getCommittee())));
			entry.getNameValueList().add(new NameValue("Citation", MLSu.getEnglish(informationSource.getCitation())));

			results.add(entry);
		}

		return results.toArray(new ReportEntry[0]);
	}

	private ReportEntry[] listGeneralMeasure(Map<String, Object> criteria) {
		Collection<GeneralMeasure> generalMeasures = this.vmeDao.filterEntities(this.vmeDao.getEm(),
				GeneralMeasure.class, criteria);

		List<ReportEntry> results = new ArrayList<ReportEntry>();

		ReportEntry entry;
		Rfmo owner;
		for (GeneralMeasure generalMeasure : generalMeasures) {
			owner = generalMeasure.getRfmo();

			entry = new ReportEntry();
			entry.setNameValueList(new ArrayList<NameValue>());

			entry.setId(generalMeasure.getId());
			entry.setReportType(new ReportType("GeneralMeasure"));
			entry.setOwned(owner != null);
			entry.setOwner(owner == null ? "[ NOT SET]" : owner.getId());
			entry.setIdentifier(entry.getOwner() + " - " + generalMeasure.getYear());

			entry.getNameValueList().add(new NameValue("Rfmo", owner == null ? "[ NOT SET]" : owner.getId()));
			entry.getNameValueList()
					.add(new NameValue("Year", generalMeasure.getYear() == null ? null : generalMeasure.getYear()
							.toString()));
			entry.getNameValueList().add(
					new NameValue("Validity start", generalMeasure.getValidityPeriod() == null
							|| generalMeasure.getValidityPeriod().getBeginDate() == null ? null : generalMeasure
							.getValidityPeriod().getBeginDate().toString()));
			entry.getNameValueList().add(
					new NameValue("Validity end", generalMeasure.getValidityPeriod() == null
							|| generalMeasure.getValidityPeriod().getEndDate() == null ? null : generalMeasure
							.getValidityPeriod().getEndDate().toString()));

			results.add(entry);
		}

		return results.toArray(new ReportEntry[0]);
	}

	private ReportEntry[] listFisheryAreasHistory(Map<String, Object> criteria) {
		Collection<FisheryAreasHistory> fisheryAreasHistory = this.vmeDao.filterEntities(this.vmeDao.getEm(),
				FisheryAreasHistory.class, criteria);

		List<ReportEntry> results = new ArrayList<ReportEntry>();

		ReportEntry entry;
		Rfmo owner;
		for (FisheryAreasHistory fisheryAreaHistory : fisheryAreasHistory) {
			owner = fisheryAreaHistory.getRfmo();

			entry = new ReportEntry();
			entry.setNameValueList(new ArrayList<NameValue>());

			entry.setId(fisheryAreaHistory.getId());
			entry.setReportType(new ReportType("FisheryAreasHistory"));
			entry.setOwned(owner != null);
			entry.setOwner(owner == null ? "[ NOT SET]" : owner.getId());
			entry.setIdentifier(entry.getOwner() + " - " + fisheryAreaHistory.getYear());

			entry.getNameValueList().add(
					new NameValue("Year", fisheryAreaHistory.getYear() == null ? null : fisheryAreaHistory.getYear()
							.toString()));
			entry.getNameValueList().add(new NameValue("History", MLSu.getEnglish(fisheryAreaHistory.getHistory())));

			results.add(entry);
		}

		return results.toArray(new ReportEntry[0]);
	}

	private ReportEntry[] listVMEsHistory(Map<String, Object> criteria) {
		Collection<VMEsHistory> vmesHistory = this.vmeDao.filterEntities(this.vmeDao.getEm(), VMEsHistory.class,
				criteria);

		List<ReportEntry> results = new ArrayList<ReportEntry>();

		ReportEntry entry;
		Rfmo owner;
		for (VMEsHistory vmeHistory : vmesHistory) {
			owner = vmeHistory.getRfmo();

			entry = new ReportEntry();
			entry.setNameValueList(new ArrayList<NameValue>());

			entry.setId(vmeHistory.getId());
			entry.setReportType(new ReportType("VMEsHistory"));
			entry.setOwned(owner != null);
			entry.setOwner(owner == null ? "[ NOT SET]" : owner.getId());
			entry.setIdentifier(entry.getOwner() + " - " + vmeHistory.getYear());

			entry.getNameValueList().add(
					new NameValue("Year", vmeHistory.getYear() == null ? null : vmeHistory.getYear().toString()));
			entry.getNameValueList().add(new NameValue("History", MLSu.getEnglish(vmeHistory.getHistory())));

			results.add(entry);
		}

		return results.toArray(new ReportEntry[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gcube.application.rsg.service.RsgServiceRead#getEmptyReport(org.gcube
	 * .application.rsg.service.dto.ReportType)
	 */
	@Override
	public CompiledReport getTemplate(ReportType reportType) {
		Class<?> identifiedReport = this.getTemplateOfType(RSGReport.class, reportType);

		try {
			CompiledReport template = this._compiler.compile(identifiedReport);

			template.setCreatedBy("[ NOT SET ]");
			template.setCreationDate(new Date());
			template.setLastEditedBy("[ NOT SET ]");
			template.setLastEditingDate(new Date());

			this.dumpModel(template);

			return template;
		} catch (Throwable t) {
			return null;
		}
	}

	@Override
	public CompiledReport getReportById(ReportType reportType, Object reportId) {
		Class<?> identifiedReport = this.getTemplateOfType(RSGReport.class, reportType);

		Object identified = null;

		try {
			Field id = ScanningUtils.getUniqueIdentifier(identifiedReport);
			Class<?> idType = id.getType();

			DataConverter<?> converter = 
					ScanningUtils.isAnnotatedWith(id, RSGConverter.class) ? 
							ScanningUtils.getAnnotation(id, RSGConverter.class).value().newInstance() : 
							null;

			if (converter != null)
				reportId = converter.fromString((String) reportId);
			// Fallback case
			else if (Integer.class.equals(idType) || Long.class.equals(idType))
				reportId = Long.valueOf(reportId.toString());

			identified = this.vmeDao.getEntityById(this.vmeDao.getEm(), identifiedReport, reportId);
		} catch (Throwable t) {
			LOG.error("Unable to get entity of type {} with id {}", reportType.getTypeIdentifier(), reportId, t);
		}

		if (identified == null) {
			LOG.warn("Unable to identify report of type {} with id {}", reportType.getTypeIdentifier(), reportId);

			return null;
		}

		try {
			CompiledReport report = this._evaluator.evaluate(this._compiler.compile(identifiedReport), identified);
			report.setCreatedBy("[ NOT SET ]");
			report.setCreationDate(new Date());
			report.setLastEditedBy("[ NOT SET ]");
			report.setLastEditingDate(new Date());

			this.dumpModel(report);

			return report;
		} catch (Throwable t) {
			LOG.info(
					"Unable to compile report of type {} with id {}: {} [ {} ]",
					new Object[] { reportType.getTypeIdentifier(), reportId, t.getClass().getSimpleName(),
							t.getMessage() });

			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gcube.application.rsg.service.RsgServiceRead#getRefReport(org.gcube.
	 * application.rsg.service.dto.ReportType, int)
	 */
	@Override
	public CompiledReport getReferenceReportById(ReportType refReportType, Object refReportId) {
		Class<?> identifiedReport = this.getTemplateOfType(RSGReferenceReport.class, refReportType);

		Object identified = null;

		try {
			Field id = ScanningUtils.getUniqueIdentifier(identifiedReport);
			Class<?> idType = id.getType();

			DataConverter<?> converter = 
					ScanningUtils.isAnnotatedWith(id, RSGConverter.class) ? 
						ScanningUtils.getAnnotation(id, RSGConverter.class).value().newInstance() : 
						null;

			if (converter != null)
				refReportId = converter.fromString((String) refReportId);
			// Fallback case
			else if (Integer.class.equals(idType) || Long.class.equals(idType))
				refReportId = Long.valueOf(refReportId.toString());

			identified = this.vmeDao.getEntityById(this.vmeDao.getEm(), identifiedReport, refReportId);
		} catch (Throwable t) {
			LOG.error("Unable to get entity of type {} with id {}", refReportType.getTypeIdentifier(), refReportId);
		}

		if (identified == null) {
			LOG.warn("Unable to identify ref report of type {} with id {}", refReportType.getTypeIdentifier(),
					refReportId);

			return null;
		}

		try {
			CompiledReport report = this._compiler.compile(identifiedReport);
			report.setIsAReference(true);

			report = this._evaluator.evaluate(report, identified);
			report.setCreatedBy("[ NOT SET ]");
			report.setCreationDate(new Date());
			report.setLastEditedBy("[ NOT SET ]");
			report.setLastEditingDate(new Date());

			// this.dumpModel(report);

			return report;
		} catch (Throwable t) {
			LOG.info(
					"Unable to compile ref report of type {} with id {}: {} [ {} ]",
					new Object[] { refReportType.getTypeIdentifier(), refReportId, t.getClass().getSimpleName(),
							t.getMessage() });

			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gcube.application.rsg.service.RsgServiceRead#getRefTemplate(org.gcube
	 * .application.rsg.service.dto.ReportType)
	 */
	@Override
	public CompiledReport getRefTemplate(ReportType refReportType) {
		Class<?> identifiedReport = this.getTemplateOfType(RSGReferenceReport.class, refReportType);

		try {
			return this._compiler.compile(identifiedReport);
		} catch (Throwable t) {
			return null;
		}
	}

	protected Class<?> getEntityByReportType(ReportType reportType) {
		Class<?>[] availableReportTypes = new Class<?>[] { Vme.class };

		return this.getEntityByType(availableReportTypes, reportType);
	}

	protected Class<?> getEntityByReferenceReportType(ReportType reportType) {
		Class<?>[] availableReferenceReportTypes = new Class<?>[] { Rfmo.class, GeneralMeasure.class,
				InformationSource.class, FisheryAreasHistory.class, VMEsHistory.class };

		return this.getEntityByType(availableReferenceReportTypes, reportType);
	}

	private Class<?> getEntityByType(Class<?>[] availableTypes, ReportType reportType) {
		if (availableTypes == null || availableTypes.length == 0)
			return null;

		final String typeName = reportType.getTypeIdentifier();

		for (Class<?> type : availableTypes)
			if (type.getSimpleName().equals(typeName))
				return type;

		LOG.warn("Unknown type {}", typeName);

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gcube.application.rsg.service.RsgServiceRead#validate(org.gcube.application
	 * .rsg.support.model.components.impl.CompiledReport)
	 */
	@Override
	public ServiceResponse validate(CompiledReport report) {
		String id = report.getId() == null ? "#NEW#" : "#" + report.getId();

		LOG.info("Requesting validation of {} report {}", report.getType(), id);

		ServiceResponse response = new ServiceResponse();

		try {
			report.setEvaluated(true);

			Object extracted = this._evaluator.extract(report);

			if (extracted == null)
				response.invalid("Report evaluation yield a NULL result");
			else
				response.valid("Report has been validated successfully");
		} catch (ReportEvaluationException REe) {
			response.invalid("Report is invalid: " + REe.getMessage());
		}

		return response.undeterminedIfNotSet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gcube.application.rsg.service.RsgServiceRead#validateRef(org.gcube.
	 * application.rsg.support.model.components.impl.CompiledReport)
	 */
	@Override
	public ServiceResponse validateRef(CompiledReport report) {
		String id = report.getId() == null ? "#NEW#" : "#" + report.getId();

		LOG.info("Requesting validation of {} reference report {}", report.getType(), id);

		ServiceResponse response = new ServiceResponse();

		try {
			report.setEvaluated(true);

			Object extracted = this._evaluator.extract(report);

			if (extracted == null)
				response.invalid("Reference report evaluation yield a NULL result");
			else
				response.valid("Reference report has been validated successfully");
		} catch (ReportEvaluationException REe) {
			response.invalid("Reference report is invalid: " + REe.getMessage());
		}

		return response.undeterminedIfNotSet();
	}

	// private <S> S forceRfmo(S source, Rfmo toForce) {
	// if(source != null) {
	// if(source instanceof GeneralMeasure)
	// ((GeneralMeasure)source).setRfmo(toForce);
	// else if(source instanceof InformationSource)
	// ((InformationSource)source).setRfmo(toForce);
	// else if(source instanceof FisheryAreasHistory)
	// ((FisheryAreasHistory)source).setRfmo(toForce);
	// else if(source instanceof VMEsHistory)
	// ((VMEsHistory)source).setRfmo(toForce);
	// }
	//
	// return source;
	// }
}
