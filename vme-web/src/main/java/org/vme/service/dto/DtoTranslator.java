package org.vme.service.dto;

import java.util.Calendar;

import javax.inject.Inject;

import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.dto.VmeDto;
import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.reference.VmeScope;
import org.fao.fi.vme.domain.model.reference.VmeType;
import org.fao.fi.vme.domain.support.ValidityPeriodUtil;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.joda.time.DateTime;
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
	
	private MultiLingualStringUtil UTIL = new MultiLingualStringUtil();
	private ValidityPeriodUtil VUTIL = new ValidityPeriodUtil();
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	public SpecificMeasureDto doTranslate4Sm(SpecificMeasure sm){
		SpecificMeasureDto smDto = new SpecificMeasureDto();
		smDto.setText(UTIL.getEnglish(sm.getVmeSpecificMeasure()));
		smDto.setYear(sm.getYear());
		smDto.setValidityPeriodStart(VUTIL.getBeginYear(sm.getValidityPeriod()));
		smDto.setValidityPeriodEnd(VUTIL.getEndYear(sm.getValidityPeriod()));
		smDto.setSourceURL(sm.getInformationSource().getUrl().toExternalForm());
		return smDto;
	}
	
	/* TODO further implementation and refactoring for the VmeSearchWs.class
	 * by implementing a Vme -> VmeDto translator's method
	 */
	
	public VmeDto doTranslate4Vme(Vme vme, int year){
		
		if (year == 0) {
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		
		VmeDto res = new VmeDto();
		res.setVmeId(vme.getId());
		
		try {
			res.setScope(referenceDAO.getReferenceByID(VmeScope.class, vme.getScope()).getName());
		} catch (Exception e1) {
			throw new VmeException(e1);
		}
		
		res.setInventoryIdentifier(vme.getInventoryIdentifier());
		res.setLocalName(UTIL.getEnglish(vme.getName()));
		res.setEnvelope("");
		if (vme.getRfmo() != null) {
			String authority_acronym = vme.getRfmo().getId();
			try {
				Authority authority = (Authority) referenceDAO
						.getReferenceByAcronym(Authority.class, authority_acronym);

				if (authority == null) {
				} else
					res.setOwner(authority.getName());
			} catch (ReferenceServiceException e) {
				res.setOwner(authority_acronym);
				e.printStackTrace();
			}
		}

		VmeObservation vo = figisDao.findFirstVmeObservation(vme.getId(), Integer.toString(year));
		if (vo != null) {
			res.setFactsheetUrl("fishery/vme/" + vo.getId().getVmeId() + "/" + vo.getId().getObservationId() + "/en");
		} else {
			res.setFactsheetUrl("");
		}

		res.setGeoArea(UTIL.getEnglish(vme.getGeoArea()));
		res.setValidityPeriodFrom((new DateTime(vme.getValidityPeriod().getBeginDate())).getYear());
		res.setValidityPeriodTo((new DateTime(vme.getValidityPeriod().getEndDate())).getYear());
		
		if(vme.getAreaType() != null) {
			try {
				res.setVmeType(referenceDAO.getReferenceByID(VmeType.class, vme.getAreaType()).getName());
			} catch(Exception e) {
				LOG.error("Unable to retrieve reference {} by ID {}: {}", VmeType.class, vme.getAreaType(), e.getMessage(), e);
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
	
}
