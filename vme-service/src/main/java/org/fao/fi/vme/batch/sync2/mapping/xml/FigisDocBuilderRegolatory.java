package org.fao.fi.vme.batch.sync2.mapping.xml;

import java.util.List;

import org.fao.fi.figis.devcon.CollectionRef;
import org.fao.fi.figis.devcon.CorporateCoverPage;
import org.fao.fi.figis.devcon.CoverPage;
import org.fao.fi.figis.devcon.DataEntry;
import org.fao.fi.figis.devcon.Editor;
import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.FigisID;
import org.fao.fi.figis.devcon.FisheryArea;
import org.fao.fi.figis.devcon.Management;
import org.fao.fi.figis.devcon.ManagementMethodEntry;
import org.fao.fi.figis.devcon.ManagementMethods;
import org.fao.fi.figis.devcon.ObjectSource;
import org.fao.fi.figis.devcon.Owner;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.vme.batch.sync2.mapping.DisseminationYearSlice;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.History;
import org.fao.fi.vme.domain.model.Vme;
import org.purl.dc.terms.Created;

/**
 * FigisDocBuilder, to build a FIGISDoc from VME Domain database for regolatory
 * factsheets.
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
public class FigisDocBuilderRegolatory extends FigisDocBuilderAbstract {

	@Override
	public void docIt(Vme vme, DisseminationYearSlice disseminationYearSlice, FIGISDoc figisDoc) {
		dataEntryObjectSource(disseminationYearSlice.getVme().getRfmo().getId(), figisDoc);
		vme(vme, disseminationYearSlice.getGeoRef(), disseminationYearSlice.getYear(), figisDoc);
		fisheryArea(disseminationYearSlice.getFisheryAreasHistory(), figisDoc);
		vmesHistory(disseminationYearSlice.getVmesHistory(), figisDoc);
		generalMeasures(disseminationYearSlice.getGeneralMeasure(), figisDoc, disseminationYearSlice.getYear());
		informationSource(disseminationYearSlice.getInformationSourceList(), disseminationYearSlice.getYear(), figisDoc);
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
