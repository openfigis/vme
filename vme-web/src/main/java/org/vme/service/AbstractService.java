package org.vme.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.fao.fi.vme.VmeException;
import org.fao.fi.vme.domain.model.Authority;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.reference.VmeCriteria;
import org.fao.fi.vme.domain.model.reference.VmeScope;
import org.fao.fi.vme.domain.model.reference.VmeType;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.ReferenceServiceException;
import org.vme.dao.impl.jpa.ReferenceDaoImpl;
import org.vme.dao.sources.vme.VmeDao;

abstract class AbstractService {

	@Inject
	@ConceptProvider
	ReferenceDaoImpl refDao;

	private static final Logger LOG = LoggerFactory.getLogger(VmeDao.class);
	private static final String UNABLE2RETRIVE = "Unable to retrieve reference {} by ID {}: {}";

	public List<Vme> filterVmePerRfmo(List<Vme> vmeList, String authorityAcronym){
		List<Vme> vmeListPerRfmo = new ArrayList<Vme>();

		for (Vme v : vmeList) {
			if (v.getRfmo().getId().equals(authorityAcronym)) {
				vmeListPerRfmo.add(v);
			}
		}
		return vmeListPerRfmo;
	}

	public List<Vme> filterVmePerScope(List<Vme> vmeList, String scope){
		List<Vme> vmeListPerScope = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			try {
				if (refDao.getReferenceByID(VmeScope.class, vme.getScope()).getName().equals(scope)) {
					vmeListPerScope.add(vme);
				}
			} catch (ReferenceServiceException e) {
				throw new VmeException(e);
			}
		}
		return vmeListPerScope;
	}

	public void filterVmePerId(List<Vme> vmeList, Long vmeId){
		List<Vme> temp = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			if(vmeId.equals(vme.getId())){
				temp.add(vme);
			}
		}
		vmeList.clear();
		vmeList.addAll(temp);
	}

	public void filterVmePerInventoryIdentifier(List<Vme> vmeList, String inventoryIdentifier){
		List<Vme> temp = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			if(vme.getInventoryIdentifier().equals(inventoryIdentifier)){
				temp.add(vme);
			}
		}
		vmeList.clear();
		vmeList.addAll(temp);
	}

	public void filterVmePerType(List<Vme> vmeList, int type) {
		List<Vme> temp = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			if(vme.getAreaType()!=null && vme.getAreaType().intValue() == type){
				temp.add(vme);
			}
		}
		vmeList.clear();
		vmeList.addAll(temp);
	}

	public void filterVmePerRfmoById(List<Vme> vmeList, int authority) {
		List<Vme> temp = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			if(getAuthorityIdByAcronym(vme.getRfmo().getId()) == authority){
				temp.add(vme);
			}
		}
		vmeList.clear();
		vmeList.addAll(temp);
	}

	public void filterVmePerRelevantText(List<Vme> vmeList, String text) {
		List<Vme> temp = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			if(vmeContainRelevantText(vme, text)){
				temp.add(vme);
			}
		}
		vmeList.clear();
		vmeList.addAll(temp);
	}

	public void filterVmePerGeographFeatureId(List<Vme> vmeList, String geographicFeatureId){
		List<Vme> temp = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			boolean found = false;
			for (GeoRef gr : vme.getGeoRefList()) {
				if(gr.getGeographicFeatureID().equals(geographicFeatureId)){
					found = true;
				}
			}
			if (found){
				temp.add(vme);
			}
		}
		vmeList.clear();
		vmeList.addAll(temp);
	}

	public void filterVmePerCriteria(List<Vme> vmeList, int criteria) {
		List<Vme> temp = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			boolean found = false;
			for (Long cr : vme.getCriteria()) {
				if(cr == criteria){
					found = true;
				}
			}
			if (found){
				temp.add(vme);
			}
		}
		vmeList.clear();
		vmeList.addAll(temp);
	}

	public void filterByRequestParam(List<Vme> vmeList, int authority, int type, int criteria, String text) {

		if(authority > 0){
			filterVmePerRfmoById(vmeList, authority);
		}

		if(type > 0){
			filterVmePerType(vmeList, type);
		}

		if(criteria > 0){
			filterVmePerCriteria(vmeList, criteria);
		}

		if(text != null){
			filterVmePerRelevantText(vmeList, text);
		}

	}

	public long getAuthorityIdByAcronym(String authorityAcronym) {

		List<Authority> authorities = refDao.getAllAuthorities();

		for (Authority authority : authorities) {
			if (authority.getAcronym().equals(authorityAcronym)) {
				return authority.getId();
			}
		}
		return 0;
	}

	public String dataString() {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		return dateFormat.format(cal.getTime());
	}

	public boolean vmeContainRelevantText(Vme vme, String text) {

		if(vme.getAreaType() != null) {
			VmeType selected = null;

			try {
				selected = refDao.getReferenceByID(VmeType.class, vme.getAreaType());

				if(selected != null && selected.getName() != null) {
					if(StringUtils.containsIgnoreCase(selected.getName(), text)) {
						return true;
					}
				} else {
					LOG.warn("Selected area type is either NULL or has a NULL name");
				}
			} catch (Exception e) {
				LOG.error(UNABLE2RETRIVE, VmeType.class, vme.getAreaType(), e.getMessage(), e);
			}
		}

		if(vme.getCriteria() != null && !vme.getCriteria().isEmpty()) {
			VmeCriteria selected = null;

			for(Long criteriaId : vme.getCriteria()) {
				try {
					selected = refDao.getReferenceByID(VmeCriteria.class, criteriaId);

					if(selected != null && selected.getName() != null) {
						if(StringUtils.containsIgnoreCase(selected.getName(), text)) {
							return true;
						}
					} else {
						LOG.warn("Selected criteria is either NULL or has a NULL name");
					}
				} catch (Exception e) {
					LOG.error(UNABLE2RETRIVE, VmeCriteria.class, criteriaId, e.getMessage(), e);
				}
			}
		}
		if(vme.getGeoArea() != null){
			for (String element : vme.getGeoArea().getStringMap().values()) {
				if (StringUtils.containsIgnoreCase(element, text)) {
					return true;
				}
			}
		}

		for (GeoRef geoRef : vme.getGeoRefList()) {
			if (StringUtils.containsIgnoreCase(geoRef.getGeographicFeatureID(), text)) {
				return true;
			}
		}
		if (StringUtils.containsIgnoreCase(vme.getInventoryIdentifier(), text)) {
			return true;
		}
		for (String element : vme.getName().getStringMap().values()) {
			if (StringUtils.containsIgnoreCase(element, text)) {
				return true;
			}
		}

		if (vme.getProfileList() != null) {
			for (Profile profile : vme.getProfileList()) {
				if (profile.getDescriptionBiological() != null) {
					for (String element : profile.getDescriptionBiological().getStringMap().values()) {
						if (StringUtils.containsIgnoreCase(element, text)) {
							return true;
						}
					}
				}
				if (profile.getDescriptionImpact() != null) {
					for (String element : profile.getDescriptionImpact().getStringMap().values()) {
						if (StringUtils.containsIgnoreCase(element, text)) {
							return true;
						}
					}
				}
				if (profile.getDescriptionPhisical() != null) {
					for (String element : profile.getDescriptionPhisical().getStringMap().values()) {
						if (StringUtils.containsIgnoreCase(element, text)) {
							return true;
						}
					}
				}
			}
		}

		if (vme.getRfmo() != null) {
			for (GeneralMeasure generalMeasure : vme.getRfmo().getGeneralMeasureList()) {
				if (generalMeasure.getFishingArea() != null) {
					for (String element : generalMeasure.getFishingArea().getStringMap().values()) {
						if (StringUtils.containsIgnoreCase(element, text)) {
							return true;
						}
					}
				}

				if (generalMeasure.getExplorataryFishingProtocol() != null) {
					for (String element : generalMeasure.getExplorataryFishingProtocol().getStringMap().values()) {
						if (StringUtils.containsIgnoreCase(element, text)) {
							return true;
						}
					}
				}
				if (generalMeasure.getVmeEncounterProtocol() != null) {
					for (String element : generalMeasure.getVmeEncounterProtocol().getStringMap().values()) {
						if (StringUtils.containsIgnoreCase(element, text)) {
							return true;
						}
					}
				}
				if (generalMeasure.getVmeIndicatorSpecies() != null) {
					for (String element : generalMeasure.getVmeIndicatorSpecies().getStringMap().values()) {
						if (StringUtils.containsIgnoreCase(element, text)) {
							return true;
						}
					}
				}

				if (generalMeasure.getVmeThreshold() != null) {
					for (String element : generalMeasure.getVmeThreshold().getStringMap().values()) {
						if (StringUtils.containsIgnoreCase(element, text)) {
							return true;
						}
					}
				}

				if (generalMeasure.getInformationSourceList() != null) {
					for (InformationSource informationSource : generalMeasure.getInformationSourceList()) {

						if (informationSource.getCitation() != null) {
							for (String element : informationSource.getCitation().getStringMap().values()) {
								if (StringUtils.containsIgnoreCase(element, text)) {
									return true;
								}
							}
						}
						if (informationSource.getCommittee() != null) {
							for (String element : informationSource.getCommittee().getStringMap().values()) {
								if (StringUtils.containsIgnoreCase(element, text)) {
									return true;
								}
							}
						}

						if (informationSource.getReportSummary() != null) {
							for (String element : informationSource.getReportSummary().getStringMap().values()) {
								if (StringUtils.containsIgnoreCase(element, text)) {
									return true;
								}
							}
						}
						if (StringUtils.containsIgnoreCase(Integer.toString(informationSource.getPublicationYear()),
								text)) {
							return true;
						}
						if (StringUtils.containsIgnoreCase(informationSource.getUrl() != null ? informationSource
								.getUrl().toExternalForm() : "", text)) {
							return true;
						}
					}
				}

			}
		}

		if (vme.getSpecificMeasureList() != null) {
			for (SpecificMeasure specificMeasure : vme.getSpecificMeasureList()) {
				if (specificMeasure.getVmeSpecificMeasure() != null) {
					for (String element : specificMeasure.getVmeSpecificMeasure().getStringMap().values()) {
						if (StringUtils.containsIgnoreCase(element, text)) {
							return true;
						}
					}
				}
				if (specificMeasure.getInformationSource() != null) {
					if (specificMeasure.getInformationSource().getCitation() != null) {
						for (String element : specificMeasure.getInformationSource().getCitation().getStringMap()
								.values()) {
							if (StringUtils.containsIgnoreCase(element, text)) {
								return true;
							}
						}
					}
					if (specificMeasure.getInformationSource().getPublicationYear() != null
							&& StringUtils
							.containsIgnoreCase(Integer.toString(specificMeasure.getInformationSource()
									.getPublicationYear()), text)) {
						return true;
					}
					if (StringUtils.containsIgnoreCase(
							specificMeasure.getInformationSource().getUrl() != null ? specificMeasure
									.getInformationSource().getUrl().toExternalForm() : "", text)) {
						return true;
					}
				}
				if (specificMeasure.getInformationSource() != null
						&& specificMeasure.getInformationSource().getCommittee() != null) {
					for (String element : specificMeasure.getInformationSource().getCommittee().getStringMap().values()) {
						if (StringUtils.containsIgnoreCase(element, text)) {
							return true;
						}
					}
				}

				if (specificMeasure.getInformationSource() != null
						&& specificMeasure.getInformationSource().getReportSummary() != null) {
					for (String element : specificMeasure.getInformationSource().getReportSummary().getStringMap()
							.values()) {
						if (StringUtils.containsIgnoreCase(element, text)) {
							return true;
						}
					}
				}

			}
		}
		return false;
	}
}

