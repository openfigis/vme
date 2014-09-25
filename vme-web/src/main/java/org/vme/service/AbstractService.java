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
import org.fao.fi.vme.domain.model.MultiLingualString;
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

abstract class AbstractService {

	@Inject
	@ConceptProvider
	ReferenceDaoImpl refDao;

	private static final Logger LOG = LoggerFactory.getLogger(AbstractService.class);
	private static final String UNABLE2RETRIVE = "Unable to retrieve reference {} by ID {}: {}";

	public List<Vme> filterVmePerRfmo(List<Vme> vmeList, String authorityAcronym) {
		List<Vme> vmeListPerRfmo = new ArrayList<Vme>();

		for (Vme v : vmeList) {
			if (authorityAcronym.equals(v.getRfmo().getId())) {
				vmeListPerRfmo.add(v);
			}
		}
		return vmeListPerRfmo;
	}

	public List<Vme> filterVmePerScope(List<Vme> vmeList, String scope) {
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

	public void filterVmePerId(List<Vme> vmeList, Long vmeId) {
		List<Vme> temp = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			if (vmeId.equals(vme.getId())) {
				temp.add(vme);
			}
		}
		vmeList.clear();
		vmeList.addAll(temp);
	}

	public void filterVmePerInventoryIdentifier(List<Vme> vmeList, String inventoryIdentifier) {
		List<Vme> temp = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			if (vme.getInventoryIdentifier().equals(inventoryIdentifier)) {
				temp.add(vme);
			}
		}
		vmeList.clear();
		vmeList.addAll(temp);
	}

	public void filterVmePerType(List<Vme> vmeList, int type) {
		List<Vme> temp = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			if (vme.getAreaType() != null && vme.getAreaType().intValue() == type) {
				temp.add(vme);
			}
		}
		vmeList.clear();
		vmeList.addAll(temp);
	}

	public void filterVmePerRfmoById(List<Vme> vmeList, int authority) {
		List<Vme> temp = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			if (getAuthorityIdByAcronym(vme.getRfmo().getId()) == authority) {
				temp.add(vme);
			}
		}
		vmeList.clear();
		vmeList.addAll(temp);
	}

	public void filterVmePerRelevantText(List<Vme> vmeList, String text) {
		List<Vme> temp = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			if (vmeContainRelevantText(vme, text)) {
				temp.add(vme);
			}
		}
		vmeList.clear();
		vmeList.addAll(temp);
	}

	public void filterVmePerGeographFeatureId(List<Vme> vmeList, String geographicFeatureId) {
		List<Vme> temp = new ArrayList<Vme>();
		for (Vme vme : vmeList) {
			boolean found = false;
			for (GeoRef gr : vme.getGeoRefList()) {
				if (gr.getGeographicFeatureID().equals(geographicFeatureId)) {
					found = true;
				}
			}
			if (found) {
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
				if (cr == criteria) {
					found = true;
				}
			}
			if (found) {
				temp.add(vme);
			}
		}
		vmeList.clear();
		vmeList.addAll(temp);
	}

	public void filterByRequestParam(List<Vme> vmeList, int authority, int type, int criteria, String text) {

		if (authority > 0) {
			filterVmePerRfmoById(vmeList, authority);
		}

		if (type > 0) {
			filterVmePerType(vmeList, type);
		}

		if (criteria > 0) {
			filterVmePerCriteria(vmeList, criteria);
		}

		if (text != null) {
			filterVmePerRelevantText(vmeList, text);
		}

	}

	public long getAuthorityIdByAcronym(String authorityAcronym) {

		List<Authority> authorities = refDao.getAllAuthorities();

		for (Authority authority : authorities) {
			if (authorityAcronym.equals(authority.getAcronym())) {
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

		if (textInAreaType(vme, text) || textInCriteria(vme, text) || textInMultilingualString(vme.getGeoArea(), text)
				|| textInGeoRef(vme, text) || textInInventoryIdentifier(vme, text)
				|| textInMultilingualString(vme.getName(), text) || textInProfileList(vme, text)
				|| textInRfmo(vme, text) || textInSpecificMeasureList(vme, text)) {
			return true;
		} else {
			return false;
		}

	}

	private boolean textInSpecificMeasureList(Vme vme, String text) {
		if (vme.getSpecificMeasureList() != null) {
			for (SpecificMeasure specificMeasure : vme.getSpecificMeasureList()) {
				if (specificMeasure.getVmeSpecificMeasure() != null) {
					if (textInMultilingualString(specificMeasure.getVmeSpecificMeasure(), text)) {
						return true;
					}
				}

				if (specificMeasure.getInformationSource() != null) {
					if (textInInformationSource(specificMeasure.getInformationSource(), text)) {
						return true;
					}
				}

				if (specificMeasure.getInformationSource() != null
						&& specificMeasure.getInformationSource().getCommittee() != null) {
					if (textInMultilingualString(specificMeasure.getInformationSource().getCommittee(), text)) {
						return true;
					}
				}

				if (specificMeasure.getInformationSource() != null
						&& specificMeasure.getInformationSource().getReportSummary() != null) {
					if (textInMultilingualString(specificMeasure.getInformationSource().getReportSummary(), text)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean textInMultilingualString(MultiLingualString mString, String text) {
		if (mString != null && mString.getStringMap() != null) {
			for (String element : mString.getStringMap().values()) {
				if (StringUtils.containsIgnoreCase(element, text)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean textInInformationSource(InformationSource informationSource, String text) {
		if (informationSource.getCitation() != null) {
			if (textInMultilingualString(informationSource.getCitation(), text)) {
				return true;
			}
		}
		if (informationSource.getPublicationYear() != null
				&& StringUtils.containsIgnoreCase(Integer.toString(informationSource.getPublicationYear()), text)) {
			return true;
		}
		if (StringUtils.containsIgnoreCase(informationSource.getUrl() != null ? informationSource.getUrl()
				.toExternalForm() : "", text)) {
			return true;
		}
		return false;
	}

	private boolean textInRfmo(Vme vme, String text) {
		if (vme.getRfmo() != null) {
			for (GeneralMeasure generalMeasure : vme.getRfmo().getGeneralMeasureList()) {
				if (generalMeasure.getFishingArea() != null) {
					if (textInMultilingualString(generalMeasure.getFishingArea(), text)) {
						return true;
					}
				}

				if (generalMeasure.getExplorataryFishingProtocol() != null) {
					if (textInMultilingualString(generalMeasure.getExplorataryFishingProtocol(), text)) {
						return true;
					}
				}

				if (generalMeasure.getVmeEncounterProtocol() != null) {
					if (textInMultilingualString(generalMeasure.getVmeEncounterProtocol(), text)) {
						return true;
					}
				}

				if (generalMeasure.getVmeIndicatorSpecies() != null) {
					if (textInMultilingualString(generalMeasure.getVmeIndicatorSpecies(), text)) {
						return true;
					}
				}

				if (generalMeasure.getVmeThreshold() != null) {
					if (textInMultilingualString(generalMeasure.getVmeThreshold(), text)) {
						return true;
					}
				}

				if (generalMeasure.getInformationSourceList() != null) {
					for (InformationSource informationSource : generalMeasure.getInformationSourceList()) {

						if (informationSource.getCitation() != null) {
							if (textInMultilingualString(informationSource.getCitation(), text)) {
								return true;
							}
						}
						if (informationSource.getCommittee() != null) {
							if (textInMultilingualString(informationSource.getCommittee(), text)) {
								return true;
							}
						}

						if (informationSource.getReportSummary() != null) {
							if (textInMultilingualString(informationSource.getReportSummary(), text)) {
								return true;
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
		return false;
	}

	private boolean textInProfileList(Vme vme, String text) {
		if (vme.getProfileList() != null) {
			for (Profile profile : vme.getProfileList()) {
				if (profile.getDescriptionBiological() != null) {
					if (textInMultilingualString(profile.getDescriptionBiological(), text)) {
						return true;
					}
				}
				if (profile.getDescriptionImpact() != null) {
					if (textInMultilingualString(profile.getDescriptionImpact(), text)) {
						return true;
					}
				}
				if (profile.getDescriptionPhisical() != null) {
					if (textInMultilingualString(profile.getDescriptionPhisical(), text)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean textInInventoryIdentifier(Vme vme, String text) {
		if (StringUtils.containsIgnoreCase(vme.getInventoryIdentifier(), text)) {
			return true;
		}
		return false;
	}

	private boolean textInGeoRef(Vme vme, String text) {
		for (GeoRef geoRef : vme.getGeoRefList()) {
			if (StringUtils.containsIgnoreCase(geoRef.getGeographicFeatureID(), text)) {
				return true;
			}
		}
		return false;
	}

	private boolean textInCriteria(Vme vme, String text) {
		if (vme.getCriteria() != null && !vme.getCriteria().isEmpty()) {
			VmeCriteria selected = null;

			for (Long criteriaId : vme.getCriteria()) {
				try {
					selected = refDao.getReferenceByID(VmeCriteria.class, criteriaId);

					if (selected != null && selected.getName() != null) {
						if (StringUtils.containsIgnoreCase(selected.getName(), text)) {
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
		return false;
	}

	private boolean textInAreaType(Vme vme, String text) {
		if (vme.getAreaType() != null) {
			VmeType selected = null;

			try {
				selected = refDao.getReferenceByID(VmeType.class, vme.getAreaType());

				if (selected != null && selected.getName() != null) {
					if (StringUtils.containsIgnoreCase(selected.getName(), text)) {
						return true;
					}
				} else {
					LOG.warn("Selected area type is either NULL or has a NULL name");
				}
			} catch (Exception e) {
				LOG.error(UNABLE2RETRIVE, VmeType.class, vme.getAreaType(), e.getMessage(), e);
			}
		}
		return false;
	}

}
