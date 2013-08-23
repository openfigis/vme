package org.fao.fi.vme.sync2.mapping.xml;

import javax.xml.bind.JAXBElement;

import org.fao.fi.figis.devcon.BiblioEntry;
import org.fao.fi.figis.devcon.ManagementMethodEntry;
import org.fao.fi.figis.devcon.Max;
import org.fao.fi.figis.devcon.Measure;
import org.fao.fi.figis.devcon.MeasureType;
import org.fao.fi.figis.devcon.Min;
import org.fao.fi.figis.devcon.ObjectFactory;
import org.fao.fi.figis.devcon.Range;
import org.fao.fi.figis.devcon.Sources;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.vme.domain.GeneralMeasure;
import org.fao.fi.vme.domain.InformationSource;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.purl.dc.elements._1.Identifier;
import org.purl.dc.elements._1.Title;
import org.purl.dc.terms.BibliographicCitation;

/**
 * 
 * Building up the ManagementMethodEntry, using the vme yearobjects from the vme domain.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class ManagementMethodEntryBuilder {

	private ObjectFactory f = new ObjectFactory();
	private MultiLingualStringUtil u = new MultiLingualStringUtil();

	public void init(ManagementMethodEntry entry) {
		entry.setFocus("Vulnerable Marine Ecosystems");
		Title entryTitle = new Title();
		entryTitle.setContent("VME general measures");
		entry.setTitle(entryTitle);

	}

	public void addMeasureToEntry1(GeneralMeasure gm, ManagementMethodEntry entry) {
		// Measures
		// 1. FishingAreas
		if (gm != null) {
			Measure measure1 = f.createMeasure();
			MeasureType measureType1 = f.createMeasureType();
			measureType1.setValue("Fishing_areas");
			Text measureText1 = f.createText();
			measureText1.getContent().add(gm.getFishingAreas());

			measure1.getTextsAndImagesAndTables().add(measureType1);
			measure1.getTextsAndImagesAndTables().add(measureText1);

			entry.getTextsAndImagesAndTables().add(measure1); // add measure to entry
		}

	}

	public void addMeasureToEntry2(GeneralMeasure yearObject, ManagementMethodEntry entry) {
		if (yearObject != null) {
			// 2. ExploratoryFishingProtocol
			Measure measure2 = f.createMeasure();
			MeasureType measureType2 = f.createMeasureType();
			measureType2.setValue("Exploratory_fishing_protocol");
			Text measureText2 = f.createText();
			measureText2.getContent().add(u.getEnglish(yearObject.getExplorataryFishingProtocols()));

			measure2.getTextsAndImagesAndTables().add(measureType2);
			measure2.getTextsAndImagesAndTables().add(measureText2);
			entry.getTextsAndImagesAndTables().add(measure2); // add measure to entry
		}

	}

	public void addMeasureToEntry3(GeneralMeasure yearObject, ManagementMethodEntry entry) {
		if (yearObject != null) {
			// 3. EncounterProtocol
			Measure measure3 = f.createMeasure();
			MeasureType measureType3 = f.createMeasureType();
			measureType3.setValue("VME_encounter_protocols");
			Text measureText3 = f.createText();
			measureText3.getContent().add(u.getEnglish(yearObject.getVmeEncounterProtocols()));

			measure3.getTextsAndImagesAndTables().add(measureType3);
			measure3.getTextsAndImagesAndTables().add(measureText3);
			entry.getTextsAndImagesAndTables().add(measure3); // add measure to entry
		}

	}

	public void addMeasureToEntry4(GeneralMeasure yearObject, ManagementMethodEntry entry) {
		if (yearObject != null) {
			// 4. Threshold
			Measure measure4 = f.createMeasure();
			MeasureType measureType4 = f.createMeasureType();
			measureType4.setValue("VME_threshold");
			Text measureText4 = f.createText();
			measureText4.getContent().add(u.getEnglish(yearObject.getVmeThreshold()));

			measure4.getTextsAndImagesAndTables().add(measureType4);
			measure4.getTextsAndImagesAndTables().add(measureText4);
			entry.getTextsAndImagesAndTables().add(measure4); // add measure to entry
		}

	}

	void addMeasureToEntry5(GeneralMeasure yearObject, ManagementMethodEntry entry) {
		// 5. IndicatorSpecies
		if (yearObject != null) {
			Measure measure5 = f.createMeasure();
			MeasureType measureType5 = f.createMeasureType();
			measureType5.setValue("VME_indicatorspecies");
			Text measureText5 = f.createText();
			measureText5.getContent().add(u.getEnglish(yearObject.getVmeIndicatorSpecies()));

			measure5.getTextsAndImagesAndTables().add(measureType5);
			measure5.getTextsAndImagesAndTables().add(measureText5);
			entry.getTextsAndImagesAndTables().add(measure5);
		}

	}

	public void addSources(GeneralMeasure yearObject, ManagementMethodEntry entry) {
		// ManagementMethodEntry Sources
		if (yearObject != null) {
			Sources sources = f.createSources();
			for (InformationSource infoSource : yearObject.getInformationSourceList()) {
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
		}

	}

	public void addRange(GeneralMeasure yearObject, ManagementMethodEntry entry) {
		// ManagementMethodEntry ValidityPeriod
		if (yearObject != null) {
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
		}
	}

}
