package org.fao.fi.vme.batch.sync2.mapping.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.JAXBElement;

import org.fao.fi.figis.devcon.BiblioEntry;
import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.FigisID;
import org.fao.fi.figis.devcon.ForeignID;
import org.fao.fi.figis.devcon.GeoReference;
import org.fao.fi.figis.devcon.Max;
import org.fao.fi.figis.devcon.Min;
import org.fao.fi.figis.devcon.ObjectFactory;
import org.fao.fi.figis.devcon.OrgRef;
import org.fao.fi.figis.devcon.Range;
import org.fao.fi.figis.devcon.Sources;
import org.fao.fi.figis.devcon.SpatialScale;
import org.fao.fi.figis.devcon.VME;
import org.fao.fi.figis.devcon.VMECriteria;
import org.fao.fi.figis.devcon.VMEIdent;
import org.fao.fi.figis.devcon.VMEType;
import org.fao.fi.figis.devcon.WaterAreaRef;
import org.fao.fi.vme.batch.sync2.mapping.BiblioEntryFromInformationSource;
import org.fao.fi.vme.batch.sync2.mapping.DisseminationYearSlice;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.reference.VmeCriteria;
import org.fao.fi.vme.domain.model.reference.VmeScope;
import org.fao.fi.vme.domain.model.reference.VmeType;
import org.fao.fi.vme.domain.support.VmeSimpleDateFormat;
import org.fao.fi.vme.domain.util.Lang;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.gcube.application.rsg.support.compiler.bridge.annotations.ConceptProvider;
import org.purl.dc.elements._1.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.dao.ReferenceDAO;

/**
 * Abstract class for FigisDocBuilderRegolatory and FigisDocBuilderVme
 * 
 * 
 * @author Erik van Ingen
 * 
 * 
 */
abstract class FigisDocBuilderAbstract {

	static final private Logger LOG = LoggerFactory.getLogger(FigisDocBuilderAbstract.class);

	@Inject
	@ConceptProvider
	protected ReferenceDAO refDao;

	protected ObjectFactory f = new ObjectFactory();
	protected MultiLingualStringUtil u = new MultiLingualStringUtil();
	protected EnglishTextUtil ut = new EnglishTextUtil();
	protected JAXBElementUtil uj = new JAXBElementUtil();
	protected GeneralMeasureManagementMethodEntryBuilder mmeBuilder = new GeneralMeasureManagementMethodEntryBuilder();
	protected CurrentDate currentDate = new CurrentDate();
	protected BiblioEntryFromInformationSource bu = new BiblioEntryFromInformationSource();
	protected VmeSimpleDateFormat du = new VmeSimpleDateFormat();

	public static final String VULNERABLE_MARINE_ECOSYSTEMS = "Vulnerable Marine Ecosystems";

	public static final String VME_SPECIFIC_MEASURES = "VME-specific measures";

	protected String safeGetMultilingualString(MultiLingualString mls) {
		return this.safeGetMultilingualString(mls, Lang.EN);
	}

	protected String safeGetMultilingualString(MultiLingualString mls, Integer lang) {
		if (mls != null) {
			return mls.getStringMap().get(lang);
		}

		return null;
	}

	public abstract void docIt(Vme vme, DisseminationYearSlice disseminationYearSlice, FIGISDoc figisDoc);

	/**
	 * Adds a Vme to the FIGISDoc
	 * 
	 * VME Identifier fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:FigisID
	 * 
	 * inventoryIdentifier
	 * fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:ForeignID@CodeSystem="invid"/@Code
	 * 
	 * name fi:FIGISDoc/fi:VME/fi:VMEIdent/dc:Title
	 * 
	 * geographicLayerId
	 * fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:WaterAreaRef/fi:ForeignID
	 * 
	 * @CodeSystem="vme"/@Code
	 * 
	 *                         areaType fi:FIGISDoc/fi:VME/fi:VMEIdent/VMEType/@Value
	 * 
	 *                         criteria
	 *                         fi:FIGISDoc/fi:VME/fi:VMEIdent/VMECriteria/@Value
	 * 
	 *                         ValidityPeriod/beginYear
	 *                         fi:FIGISDoc/fi:VME/fi:VMEIdent
	 *                         /fi:Range@Type="Time"/fi:Min
	 * 
	 *                         ValidityPeriod/endYear
	 *                         fi:FIGISDoc/fi:VME/fi:VMEIdent
	 *                         /fi:Range@Type="Time"/fi:Max
	 * 
	 * 
	 *                         Observation/Year
	 *                         fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:ReportingYear
	 * 
	 * 
	 * @param vmeDomain
	 * @param i
	 * @param figisDoc
	 */
	public void vme(Vme vmeDomain, GeoRef georef, int year, FIGISDoc figisDoc) {
		VMEIdent vmeIdent = new VMEIdent();

		VmeScope scope = null;
		try {
			scope = vmeDomain.getScope() == null ? null : refDao.getReferenceByID(VmeScope.class, vmeDomain.getScope());
		} catch (Exception e) {
			LOG.error("Unable to retrieve reference {} with ID {}: {}", VmeType.class, vmeDomain.getAreaType(),
					e.getMessage(), e);
		}

		// FigisID
		FigisID figisID = new FigisID();
		figisID.setContent(vmeDomain.getId().toString());

		// Title
		Title title = new Title();
		title.setContent(this.safeGetMultilingualString(vmeDomain.getName()));

		// ForeignID
		ForeignID vmeForeignID = new ForeignID();
		vmeForeignID.setCodeSystem("invid");
		vmeForeignID.setCode(vmeDomain.getInventoryIdentifier());

		// OrgRef
		ForeignID rfmoForeignID = f.createForeignID();
		rfmoForeignID.setCodeSystem("acronym");
		rfmoForeignID.setCode(vmeDomain.getRfmo().getId());

		OrgRef rfmoOrg = f.createOrgRef();
		rfmoOrg.getForeignIDsAndFigisIDsAndTitles().add(rfmoForeignID);

		// geoReference
		SpatialScale spatialScale = new SpatialScale();
		spatialScale.setValue("Regional");

		Title spatialScaleTitle = new Title();
		spatialScaleTitle.setContent(this.safeGetMultilingualString(vmeDomain.getGeoArea()));

		GeoReference geoReference = f.createGeoReference();
		geoReference.setSpatialScale(spatialScale);
		geoReference.setTitle(spatialScaleTitle);

		// WaterAreaRef
		WaterAreaRef waterAreaRef = new WaterAreaRef();
		ForeignID areaForeignID = new ForeignID();
		areaForeignID.setCodeSystem(scope.getCodeSystem());

		if (vmeDomain.getGeoRefList() != null && !vmeDomain.getGeoRefList().isEmpty()) {
			// this is the bug.
			areaForeignID.setCode(georef.getGeographicFeatureID());
			waterAreaRef.getFigisIDsAndForeignIDs().add(areaForeignID);
		}

		JAXBElement<Min> minJAXBElement = f.createRangeMin(f.createMin());
		uj.fillObject(du.createUiString(vmeDomain.getValidityPeriod().getBeginDate()), minJAXBElement);

		JAXBElement<Max> maxJAXBElement = f.createRangeMax(f.createMax());
		uj.fillObject(du.createUiString(vmeDomain.getValidityPeriod().getEndDate()), maxJAXBElement);

		Range range = f.createRange();
		range.setType("Time");
		range.getContent().add(minJAXBElement);
		range.getContent().add(maxJAXBElement);

		// VME Scope
		vmeIdent.setScope(scope.getName());

		// VME Type
		VMEType vmeType = new VMEType();
		VmeType refVmeType = null;

		if (vmeDomain.getAreaType() != null) {
			try {
				refVmeType = vmeDomain.getAreaType() == null ? null : refDao.getReferenceByID(VmeType.class,
						vmeDomain.getAreaType());
			} catch (Exception e) {
				LOG.error("Unable to retrieve reference {} with ID {}: {}", VmeType.class, vmeDomain.getAreaType(),
						e.getMessage(), e);
			}
		}

		if (refVmeType != null) {
			vmeType.setValue(refVmeType.getName());
		}

		// VME Criteria
		List<VMECriteria> vmeCriteria = new ArrayList<VMECriteria>();

		if (vmeDomain.getCriteria() != null) {
			VMECriteria criteria;
			VmeCriteria refCriteria;
			for (Long criteriaId : vmeDomain.getCriteria()) {
				try {
					refCriteria = criteriaId == null ? null : refDao.getReferenceByID(VmeCriteria.class, criteriaId);

					if (refCriteria != null) {
						criteria = new VMECriteria();
						criteria.setValue(refCriteria.getName());

						vmeCriteria.add(criteria);
					}
				} catch (Exception e) {
					LOG.error("Unable to retrieve reference {} with ID {}: {}", VmeCriteria.class, criteriaId,
							e.getMessage(), e);
				}
			}
		}

		// Order:
		// dc:Title
		// fi:Range
		// fi:ReportingYear
		// fi:OrgRef
		// fi:GeoReference
		// fi:VMEType
		// fi:VMECriteria

		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(figisID);
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(vmeForeignID);
		// OrgRef

		// WaterAreaRef
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(waterAreaRef);

		// dc:Title
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(title);

		// fi:Range
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(range);

		// fi:ReportingYear
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(Integer.toString(year));

		// fi:OrgRef
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(rfmoOrg);

		// fi:GeoReference
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(geoReference);

		// fi:VMEType
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(vmeType);

		// fi:VMECriteria
		// TODO: Fabio Fiorellato - CHECK WHETHER THIS IS CORRECT
		new AddWhenContentRule<Object>().check(vmeDomain.getCriteria()).beforeAdding(vmeCriteria)
				.to(vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves());

		VME vme = new VME();
		vme.setVMEIdent(vmeIdent);
		figisDoc.setVME(vme);

	}

	/**
	 * 
	 * -- date fi:FIGISDoc/fi:VME/fi:Sources/fi:BiblioEntry/dcterms:Created
	 * 
	 * committee
	 * fi:FIGISDoc/fi:VME/fi:Sources/fi:BiblioEntry/ags:CreatorCorporate
	 * 
	 * reportSummary
	 * fi:FIGISDoc/fi:VME/fi:Sources/fi:BiblioEntry/dcterms:Abstract
	 * 
	 * url fi:FIGISDoc/fi:VME/fi:Sources/fi:BiblioEntry/dc:Identifier@Type="URI"
	 * 
	 * citation fi:FIGISDoc/fi:VME/fi:Sources/fi:BiblioEntry/dcterms:
	 * bibliographicCitation
	 * 
	 * -- type fi:FIGISDoc/fi:VME/fi:Sources/fi:BiblioEntry/dc:Type
	 * 
	 * meetingStartDate - meetingEndDate
	 * fi:FIGISDoc/fi:VME/fi:Sources/fi:BiblioEntry/dc:Date *
	 * 
	 * @param infoSourceList
	 * @param i
	 * @param figisDoc
	 */
	public void informationSource(List<InformationSource> infoSourceList, int disseminationYear, FIGISDoc figisDoc) {

		if (infoSourceList != null) {
			Sources sources = f.createSources();

			Collections.sort(infoSourceList, new InformationSourceComparator());

			for (InformationSource infoSource : infoSourceList) {
				// Algorithm for "InformationSource" UML table: with regards of
				// the
				// meetings tab, provide all and only records with
				// "sourcetype=3"
				// and according to the selected year
				Integer oneBefore = Integer.valueOf(disseminationYear - 1);

				// FFiorellato: fixed following conditions. Previously they were
				// using '==' and a variable LHS that would produce NPE when the
				// LHS
				// was null
				if (infoSource != null
						&& infoSource.getSourceType() != null
						&& infoSource.getSourceType().isAMeetingDocument()
						&& (new Integer(disseminationYear).equals(infoSource.getPublicationYear()) || oneBefore
								.equals(infoSource.getPublicationYear()))) {
					BiblioEntry biblioEntry = bu.transform(infoSource);

					new AddWhenContentRule<Object>().check(biblioEntry).beforeAdding(biblioEntry)
							.to(sources.getTextsAndImagesAndTables());
				}

			}
			figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(sources);
		}
	}

}
