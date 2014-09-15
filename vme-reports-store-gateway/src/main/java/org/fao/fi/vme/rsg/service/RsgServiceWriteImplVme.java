package org.fao.fi.vme.rsg.service;

import javax.enterprise.inject.Alternative;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.VMEsHistory;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb.VmeModelChange;
import org.gcube.application.rsg.service.RsgServiceWrite;
import org.gcube.application.rsg.service.dto.ReportType;
import org.gcube.application.rsg.service.dto.response.ServiceResponse;
import org.gcube.application.rsg.support.model.components.impl.CompiledReport;
import org.gcube.application.rsg.support.model.utils.CompiledReportUtils;

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
public class RsgServiceWriteImplVme extends AbstractRsgServiceImplVme implements RsgServiceWrite {


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gcube.application.rsg.service.RsgServiceRead#deleteById(org.gcube.application
	 * .rsg.service.dto.ReportType, java.lang.Object)
	 */
	@Override
	public ServiceResponse deleteById(ReportType reportType, Object reportId) {
		LOG.info("Requesting deletion of {} report #{}", reportType.getTypeIdentifier(), reportId);

		ServiceResponse response = new ServiceResponse();

		Class<?> entity = this.getEntityByReportType(reportType);

		EntityManager em = this.vmeDao.getEm();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			Object toDelete = this.vmeDao.getEntityById(this.vmeDao.getEm(), entity, reportId);

			if (toDelete != null) {
				this.vmeDao.delete(toDelete);

				tx.commit();

				this._fsChangeListener.vmeDeleted((Vme) toDelete);
				aVmeModelChange.fire(new VmeModelChange());

				response.succeeded(entity.getName() + " report #" + reportId + " has been deleted");

				LOG.info("{} report #{} has been deleted", entity.getName(), reportId);
			} else {
				LOG.warn("{} report #{} cannot be deleted as it doesn't exist", entity.getName(), reportId);

				response.notSucceeded(entity.getName() + " report #" + reportId
						+ " cannot be deleted as it doesn't exist");
			}
		} catch (Throwable t) {
			LOG.error("Unable to delete {} report #{}: {} [ {} ]", entity.getName(), reportId, t.getClass()
					.getSimpleName(), t.getMessage());

			response.notSucceeded(t.getMessage()); // entity.getName() +
													// " report #" + reportId +
													// " cannot be deleted: " +
													// t.getClass().getSimpleName()
													// + " [ " + t.getMessage()
													// + " ]");

			response = this.handleRollback(response, tx);
		}

		return response.undeterminedIfNotSet();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gcube.application.rsg.service.RsgServiceRead#deleteReferenceById(org.
	 * gcube.application.rsg.service.dto.ReportType, java.lang.Object)
	 */
	@Override
	public ServiceResponse deleteReferenceById(ReportType referenceReportType, Object refReportId) {
		LOG.info("Requesting deletion of {} reference report #{}", referenceReportType.getTypeIdentifier(), refReportId);

		ServiceResponse response = new ServiceResponse();

		Class<?> entity = this.getEntityByReferenceReportType(referenceReportType);

		EntityManager em = this.vmeDao.getEm();
		EntityTransaction tx = em.getTransaction();

		try {
			Object toDelete = this.vmeDao.getEntityById(this.vmeDao.getEm(), entity, refReportId);
			Rfmo parent = null;

			if (toDelete != null) {
				tx.begin();

				parent = this.extractRfmo(toDelete);

				this.vmeDao.delete(toDelete);

				tx.commit();

				// This is necessary, as deleting the reference will set its
				// Rfmo to NULL and thus it won't be
				// possible to notify factsheet changes to VME belonging to the
				// same RFMO owning the just deleted entity

				if (toDelete instanceof GeneralMeasure) {
					this._fsChangeListener.generalMeasureDeleted(parent, (GeneralMeasure) toDelete);
				} else if (toDelete instanceof InformationSource) {
					this._fsChangeListener.informationSourceDeleted(parent, (InformationSource) toDelete);
				} else if (toDelete instanceof FisheryAreasHistory) {
					this._fsChangeListener.fishingFootprintDeleted(parent, (FisheryAreasHistory) toDelete);
				} else if (toDelete instanceof VMEsHistory) {
					this._fsChangeListener.regionalHistoryDeleted(parent, (VMEsHistory) toDelete);
				}
				aVmeModelChange.fire(new VmeModelChange());

				response.succeeded(entity.getName() + " reference report #" + refReportId + " has been deleted");

				LOG.info("{} reference report #{} has been deleted", entity.getName(), refReportId);
			} else {
				LOG.warn("{} reference report #{} cannot be deleted as it doesn't exist", entity.getName(), refReportId);

				response.notSucceeded(entity.getName() + " report #" + refReportId
						+ " cannot be deleted as it doesn't exist");
			}
		} catch (Throwable t) {
			LOG.error("Unable to delete {} report #{}: {} [ {} ]", entity.getName(), refReportId, t.getClass()
					.getSimpleName(), t.getMessage(), t);

			response.notSucceeded(t.getMessage());

			response = this.handleRollback(response, tx);
		}

		return response.undeterminedIfNotSet();
	}

	private Class<?> getEntityByReportType(ReportType reportType) {
		Class<?>[] availableReportTypes = new Class<?>[] { Vme.class };

		return this.getEntityByType(availableReportTypes, reportType);
	}

	private Class<?> getEntityByReferenceReportType(ReportType reportType) {
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
	 * org.gcube.application.rsg.service.RsgServiceRead#update(org.gcube.application
	 * .rsg.support.model.components.impl.CompiledReport)
	 */
	@Override
	public ServiceResponse update(CompiledReport report) {
		this.vmeDao.getEm().clear();

		boolean isNew = report.getId() == null;

		String id = isNew ? "#NEW#" : "#" + report.getId();

		LOG.info("Requesting update of {} report {}", report.getType(), id);

		LOG.info("Report details:");

		try {
			LOG.info(CompiledReportUtils.toXML(report));
		} catch (Throwable t) {
			LOG.warn("Unable to dump report to its XML format", t);
		}

		ServiceResponse response = new ServiceResponse();

		EntityManager em = this.vmeDao.getEm();
		EntityTransaction tx = em.getTransaction();

		Object toUpdate, updated = null;

		try {
			tx.begin();

			report.setEvaluated(true);

			toUpdate = this._evaluator.extract(report);

			updated = isNew ? this.doCreate(toUpdate) : this.doUpdate(toUpdate);

			if (updated != null) {
				response.succeeded(report.getType() + " report " + id + " has been updated");

				LOG.info("{} report {} has been updated", report.getType(), id);
			} else {
				response.notSucceeded(report.getType() + " report " + id + " cannot be updated");

				LOG.error("{} report {} cannot be updated", report.getType(), id);
			}

			tx.commit();

			try {
				if (isNew)
					this._fsChangeListener.vmeAdded((Vme) updated);
				else
					this._fsChangeListener.vmeChanged((Vme) updated);
				aVmeModelChange.fire(new VmeModelChange());
			} catch (Throwable t) {
				LOG.warn("Unable to update factsheet for {} report {}: {} [ {} ]", report.getType(), id, t.getClass()
						.getSimpleName(), t.getMessage(), t);
			}
		} catch (Throwable t) {
			LOG.error("Unable to update {} report {}: {} [ {} ]", report.getType(), id, t.getClass().getSimpleName(),
					t.getMessage(), t);

			response.notSucceeded(t.getMessage()); // report.getType() +
													// " report " + id +
													// " cannot be updated: " +
													// t.getClass().getSimpleName()
													// + " [ " + t.getMessage()
													// + " ]");

			response = this.handleRollback(response, tx);
		}

		return response.undeterminedIfNotSet();
	}

	private Object doCreate(Object holder) throws Throwable {
		if (holder == null)
			throw new IllegalArgumentException("Cannot create an NULL or empty report");

		if (holder instanceof Vme) {
			Vme toReturn = this.vmeDao.create((Vme) holder);

			return toReturn;
		} else
			throw new IllegalArgumentException(holder.getClass() + " is not a valid model for a report");
	}

	private Object doUpdate(Object holder) throws Throwable {
		if (holder == null)
			throw new IllegalArgumentException("Cannot update an NULL or empty report");

		if (holder instanceof Vme) {
			Vme toReturn = this.vmeDao.update((Vme) holder);

			return toReturn;
		} else
			throw new IllegalArgumentException(holder.getClass() + " is not a valid model for a report");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gcube.application.rsg.service.RsgServiceRead#updateRef(org.gcube.application
	 * .rsg.support.model.components.impl.CompiledReport)
	 */
	@Override
	public ServiceResponse updateRef(CompiledReport referenceReport) {
		boolean isNew = referenceReport.getId() == null;

		String id = isNew ? "#NEW#" : "#" + referenceReport.getId();

		LOG.info("Requesting update of {} reference report {}", referenceReport.getType(), id);

		LOG.info("Reference report details:");

		try {
			LOG.info(CompiledReportUtils.toXML(referenceReport));
		} catch (Throwable t) {
			LOG.warn("Unable to dump reference report to its XML format", t);
		}

		ServiceResponse response = new ServiceResponse();

		EntityManager em = this.vmeDao.getEm();
		EntityTransaction tx = em.getTransaction();

		Object toUpdate, updated = null;

		try {
			tx.begin();

			referenceReport.setEvaluated(true);

			toUpdate = this._evaluator.extract(referenceReport);
			updated = isNew ? this.doCreateReference(toUpdate) : this.doUpdateReference(toUpdate);

			if (updated != null) {
				response.succeeded(referenceReport.getType() + " reference report " + id + " has been updated");

				LOG.info("{} reference report {} has been updated", referenceReport.getType(), id);
			} else {
				response.notSucceeded(referenceReport.getType() + " reference report " + id + " cannot be updated");

				LOG.error("{} reference report {} cannot be updated", referenceReport.getType(), id);
			}

			tx.commit();

			if (updated instanceof InformationSource) {
				if (isNew)
					this._fsChangeListener.informationSourceAdded((InformationSource) updated);
				else
					this._fsChangeListener.informationSourceChanged((InformationSource) updated);
			}

			if (updated instanceof GeneralMeasure) {
				if (isNew)
					this._fsChangeListener.generalMeasureAdded((GeneralMeasure) updated);
				else
					this._fsChangeListener.generalMeasureChanged((GeneralMeasure) updated);
			}

			if (updated instanceof FisheryAreasHistory) {
				if (isNew)
					this._fsChangeListener.fishingFootprintAdded((FisheryAreasHistory) updated);
				else
					this._fsChangeListener.fishingFootprintChanged((FisheryAreasHistory) updated);
			}

			if (updated instanceof VMEsHistory) {
				if (isNew)
					this._fsChangeListener.regionalHistoryAdded((VMEsHistory) updated);
				else
					this._fsChangeListener.regionalHistoryChanged((VMEsHistory) updated);
			}
			aVmeModelChange.fire(new VmeModelChange());
		} catch (Throwable t) {
			LOG.error("Unable to update {} reference report {}: {} [ {} ]", referenceReport.getType(), id, t.getClass()
					.getSimpleName(), t.getMessage(), t);

			response.notSucceeded(t.getMessage()); // referenceReport.getType()
													// + " reference report " +
													// id +
													// " cannot be updated: " +
													// t.getClass().getSimpleName()
													// + " [ " + t.getMessage()
													// + " ]");

			response = this.handleRollback(response, tx);
		}

		return response.undeterminedIfNotSet();
	}

	private Object doCreateReference(Object holder) throws Throwable {
		if (holder == null)
			throw new IllegalArgumentException("Cannot create an NULL or empty reference report");

		if (holder instanceof GeneralMeasure) {
			return this.vmeDao.create((GeneralMeasure) holder);
		} else if (holder instanceof InformationSource) {
			return this.vmeDao.create((InformationSource) holder);
		} else if (holder instanceof FisheryAreasHistory) {
			return this.vmeDao.create((FisheryAreasHistory) holder);
		} else if (holder instanceof VMEsHistory) {
			return this.vmeDao.create((VMEsHistory) holder);
		} else
			throw new IllegalArgumentException(holder.getClass() + " is not a valid model for a reference report");
	}

	private Object doUpdateReference(Object holder) throws Throwable {
		if (holder == null)
			throw new IllegalArgumentException("Cannot update a NULL or empty reference report");

		if (holder instanceof GeneralMeasure) {
			return this.vmeDao.update((GeneralMeasure) holder);
		} else if (holder instanceof InformationSource) {
			return this.vmeDao.update((InformationSource) holder);
		} else if (holder instanceof FisheryAreasHistory) {
			return this.vmeDao.update((FisheryAreasHistory) holder);
		} else if (holder instanceof VMEsHistory) {
			return this.vmeDao.update((VMEsHistory) holder);
		} else
			throw new IllegalArgumentException(holder.getClass() + " is not a valid model for a reference report");
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
