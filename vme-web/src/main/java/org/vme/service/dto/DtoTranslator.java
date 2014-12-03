package org.vme.service.dto;

import java.util.Calendar;

import javax.inject.Inject;

import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.reference.VmeScope;
import org.fao.fi.vme.domain.model.reference.VmeType;
import org.fao.fi.vme.domain.support.ValidityPeriodUtil;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.webservice.GeneralMeasureType;
import org.fao.fi.vme.webservice.SpecificMeasureType;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.ReferenceServiceException;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.figis.FigisDao;

public class DtoTranslator {

	@Inject
	@ConceptProvider
	ReferenceDaoImpl referenceDAO;

	@Inject
	private FigisDao figisDao;

	private static final MultiLingualStringUtil UTIL = new MultiLingualStringUtil();
	private static final ValidityPeriodUtil VUTIL = new ValidityPeriodUtil();

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private static final String UNABLE2RETRIVE = "Unable to retrieve reference {} by ID {}: {}";

	public SpecificMeasureDto doTranslate4Sm(SpecificMeasure sm) {
		SpecificMeasureDto smDto = new SpecificMeasureDto();
		smDto.setText(UTIL.getEnglish(sm.getVmeSpecificMeasure()));
		smDto.setYear(sm.getYear());
		smDto.setValidityPeriodStart(sm.getValidityPeriod().getBeginDate());
		smDto.setValidityPeriodEnd(sm.getValidityPeriod().getEndDate());
		if (sm.getReviewYear() != null) {
			smDto.setReviewYear(sm.getReviewYear());
		}
		if (sm.getInformationSource() != null && sm.getInformationSource().getUrl() != null) {
			smDto.setSourceURL(sm.getInformationSource().getUrl().toExternalForm());
		}

		VmeObservation obs = figisDao.findFirstVmeObservation(sm.getVme().getId(), sm.getYear());

		if (obs != null) {
			smDto.setFactsheetURL(factsheetURL(obs));
		}
		return smDto;
	}

	public SpecificMeasureType doTranslate4SmType(SpecificMeasure sm) {
		SpecificMeasureType smt = new SpecificMeasureType();
		smt.setId(sm.getVme().getId().intValue());
		smt.setLang("en");
		if (sm.getInformationSource() != null && sm.getInformationSource().getUrl() != null) {
			smt.setMeasureSourceUrl(sm.getInformationSource().getUrl().toExternalForm());
		}
		smt.setMeasureText(UTIL.getEnglish(sm.getVmeSpecificMeasure()));

		VmeObservation obs = figisDao.findFirstVmeObservation(sm.getVme().getId(), sm.getYear());

		if (obs != null) {
			smt.setOid(obs.getId().getObservationId().intValue());
		}
		smt.setValidityPeriodStart(String.valueOf(sm.getValidityPeriod().getBeginDate()));
		smt.setValidityPeriodEnd(String.valueOf(sm.getValidityPeriod().getEndDate()));
		smt.setYear(sm.getYear());
		return smt;
	}

	public VmeDto doTranslate4Vme(Vme vme, int year) {

		if (year == 0) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}

		VmeDto res = new VmeDto();
		res.setVmeId(vme.getId());

		try {
			res.setScope(referenceDAO.getReferenceByID(VmeScope.class, vme.getScope()).getName());
		} catch (Exception e1) {
			log.error(UNABLE2RETRIVE, VmeScope.class, vme.getScope(), e1.getMessage(), e1);
		}

		res.setInventoryIdentifier(vme.getInventoryIdentifier());
		res.setLocalName(UTIL.getEnglish(vme.getName()));
		res.setEnvelope("");
		if (vme.getRfmo() != null) {
			String authorityAcronym = vme.getRfmo().getId();
			try {
				Authority authority = (Authority) referenceDAO.getReferenceByAcronym(Authority.class, authorityAcronym);

				if (authority == null) {
					log.warn("authority == null");
				} else {
					res.setOwner(authority.getName());
				}
			} catch (ReferenceServiceException e) {
				res.setOwner(authorityAcronym);
				log.error(e.getMessage(), e);
			}
		}

		VmeObservation vo = figisDao.findFirstVmeObservation(vme.getId(), year);
		if (vo != null) {
			res.setFactsheetUrl(factsheetURL(vo));
		} else {
			res.setFactsheetUrl("");
		}

		res.setGeoArea(UTIL.getEnglish(vme.getGeoArea()));

		res.setValidityPeriodFrom(vme.getValidityPeriod().getBeginDate());
		res.setValidityPeriodTo(vme.getValidityPeriod().getEndDate());

		if (vme.getAreaType() != null) {
			try {
				res.setVmeType(referenceDAO.getReferenceByID(VmeType.class, vme.getAreaType()).getName());
			} catch (Exception e) {
				log.error(UNABLE2RETRIVE, VmeType.class, vme.getAreaType(), e.getMessage(), e);
			}
		}

		if (!vme.getGeoRefList().isEmpty()) {
			GeoRef first = vme.getGeoRefList().get(0);

			res.setYear(first.getYear());
			res.setGeographicFeatureId(first.getGeographicFeatureID());
		} else {
			res.setGeographicFeatureId("");
		}
		return res;
	}

	public GeneralMeasureDto doTranslate4Gm(GeneralMeasure gm) {

		GeneralMeasureDto gmDto = new GeneralMeasureDto();
		gmDto.setYear(gm.getYear());
		gmDto.setValidityPeriodStart(VUTIL.getBeginYear(gm.getValidityPeriod()));
		gmDto.setValidityPeriodEnd(VUTIL.getBeginYear(gm.getValidityPeriod()));
		gmDto.setFishingArea(UTIL.getEnglish(gm.getFishingArea()));
		gmDto.setExploratoryFishingProtocol(UTIL.getEnglish(gm.getExplorataryFishingProtocol()));
		gmDto.setVmeEncounterProtocol(UTIL.getEnglish(gm.getVmeEncounterProtocol()));
		gmDto.setVmeIndicatorSpecies(UTIL.getEnglish(gm.getVmeIndicatorSpecies()));
		gmDto.setThreshold(UTIL.getEnglish(gm.getVmeThreshold()));

		if (figisDao.findExactVmeObservation(gm.getRfmo().getListOfManagedVmes().get(0).getId(), gm.getYear()) != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(gm.getValidityPeriod().getBeginDate());

			gmDto.setFactsheetURL(factsheetURL(figisDao.findExactVmeObservation(gm.getRfmo().getListOfManagedVmes()
					.get(0).getId(), calendar.get(Calendar.YEAR))));
		}
		return gmDto;

	}

	public String factsheetURL(VmeObservation vo) {
		if (vo != null) {
			return "fishery/vme/" + vo.getId().getVmeId() + "/" + vo.getId().getObservationId() + "/en";
		} else {
			return "";
		}
	}

	public GeneralMeasureType doTranslate4GmType(Long vmeId, GeneralMeasure gm) {
		GeneralMeasureType gmt = new GeneralMeasureType();
		gmt.setLang("en");
		gmt.setId(vmeId.intValue());
		// gmt.setMeasureSourceUrl(gm.getInformationSourceList().get(0).getUrl().toExternalForm());
		if (figisDao.findFirstVmeObservation(vmeId, gm.getYear()) != null) {
			gmt.setOid(figisDao.findExactVmeObservation(vmeId, gm.getYear()).getId().getObservationId().intValue());
		}
		gmt.setValidityPeriodStart(String.valueOf(gm.getValidityPeriod().getBeginDate()));
		gmt.setValidityPeriodEnd(String.valueOf(gm.getValidityPeriod().getEndDate()));
		gmt.setYear(gm.getYear());
		return gmt;
	}
}
