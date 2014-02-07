package org.fao.fi.vme.sync2.mapping.xml;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.fao.fi.figis.devcon.BiblioEntry;
import org.fao.fi.figis.devcon.CollectionRef;
import org.fao.fi.figis.devcon.CorporateCoverPage;
import org.fao.fi.figis.devcon.CoverPage;
import org.fao.fi.figis.devcon.DataEntry;
import org.fao.fi.figis.devcon.Editor;
import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.FigisID;
import org.fao.fi.figis.devcon.FisheryArea;
import org.fao.fi.figis.devcon.ForeignID;
import org.fao.fi.figis.devcon.GeneralBiology;
import org.fao.fi.figis.devcon.GeoForm;
import org.fao.fi.figis.devcon.GeoReference;
import org.fao.fi.figis.devcon.HabitatBio;
import org.fao.fi.figis.devcon.Impacts;
import org.fao.fi.figis.devcon.Management;
import org.fao.fi.figis.devcon.ManagementMethodEntry;
import org.fao.fi.figis.devcon.ManagementMethods;
import org.fao.fi.figis.devcon.Max;
import org.fao.fi.figis.devcon.Measure;
import org.fao.fi.figis.devcon.MeasureType;
import org.fao.fi.figis.devcon.Min;
import org.fao.fi.figis.devcon.ObjectFactory;
import org.fao.fi.figis.devcon.ObjectSource;
import org.fao.fi.figis.devcon.OrgRef;
import org.fao.fi.figis.devcon.Owner;
import org.fao.fi.figis.devcon.Range;
import org.fao.fi.figis.devcon.Sources;
import org.fao.fi.figis.devcon.SpatialScale;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.figis.devcon.VME;
import org.fao.fi.figis.devcon.VMECriteria;
import org.fao.fi.figis.devcon.VMEIdent;
import org.fao.fi.figis.devcon.VMEType;
import org.fao.fi.figis.devcon.WaterAreaRef;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.History;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.Lang;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.sync2.mapping.BiblioEntryFromInformationSource;
import org.purl.dc.elements._1.Title;
import org.purl.dc.terms.Created;

/**
 * FigisDocBuilder, to build a FIGISDoc from VME Domain database
 * 
 * 
 * Order for VMEIdent (/fi:FIGISDoc/fi:VME/fi:VMEIdent)
 * 
 * dc:Title
 * 
 * fi:Range
 * 
 * fi:ReportingYear
 * 
 * fi:OrgRef
 * 
 * fi:GeoReference
 * 
 * fi:VMEType
 * 
 * fi:VMECriteria
 * 
 * @author Emmanuel Blondel
 * @author Erik van Ingen
 * 
 */
public class FigisDocBuilder {

	private ObjectFactory f = new ObjectFactory();
	private MultiLingualStringUtil u = new MultiLingualStringUtil();
	private EnglishTextUtil ut = new EnglishTextUtil();
	private GeneralMeasureManagementMethodEntryBuilder mmeBuilder = new GeneralMeasureManagementMethodEntryBuilder();
	private CurrentDate currentDate = new CurrentDate();
	private BiblioEntryFromInformationSource bu = new BiblioEntryFromInformationSource();

	public static final String VULNERABLE_MARINE_ECOSYSTEMS = "Vulnerable Marine Ecosystems";

	public static final String VME_SPECIFIC_MEASURES = "VME-specific measures";

	/**
	 * Adds specificMeasures to a FIGISDoc
	 * 
	 * measureSummary fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:
	 * ManagementMethodEntry@Focus=
	 * "Vulnerable Marine Ecosystems"/fi:Measure/fi:Text
	 * fi:FIGISDoc/fi:VME/fi:Management
	 * /fi:ManagementMethods/fi:ManagementMethodEntry
	 * 
	 * @Focus="Vulnerable Marine Ecosystems"/dc:Title[VME-specific measures]
	 *                    fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 *                    /fi:ManagementMethodEntry@Focus=
	 *                    "Vulnerable Marine Ecosystems"/fi:Measure/MeasureType
	 *                    Value="vulnerable marine ecosystem"
	 *                    fi:FIGISDoc/fi:VME/
	 *                    fi:Management/fi:ManagementMethods/fi
	 *                    :ManagementMethodEntry /fi:Measure/fi:Text
	 * 
	 *                    Source/url
	 *                    fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 *                    /fi:ManagementMethodEntry/fi:Measure /fi:Sources/fi
	 *                    :BiblioEntry/dc:Identifier@Type="URI"
	 * 
	 *                    Source/citation
	 *                    fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 *                    /fi:ManagementMethodEntry/ fi:Measure/fi:Sources
	 *                    /fi:BiblioEntry/dcterms:bibliographicCitation
	 * 
	 *                    ValidityPeriod/beginYear
	 *                    fi:FIGISDoc/fi:VME/fi:Management
	 *                    /fi:ManagementMethods/fi
	 *                    :ManagementMethodEntry/fi:Measure
	 *                    /fi:Range@Type="Time"/fi:Min
	 * 
	 *                    ValidityPeriod/endYear
	 *                    fi:FIGISDoc/fi:VME/fi:Management
	 *                    /fi:ManagementMethods/fi
	 *                    :ManagementMethodEntry/fi:Measure/fi
	 *                    :Range@Type="Time"/fi:Max
	 * 
	 * 
	 * @param specificMeasure
	 * @param figisDoc
	 */
	public void specificMeasures(SpecificMeasure specificMeasure, FIGISDoc figisDoc) {

		// ManagementMethodEntry
		if (specificMeasure != null) {

			ManagementMethodEntry entry = f.createManagementMethodEntry();
			entry.setFocus(VULNERABLE_MARINE_ECOSYSTEMS);

			// title
			Title entryTitle = new Title();
			entryTitle.setContent(VME_SPECIFIC_MEASURES);
			entry.setTitle(entryTitle);

			// Measure
			Measure measure = f.createMeasure();

			// measureType
			MeasureType measureType = f.createMeasureType();
			measureType.setValue(VME_SPECIFIC_MEASURES);
			measure.getTextsAndImagesAndTables().add(measureType);

			// text

			Text measureText = ut.getEnglishText(specificMeasure.getVmeSpecificMeasure());
			measure.getTextsAndImagesAndTables().add(measureText);

			// contentRule.add(content, measureType,
			// measure.getTextsAndImagesAndTables())

			// range (time)
			if (specificMeasure.getValidityPeriod() != null) {
				Min min = f.createMin();
				min.setContent(specificMeasure.getValidityPeriod().getBeginYear().toString());
				JAXBElement<Min> minJAXBElement = f.createRangeMin(min);

				Max max = f.createMax();
				max.setContent(specificMeasure.getValidityPeriod().getEndYear().toString());
				JAXBElement<Max> maxJAXBElement = f.createRangeMax(max);

				Range range = f.createRange();
				range.setType("Time");
				range.getContent().add(minJAXBElement);
				range.getContent().add(maxJAXBElement);
				measure.getTextsAndImagesAndTables().add(range);
			}

			// sources
			Sources sources = f.createSources();

			// make a biblioEntry out of the InformationSource.
			BiblioEntry biblioEntry = bu.transform(specificMeasure.getInformationSource());

			if (specificMeasure.getInformationSource() != null) {

				// add biblioEntry to sources
				new AddWhenContentRule<Object>().check(specificMeasure.getInformationSource())
						.check(specificMeasure.getInformationSource()).beforeAdding(biblioEntry)
						.to(sources.getTextsAndImagesAndTables());

				// add source to the measure (Sources are added to the
				// SpecificMeasure, not to the entry)
				new AddWhenContentRule<Object>().check(specificMeasure.getInformationSource())
						.check(specificMeasure.getInformationSource().getUrl())
						.check(specificMeasure.getInformationSource().getCitation()).beforeAdding(sources)
						.to(measure.getTextsAndImagesAndTables());

			}

			// add measure to entry
			new AddWhenContentRule<Object>().check(specificMeasure.getInformationSource())
					.check(specificMeasure.getVmeSpecificMeasure()).beforeAdding(measure)
					.to(entry.getTextsAndImagesAndTables());

			ManagementMethods managementMethods = findManagementMethods(figisDoc);

			managementMethods.getManagementMethodEntriesAndTextsAndImages().add(entry);

		}

	}

	private ManagementMethods findManagementMethods(FIGISDoc figisDoc) {
		Management management = findManagement(figisDoc);

		ManagementMethods managementMethods = null;
		List<Object> textsAndImagesAndTablesList = management.getTextsAndImagesAndTables();
		for (Object o : textsAndImagesAndTablesList) {
			if (o instanceof ManagementMethods) {
				managementMethods = (ManagementMethods) o;
			}
		}

		if (managementMethods == null) {
			managementMethods = f.createManagementMethods();
			management.getTextsAndImagesAndTables().add(managementMethods);
		}
		return managementMethods;
	}

	private Management findManagement(FIGISDoc figisDoc) {
		List<Object> list = figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts();
		Management management = null;
		for (Object object : list) {
			if (object instanceof Management) {
				management = (Management) object;
			}
		}
		if (management == null) {
			management = f.createManagement();
			figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(management);
		}
		return management;
	}

	/**
	 * Adds a profile to the FIGISDoc
	 * 
	 * descriptionBiological fi:FIGISDoc/fi:VME/fi:HabitatBio/fi:Text
	 * 
	 * descriptionPhysical fi:FIGISDoc/fi:VME/fi:HabitatBio/fi:GeoForm/fi:Text
	 * 
	 * descriptionImpact fi:FIGISDoc/fi:VME/fi:Impacts/fi:Text
	 * 
	 * 
	 * @param profile
	 * @param figisDoc
	 */
	public void profile(Profile profile, FIGISDoc figisDoc) {
		// Habitat-Biological profile
		if (profile != null) {

			// • VMEIdent
			// • HabitatBio
			// • Impacts
			// • Management
			// • History
			// • FisheryAreas
			// • AddInfo
			// • Sources
			// • RelatedResources

			GeneralBiology gb = f.createGeneralBiology();
			Text text1 = ut.getEnglishText(profile.getDescriptionBiological());
			gb.getTextsAndImagesAndTables().add(text1);
			HabitatBio habitatBio = f.createHabitatBio();
			new AddWhenContentRule<Object>().check(u.getEnglish(profile.getDescriptionBiological())).beforeAdding(gb)
					.to(habitatBio.getClimaticZonesAndDepthZonesAndDepthBehavs());

			// Physical profile
			// terribe workaround because if there is no Text, profileEnglish
			// will also not be added by Jaxb.
			Text descriptionPhisical = ut.getEnglishText(profile.getDescriptionPhisical());
			if (descriptionPhisical == null) {
				descriptionPhisical = new Text();
				descriptionPhisical.getContent().add(" ");
			}

			JAXBElement<Text> geoformJAXBElement = f.createGeoFormText(descriptionPhisical);
			GeoForm geoform = f.createGeoForm();
			new AddWhenContentRule<Serializable>().check(profile.getDescriptionPhisical())
					.beforeAdding(geoformJAXBElement).to(geoform.getContent());
			// geoform.getContent().add(geoformJAXBElement);

			String profileEnglish = u.getEnglish(profile.getGeoform());
			// fi:FIGISDoc/fi:VME/fi:HabitatBio/fi:GeoForm@Value (if Value =
			// Seamounts or Canyons) or
			// fi:FIGISDoc/fi:VME/fi:HabitatBio/fi:GeoForm@FreeValue (for other
			// values)
			if (profileEnglish == "Seamounts" || profileEnglish == "Canyons") {
				geoform.setValue(profileEnglish);
			} else {
				geoform.setFreeValue(profileEnglish);
			}

			habitatBio.getClimaticZonesAndDepthZonesAndDepthBehavs().add(geoform); // geoForm
																					// is
																					// part
																					// of
																					// HabitatBio
			// profile

			new AddWhenContentRule<Object>().check(profile.getDescriptionPhisical()).beforeAdding(habitatBio)
					.to(figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts());

			// Impacts profile
			Impacts impacts = f.createImpacts();
			Text text3 = ut.getEnglishText(profile.getDescriptionImpact());
			impacts.getTextsAndImagesAndTables().add(text3);
			new AddWhenContentRule<Object>().check(text3).beforeAdding(impacts)
					.to(figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts());

		}

	}

	/**
	 * Adds GeneralMeasures to the FIGISDoc assuming the Management and
	 * ManagementMethods children have been yet created by specifying the
	 * specificMeasures.
	 * 
	 * FishingAreas fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:
	 * ManagementMethodEntry@Focus= "Vulnerable Marine Ecosystems"/dc:Title[VME
	 * general measures] fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 * /fi
	 * :ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/fi:Measure/
	 * MeasureType@Value="Fishing_areas"
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 * /fi:ManagementMethodEntry/fi:Measure/fi:Text
	 * 
	 * ExploratoryFishingProtocol
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 * /fi:ManagementMethodEntry@Focus=
	 * "Vulnerable Marine Ecosystems"/dc:Title[VME general measures]
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 * /fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"
	 * /fi:Measure/MeasureType@Value="Exploratory_fishing_protocol"
	 * fi:FIGISDoc/fi
	 * :VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry
	 * /fi:Measure/fi:Text
	 * 
	 * EncounterProtocols
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi
	 * :ManagementMethodEntry@Focus= "Vulnerable Marine Ecosystems"/dc:Title[VME
	 * general measures] fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 * /fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"
	 * /fi:Measure/MeasureType@Value="VME_encounter_protocols"
	 * fi:FIGISDoc/fi:VME
	 * /fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry
	 * /fi:Measure/fi:Text
	 * 
	 * Threshold fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:
	 * ManagementMethodEntry@Focus= "Vulnerable Marine Ecosystems"/dc:Title[VME
	 * general measures] fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 * /fi
	 * :ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/fi:Measure/
	 * MeasureType@Value="VME_threshold"
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 * /fi:ManagementMethodEntry/fi:Measure/fi:Text
	 * 
	 * IndicatorSpecies
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:
	 * ManagementMethodEntry@Focus= "Vulnerable Marine Ecosystems"/dc:Title[VME
	 * general measures] fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 * /fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"
	 * /fi:Measure/MeasureType@Value="VME_indicatorspecies"
	 * fi:FIGISDoc/fi:VME/fi
	 * :Management/fi:ManagementMethods/fi:ManagementMethodEntry
	 * /fi:Measure/fi:Text
	 * 
	 * Source/url fi:FIGISDoc/fi:VME//fi:Management/fi:ManagementMethods/fi:
	 * ManagementMethodEntry/fi:Sources/fi:BiblioEntry /dc:Identifier@Type="URI"
	 * Source/citation
	 * fi:FIGISDoc/fi:VME//fi:Management/fi:ManagementMethods/fi:
	 * ManagementMethodEntry
	 * /fi:Sources/fi:BiblioEntry/dcterms:bibliographicCitation
	 * 
	 * ValidityPeriod/beginYear
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 * /fi:ManagementMethodEntry/fi:Range@Type="Time"/fi:Min
	 * ValidityPeriod/endYear
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 * /fi:ManagementMethodEntry/fi:Range@Type="Time"/fi:Max
	 * 
	 * @param generalMeasure
	 * @param figisDoc
	 */
	public void generalMeasures(GeneralMeasure generalMeasure, FIGISDoc figisDoc, int disseminationYear) {

		// entry
		ManagementMethodEntry entry = f.createManagementMethodEntry();

		mmeBuilder.initGM(entry);

		// FishingAreas (entry)
		mmeBuilder.addMeasureToEntry1(generalMeasure, entry);

		// ExploratoryFishingProtocol (entry)
		mmeBuilder.addMeasureToEntry2(generalMeasure, entry);

		//
		// VME_ENCOUNTER_PROTOCOLS (entry)
		mmeBuilder.addMeasureToEntry3(generalMeasure, entry);

		//
		// VME_THRESHOLD (entry)
		mmeBuilder.addMeasureToEntry4(generalMeasure, entry);

		// VME_INDICATORSPECIES (entry)
		mmeBuilder.addMeasureToEntry5(generalMeasure, entry);

		// this one is associated to the method, not to any entry
		mmeBuilder.addSources(generalMeasure, entry);

		// this one is asociated to the method, not to any entry
		mmeBuilder.addRange(generalMeasure, entry);

		ManagementMethods methods = findManagementMethods(figisDoc);

		if (entry.getTextsAndImagesAndTables().size() > 0) {
			methods.getManagementMethodEntriesAndTextsAndImages().add(entry);
		}

	}

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
	public void vme(Vme vmeDomain, int year, FIGISDoc figisDoc) {
		VMEIdent vmeIdent = new VMEIdent();

		// FigisID
		FigisID figisID = new FigisID();
		figisID.setContent(vmeDomain.getId().toString());

		// Title
		Title title = new Title();
		title.setContent(vmeDomain.getName().getStringMap().get(Lang.EN));

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
		spatialScaleTitle.setContent(vmeDomain.getGeoArea().getStringMap().get(Lang.EN));

		GeoReference geoReference = f.createGeoReference();
		geoReference.setSpatialScale(spatialScale);
		geoReference.setTitle(spatialScaleTitle);

		// WaterAreaRef
		WaterAreaRef waterAreaRef = new WaterAreaRef();
		ForeignID areaForeignID = new ForeignID();
		areaForeignID.setCodeSystem("vme");
		areaForeignID.setCode(vmeDomain.getGeoRefList().get(0).getGeographicFeatureID());
		waterAreaRef.getFigisIDsAndForeignIDs().add(areaForeignID);

		// Validity period - Range
		Min min = f.createMin();
		min.setContent(vmeDomain.getValidityPeriod().getBeginYear().toString());
		JAXBElement<Min> minJAXBElement = f.createRangeMin(min);

		Max max = f.createMax();
		max.setContent(vmeDomain.getValidityPeriod().getEndYear().toString());
		JAXBElement<Max> maxJAXBElement = f.createRangeMax(max);

		Range range = f.createRange();
		range.setType("Time");
		range.getContent().add(minJAXBElement);
		range.getContent().add(maxJAXBElement);

		// VME Type
		VMEType vmeType = new VMEType();
		vmeType.setValue(vmeDomain.getAreaType());

		// VME Criteria
		VMECriteria vmeCriteria = new VMECriteria();
		vmeCriteria.setValue(vmeDomain.getCriteria());

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
		Sources sources = f.createSources();

		Collections.sort(infoSourceList, new InformationSourceComparator());

		for (InformationSource infoSource : infoSourceList) {
			// Algorithm for "InformationSource" UML table: with regards of the
			// meetings tab, provide all and only records with "sourcetype=3"
			// and according to the selected year
			int oneBefore = disseminationYear - 1;
			if (infoSource.getSourceType() == 3
					&& (infoSource.getPublicationYear() == disseminationYear || infoSource.getPublicationYear() == oneBefore)) {
				BiblioEntry biblioEntry = bu.transform(infoSource);
				new AddWhenContentRule<Object>().check(biblioEntry).beforeAdding(biblioEntry)
						.to(sources.getTextsAndImagesAndTables());
			}

		}
		figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(sources);
	}

	/**
	 * <fi:DataEntry>
	 * 
	 * <fi:Editor>///RFMO Acronym///</fi:Editor>
	 * 
	 * <dcterms:Created>//// date of creation yyyy-mm-dd ////</dcterms:Created>
	 * 
	 * </fi:DataEntry>
	 * 
	 * <fi:ObjectSource>
	 * 
	 * <fi:Owner>
	 * 
	 * <fi:CollectionRef>
	 * 
	 * <fi:FigisID MetaID="267000">7300</fi:FigisID>
	 * 
	 * </fi:CollectionRef>
	 * 
	 * </fi:Owner>
	 * 
	 * <fi:CorporateCoverPage>
	 * 
	 * <fi:FigisID MetaID="280000">791</fi:FigisID>
	 * 
	 * </fi:CorporateCoverPage>
	 * 
	 * <fi:CoverPage>
	 * 
	 * <dcterms:Created>//// date of creation yyyy-mm-dd ////</dcterms:Created>
	 * 
	 * </fi:CoverPage>
	 * 
	 * </fi:ObjectSource>
	 * 
	 * 
	 * @param figisDoc
	 */
	public void dataEntryObjectSource(String rfmo, FIGISDoc figisDoc) {

		// dataEntry
		Editor editor = f.createEditor();
		editor.setContent(rfmo);

		Created created = new Created();
		created.setContent(currentDate.getCurrentDateYyyyMmDd());

		DataEntry dataEntry = f.createDataEntry();
		dataEntry.setEditor(editor);
		dataEntry.setCreated(created);

		figisDoc.setDataEntry(dataEntry);

		// fi:ObjectSource (owner corporateCoverPage, coverPage)

		// owner
		FigisID figisID = new FigisID();
		figisID.setContent("7300");
		figisID.setMetaID("267000");

		CollectionRef collectionRef = f.createCollectionRef();
		collectionRef.getFigisIDsAndForeignIDs().add(figisID);

		Owner owner = f.createOwner();
		owner.setCollectionRef(collectionRef);

		// corporateCoverPage <fi:FigisID MetaID="280000">791</fi:FigisID>
		FigisID figisIDCC = new FigisID();
		figisIDCC.setContent("791");
		figisIDCC.setMetaID("280000");
		CorporateCoverPage corporateCoverPage = f.createCorporateCoverPage();
		corporateCoverPage.getFigisIDsAndForeignIDs().add(figisIDCC);

		// coverPage
		CoverPage coverPage = f.createCoverPage();
		coverPage.getCreatorPersonalsAndCreatedsAndModifieds().add(currentDate.getCurrentDateYyyyMmDd());

		ObjectSource objectSource = f.createObjectSource();
		objectSource.setOwner(owner);
		objectSource.setCoverPage(coverPage);
		objectSource.setCorporateCoverPage(corporateCoverPage);

		figisDoc.setObjectSource(objectSource);

	}

	public void fisheryArea(History fisheryAreasHistory, FIGISDoc figisDoc) {
		// // FishingArea_history fi:FIGISDoc/fi:VME/fi:FisheryArea/fi:Text
		if (fisheryAreasHistory != null) {
			Text text = ut.getEnglishText(fisheryAreasHistory.getHistory());
			FisheryArea fisheryArea = f.createFisheryArea();
			fisheryArea.getTextsAndImagesAndTables().add(text);
			figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(fisheryArea);
		}

	}

	public void vmesHistory(History vmesHistory, FIGISDoc figisDoc) {
		// // VME_history fi:FIGISDoc/fi:VME/fi:History/fi:Text
		if (vmesHistory != null) {
			Text text = ut.getEnglishText(vmesHistory.getHistory());
			org.fao.fi.figis.devcon.History history = f.createHistory();
			history.getTextsAndImagesAndTables().add(text);
			figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(history);
		}
	}
}
