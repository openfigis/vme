package org.fao.fi.vme.sync2.mapping.xml;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.fao.fi.figis.devcon.BiblioEntry;
import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.FigisID;
import org.fao.fi.figis.devcon.ForeignID;
import org.fao.fi.figis.devcon.GeoForm;
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
import org.fao.fi.figis.devcon.OrgRef;
import org.fao.fi.figis.devcon.Range;
import org.fao.fi.figis.devcon.Sources;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.figis.devcon.VME;
import org.fao.fi.figis.devcon.VMECriteria;
import org.fao.fi.figis.devcon.VMEIdent;
import org.fao.fi.figis.devcon.VMEType;
import org.fao.fi.figis.devcon.WaterAreaRef;
import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.History;
import org.fao.fi.vme.domain.InformationSource;
import org.fao.fi.vme.domain.Profile;
import org.fao.fi.vme.domain.Rfmo;
import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.util.Lang;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.sync2.mapping.RfmoHistory;
import org.fao.fi.vme.sync2.mapping.VmeHistory;
import org.purl.agmes._1.CreatorCorporate;
import org.purl.dc.elements._1.Date;
import org.purl.dc.elements._1.Identifier;
import org.purl.dc.elements._1.Title;
import org.purl.dc.terms.Abstrakt;
import org.purl.dc.terms.BibliographicCitation;

/**
 * FigisDocBuilder, to build a FIGISDoc from VME Domain database
 * 
 * @author Emmanuel Blondel
 * @author Erik van Ingen
 *
 */
public class FigisDocBuilder {

	ObjectFactory f = new ObjectFactory();
	MultiLingualStringUtil u = new MultiLingualStringUtil();
	
	/**
	 * Adds specificMeasures to a FIGISDoc
	 * 
	 * measureSummary 
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/fi:Measure/fi:Text
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/dc:Title[VME-specific measures]
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/fi:Measure/MeasureType Value="vulnerable marine ecosystem"
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Measure/fi:Text
	 * 
	 * Source/url
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Measure/fi:Sources/fi:BiblioEntry/dc:Identifier@Type="URI"
	 * 
	 * Source/citation 
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Measure/fi:Sources/fi:BiblioEntry/dcterms:bibliographicCitation
	 * 
	 * ValidityPeriod/beginYear
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Measure/fi:Range@Type="Time"/fi:Min
	 * 
	 * ValidityPeriod/endYear
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Measure/fi:Range@Type="Time"/fi:Max 
	 * 
	 * 
	 * @param yearObject
	 * @param figisDoc
	 */
	public void specificMeasures(SpecificMeasures yearObject, FIGISDoc figisDoc) {
		
		//ManagementMethodEntry
		ManagementMethodEntry entry = f.createManagementMethodEntry();
		entry.setFocus("Vulnerable Marine Ecosystems");
		
		//title
		Title entryTitle = new Title();
		entryTitle.setContent("VME-specific measures");
		entry.setTitle(entryTitle);
		
		//Measure
		Measure measure = f.createMeasure();
			
			//measureType
			MeasureType measureType = f.createMeasureType();
			measureType.setValue("VME-specific measures");
			measure.getTextsAndImagesAndTables().add(measureType);
			
			//text
			Text measureText = f.createText();
			measureText.getContent().add(u.getEnglish(yearObject.getVmeSpecificMeasure()));
			measure.getTextsAndImagesAndTables().add(measureText);
			
			//range (time)
			Min min = f.createMin();
			min.setContent(yearObject.getValidityPeriod().getBeginYear().toString());
			JAXBElement<Min> minJAXBElement = f.createRangeMin(min);

			Max max = f.createMax();
			max.setContent(yearObject.getValidityPeriod().getEndYear().toString());
			JAXBElement<Max> maxJAXBElement = f.createRangeMax(max);

			Range range = f.createRange();
			range.setType("Time");
			range.getContent().add(minJAXBElement);
			range.getContent().add(maxJAXBElement);
			measure.getTextsAndImagesAndTables().add(range);
			
			//sources
			Sources sources = f.createSources();
			BiblioEntry biblioEntry = f.createBiblioEntry();
			
			BibliographicCitation citation = new BibliographicCitation();
			citation.setContent(u.getEnglish(yearObject.getInformationSource().getCitation()));
			biblioEntry.getContent().add(citation);
			
			Identifier identifier = new Identifier();
			identifier.setType("URI");
			identifier.setContent(yearObject.getInformationSource().getUrl().toString());
			biblioEntry.getContent().add(identifier);
			
			sources.getTextsAndImagesAndTables().add(biblioEntry);
			measure.getTextsAndImagesAndTables().add(sources);
		
			
		entry.getTextsAndImagesAndTables().add(measure); //add measure to ManagementMethodEntry
		
		ManagementMethods managementMethods = f.createManagementMethods();
		managementMethods.getManagementMethodEntriesAndTextsAndImages().add(entry);
		Management management = f.createManagement();
		management.getTextsAndImagesAndTables().add(managementMethods);
		
		figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(management);
				
	}

	/**
	 * Adds a VME history to the FIGISDoc
	 * 
	 * VME_history 	fi:FIGISDoc/fi:VME/fi:History/fi:Text
	 * 
	 * @param yearObject
	 * @param figisDoc
	 */
	public void vmeHistory(VmeHistory yearObject, FIGISDoc figisDoc) {	
		//TODO
		
		/*org.fao.fi.figis.devcon.History hist = f.createHistory();
		Text historyText = f.createText();
		historyText.getContent().add(u.getEnglish(history.getHistory()));
		hist.getTextsAndImagesAndTables().add(historyText);
		figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(hist);*/
	}

	
	/**
	 * Adds a Fishery history to the FIGISDoc
	 * 
	 * @param yearObject
	 * @param figisDoc
	 */
	public void rfmoHistory(RfmoHistory yearObject, FIGISDoc figisDoc) {
		// TODO Auto-generated method stub

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
	public void profile(Profile yearObject, FIGISDoc figisDoc) {
		//Habitat-Biological profile
		HabitatBio habitatBio = f.createHabitatBio();

		Text text1 = f.createText();
		text1.getContent().add(u.getEnglish(yearObject.getDescriptionBiological()));
		habitatBio.getClimaticZonesAndDepthZonesAndDepthBehavs().add(text1);
		figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(habitatBio);

		//Physical profile
		Text text2 = f.createText();
		text2.getContent().add(u.getEnglish(yearObject.getDescriptionPhisical()));
		GeoForm geoform = f.createGeoForm();
		JAXBElement<Text> geoformJAXBElement = f.createGeoFormText(text2);
		geoform.getContent().add(geoformJAXBElement);
		habitatBio.getClimaticZonesAndDepthZonesAndDepthBehavs().add(geoform); //geoForm is part of HabitatBio profile
		
		//Impacts profile
		Impacts impacts = f.createImpacts();
		Text text3 = f.createText();
		text3.getContent().add(u.getEnglish(yearObject.getDescriptionImpact()));
		impacts.getTextsAndImagesAndTables().add(text3);
		figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(impacts);

	}

	
	/**
	 * Adds GeneralMeasures to the FIGISDoc assuming the Management and ManagementMethods children
	 * have been yet created by specifying the specificMeasures.
	 * 
	 * FishingAreas
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/dc:Title[VME general measures]
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/fi:Measure/MeasureType@Value="Fishing_areas"
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Measure/fi:Text
	 * 
	 * ExploratoryFishingProtocol
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/dc:Title[VME general measures]
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/fi:Measure/MeasureType@Value="Exploratory_fishing_protocol"
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Measure/fi:Text
	 * 
	 * EncounterProtocols
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/dc:Title[VME general measures]
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/fi:Measure/MeasureType@Value="VME_encounter_protocols"
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Measure/fi:Text 
	 * 
	 * Threshold
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/dc:Title[VME general measures]
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/fi:Measure/MeasureType@Value="VME_threshold"
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Measure/fi:Text
	 * 
	 * IndicatorSpecies
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/dc:Title[VME general measures]
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry@Focus="Vulnerable Marine Ecosystems"/fi:Measure/MeasureType@Value="VME_indicatorspecies"
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Measure/fi:Text 
	 * 
	 * Source/url	fi:FIGISDoc/fi:VME//fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Sources/fi:BiblioEntry/dc:Identifier@Type="URI"
	 * Source/citation	fi:FIGISDoc/fi:VME//fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Sources/fi:BiblioEntry/dcterms:bibliographicCitation
	 * 
	 * ValidityPeriod/beginYear	fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Range@Type="Time"/fi:Min
	 * ValidityPeriod/endYear	fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Range@Type="Time"/fi:Max
	 * 
	 * @param yearObject
	 * @param figisDoc
	 */
	public void generalMeasures(GeneralMeasures yearObject, FIGISDoc figisDoc) {
		
		//entry
		ManagementMethodEntry entry = f.createManagementMethodEntry();
		entry.setFocus("Vulnerable Marine Ecosystems");
		Title entryTitle = new Title();
		entryTitle.setContent("VME general measures");
		entry.setTitle(entryTitle);
		
		//Measures
		//1. FishingAreas
		Measure measure1 = f.createMeasure();
		MeasureType measureType1 = f.createMeasureType();
		measureType1.setValue("Fishing_areas");
		Text measureText1 = f.createText();
		measureText1.getContent().add(yearObject.getFishingAreas());
		
		measure1.getTextsAndImagesAndTables().add(measureType1);
		measure1.getTextsAndImagesAndTables().add(measureText1);
		entry.getTextsAndImagesAndTables().add(measure1); //add measure to entry
		
		//2. ExploratoryFishingProtocol
		Measure measure2 = f.createMeasure();
		MeasureType measureType2 = f.createMeasureType();
		measureType2.setValue("Exploratory_fishing_protocol");
		Text measureText2 = f.createText();
		measureText2.getContent().add(u.getEnglish(yearObject.getExplorataryFishingProtocols()));
		
		measure2.getTextsAndImagesAndTables().add(measureType2);
		measure2.getTextsAndImagesAndTables().add(measureText2);
		entry.getTextsAndImagesAndTables().add(measure2); //add measure to entry

		//3. EncounterProtocol
		Measure measure3 = f.createMeasure();
		MeasureType measureType3 = f.createMeasureType();
		measureType3.setValue("VME_encounter_protocols");
		Text measureText3 = f.createText();
		measureText3.getContent().add(u.getEnglish(yearObject.getVmeEncounterProtocols()));
		
		measure3.getTextsAndImagesAndTables().add(measureType3);
		measure3.getTextsAndImagesAndTables().add(measureText3);
		entry.getTextsAndImagesAndTables().add(measure3); //add measure to entry
		
		//4. Threshold
		Measure measure4 = f.createMeasure();
		MeasureType measureType4 = f.createMeasureType();
		measureType4.setValue("VME_threshold");
		Text measureText4 = f.createText();
		measureText4.getContent().add(u.getEnglish(yearObject.getVmeThreshold()));
		
		measure4.getTextsAndImagesAndTables().add(measureType4);
		measure4.getTextsAndImagesAndTables().add(measureText4);
		entry.getTextsAndImagesAndTables().add(measure4); //add measure to entry
		
		//5. IndicatorSpecies
		Measure measure5 = f.createMeasure();
		MeasureType measureType5 = f.createMeasureType();
		measureType5.setValue("VME_indicatorspecies");
		Text measureText5 = f.createText();
		measureText5.getContent().add(u.getEnglish(yearObject.getVmeIndicatorSpecies()));
		
		measure5.getTextsAndImagesAndTables().add(measureType5);
		measure5.getTextsAndImagesAndTables().add(measureText5);
		entry.getTextsAndImagesAndTables().add(measure5);
		
		
		//ManagementMethodEntry Sources
		Sources sources = f.createSources();
		for(InformationSource infoSource : yearObject.getInformationSourceList()){
			BiblioEntry biblioEntry = f.createBiblioEntry();
			
			Identifier identifier = new Identifier();
			identifier.setType("URI");
			identifier.setContent(infoSource.getUrl().toString());
			
			BibliographicCitation citation = new BibliographicCitation();
			citation.setContent(u.getEnglish(infoSource.getCitation()));
			
			biblioEntry.getContent().add(identifier);
			biblioEntry.getContent().add(citation);
			sources.getTextsAndImagesAndTables().add(biblioEntry);
		}
		entry.getTextsAndImagesAndTables().add(sources);
		
		//ManagementMethodEntry ValidityPeriod
		Min min = f.createMin();
		min.setContent(yearObject.getValidityPeriod().getBeginYear().toString());
		JAXBElement<Min> minJAXBElement = f.createRangeMin(min);

		Max max = f.createMax();
		max.setContent(yearObject.getValidityPeriod().getEndYear().toString());
		JAXBElement<Max> maxJAXBElement = f.createRangeMax(max);

		Range range = f.createRange();
		range.setType("Time");
		range.getContent().add(minJAXBElement);
		range.getContent().add(maxJAXBElement);
		entry.getTextsAndImagesAndTables().add(range);
		
		
		// Adding to FigisDocBuilder...
		// get Management & ManagementMethods list indexes to then set
		// new ManagementMethodEntry to existing ManagementMethod
		int i = 0;
		int j = 0;
		for (Object obj : figisDoc.getVME()
				.getOverviewsAndHabitatBiosAndImpacts()) {
			if (obj instanceof Management) {
				for (Object obj2 : ((Management) obj)
						.getTextsAndImagesAndTables()) {
					if (obj2 instanceof ManagementMethods) {
						break;
					} else {
						j++;
					}
				}
			} else {
				i++;
			}
		}
		//use Management index (i) and ManagementMethod index(j) to add
		//the new ManagementMethodEntry
		((ManagementMethods) ((Management) figisDoc.getVME()
				.getOverviewsAndHabitatBiosAndImpacts().get(i))
				.getTextsAndImagesAndTables().get(j))
				.getManagementMethodEntriesAndTextsAndImages().add(entry);
	}

	/**
	 * Adds a Vme to the FIGISDoc
	 * 
	 * VME Identifier fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:FigisID
	 * 
	 * inventoryIdentifier fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:ForeignID@CodeSystem="invid"/@Code
	 * 
	 * name fi:FIGISDoc/fi:VME/fi:VMEIdent/dc:Title
	 * 
	 * geographicLayerId fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:WaterAreaRef/fi:ForeignID@CodeSystem="vme"/@Code
	 * 
	 * areaType fi:FIGISDoc/fi:VME/fi:VMEIdent/VMEType/@Value
	 * 
	 * criteria fi:FIGISDoc/fi:VME/fi:VMEIdent/VMECriteria/@Value
	 * 
	 * ValidityPeriod/beginYear fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:Range@Type="Time"/fi:Min
	 * 
	 * ValidityPeriod/endYear fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:Range@Type="Time"/fi:Max
	 * 
	 * @param vmeDomain
	 * @param figisDoc
	 */
	public void vme(Vme vmeDomain, FIGISDoc figisDoc) {
		VMEIdent vmeIdent = new VMEIdent();
		
		//FigisID
		FigisID figisID = new FigisID();
		figisID.setContent(vmeDomain.getId().toString());
		
		//Title
		Title title = new Title();
		title.setContent(vmeDomain.getName().getStringMap().get(Lang.EN));
		
		//ForeignID
		ForeignID vmeForeignID = new ForeignID();
		vmeForeignID.setCodeSystem("invid");
		vmeForeignID.setCode(vmeDomain.getInventoryIdentifier());
		
		//WaterAreaRef
		WaterAreaRef waterAreaRef = new WaterAreaRef();
		ForeignID areaForeignID = new ForeignID();
		areaForeignID.setCodeSystem("vme");
		areaForeignID.setCode(vmeDomain.getGeoRefList().get(0).getGeographicFeatureID());
		waterAreaRef.getFigisIDsAndForeignIDs().add(areaForeignID);
		
		//Validity period - Range
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

		//VME Type
		VMEType vmeType = new VMEType();
		vmeType.setValue(vmeDomain.getAreaType());
		
		//VME Criteria
		VMECriteria vmeCriteria = new VMECriteria();
		vmeCriteria.setValue(vmeDomain.getCriteria());
		
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(figisID);
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(title);
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(vmeForeignID);
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(waterAreaRef);
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(vmeCriteria);
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(vmeType);
		vmeIdent.getFigisIDsAndForeignIDsAndWaterAreaReves().add(range);

		VME vme = new VME();
		vme.setVMEIdent(vmeIdent);
		figisDoc.setVME(vme);

	}

	/**
	 * Adds a reporting year to the FIGISDoc
	 * 
	 * Observation/Year	fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:ReportingYear 
	 * 
	 * @param year
	 * @param figisDoc
	 */
	public void year(Object year, FIGISDoc figisDoc) {
		figisDoc.getVME().getVMEIdent().getFigisIDsAndForeignIDsAndWaterAreaReves().add(year);
	}
	
	
	/**
	 * Adds a RFMO to the FIGISDoc
	 * 
	 * Rfmo fi:FIGISDoc/fi:VME/fi:VMEIdent/fi:OrgRef/fi:ForeignID@CodeSystem="rfb"/@Code
	 * 
	 * @param rfmo
	 * @param figisDoc
	 */
	public void rfmo(Rfmo rfmo, FIGISDoc figisDoc) {
		ForeignID rfmoForeignID = f.createForeignID();
		rfmoForeignID.setCodeSystem("rfb");
		rfmoForeignID.setCode(rfmo.getId());

		OrgRef rfmoOrg = f.createOrgRef();
		rfmoOrg.getForeignIDsAndFigisIDsAndTitles().add(rfmoForeignID);
		figisDoc.getVME().getVMEIdent()
				.getFigisIDsAndForeignIDsAndWaterAreaReves().add(rfmoOrg);
	}
	
	
	/**
	 * Adds a list of InformationSource to the FIGISDoc
	 * 
	 * date
	 * fi:FIGISDoc/fi:VME/fi:Sources/fi:BiblioEntry/dcterms:Created
	 * 
	 * committee
	 * fi:FIGISDoc/fi:VME/fi:Sources/fi:BiblioEntry/ags:CreatorCorporate
	 * 
	 * reportSummary
	 * fi:FIGISDoc/fi:VME/fi:Sources/fi:BiblioEntry/dcterms:Abstract
	 * 
	 * url
	 * fi:FIGISDoc/fi:VME/fi:Sources/fi:BiblioEntry/dc:Identifier@Type="URI"
	 * 
	 * citation
	 * fi:FIGISDoc/fi:VME/fi:Sources/fi:BiblioEntry/dcterms:bibliographicCitation 
	 * 
	 * @param infoSourceList
	 * @param figisDoc
	 */
	public void informationSource(List<InformationSource> infoSourceList, FIGISDoc figisDoc){
		Sources sources = f.createSources();
		
		for(InformationSource infoSource : infoSourceList){
			
			BiblioEntry biblioEntry = f.createBiblioEntry();
			
			CreatorCorporate cc = new CreatorCorporate();
			cc.setContent(u.getEnglish(infoSource.getCommittee()));
			biblioEntry.getContent().add(cc);
			
			Date date = new Date();
			date.setContent(infoSource.getDate().toString());
			biblioEntry.getContent().add(date);
			
			Abstrakt bibAbstract = new Abstrakt();
			bibAbstract.setContent(u.getEnglish(infoSource.getReportSummary()));
			biblioEntry.getContent().add(bibAbstract);
			
			Identifier identifier = new Identifier();
			identifier.setType("URI");
			identifier.setContent(infoSource.getUrl().toString());
			biblioEntry.getContent().add(identifier);
			
			BibliographicCitation citation = new BibliographicCitation();
			citation.setContent(u.getEnglish(infoSource.getCitation()));
			biblioEntry.getContent().add(citation);

			sources.getTextsAndImagesAndTables().add(biblioEntry);
		}
		
		figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(sources);
	}

}
