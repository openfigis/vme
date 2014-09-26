package org.fao.fi.vme.batch.sync2.mapping.xml;

import java.util.List;

import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.FisheryArea;
import org.fao.fi.figis.devcon.Management;
import org.fao.fi.figis.devcon.ManagementMethodEntry;
import org.fao.fi.figis.devcon.ManagementMethods;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.vme.batch.sync2.mapping.DisseminationYearSlice;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.History;
import org.fao.fi.vme.domain.model.Vme;

/**
 * FigisDocBuilder, to build a FIGISDoc from VME Domain database for regulatory
 * factsheets.
 * 
 * 
 * 
 * 
 * @author Emmanuel Blondel
 * @author Erik van Ingen
 * 
 */
public class FigisDocBuilderRegulatory extends FigisDocBuilderAbstract {

	@Override
	public void docIt(Vme vme, DisseminationYearSlice disseminationYearSlice, FIGISDoc figisDoc) {
		dataEntryObjectSource(disseminationYearSlice.getVme().getRfmo().getId(), figisDoc);
		vme(vme, disseminationYearSlice.getGeoRef(), disseminationYearSlice.getYear(), figisDoc);
		mediaReference(vme, figisDoc);
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
