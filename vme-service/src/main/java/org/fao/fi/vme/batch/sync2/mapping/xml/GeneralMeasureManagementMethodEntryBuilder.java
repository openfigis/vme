package org.fao.fi.vme.batch.sync2.mapping.xml;

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
import org.fao.fi.vme.batch.sync2.mapping.BiblioEntryFromInformationSource;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.support.VmeSimpleDateFormat;
import org.purl.dc.elements._1.Title;

/**
 * 
 * Building up the ManagementMethodEntry, using the vme yearobjects from the vme
 * domain.
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class GeneralMeasureManagementMethodEntryBuilder {

	public static final String TITLE_GM = "VME general measures";
	public static final String FISHING_AREAS = "Fishing_areas";
	public static final String EXPLORATORY_FISHING_PROTOCOL = "Exploratory_fishing_protocol";
	public static final String VME_ENCOUNTER_PROTOCOLS = "VME_encounter_protocols";
	public static final String VME_THRESHOLD = "VME_threshold";
	public static final String VME_INDICATORSPECIES = "VME_indicatorspecies";
	public static final String URI = "URI";
	public static final String TIME = "Time";
	private BiblioEntryFromInformationSource bu = new BiblioEntryFromInformationSource();
	private VmeSimpleDateFormat du = new VmeSimpleDateFormat();
	private ObjectFactory f = new ObjectFactory();
	private CdataUtil ut = new CdataUtil();

	public void initGM(ManagementMethodEntry entry) {
		entry.setFocus(FigisDocBuilderAbstract.VULNERABLE_MARINE_ECOSYSTEMS);
		Title entryTitle = new Title();
		entryTitle.setContent(TITLE_GM);
		entry.setTitle(entryTitle);

	}

	public void addMeasureToEntryGen(GeneralMeasure gm, ManagementMethodEntry entry, String type,
			MultiLingualString text) {
		if (gm != null) {
			Measure measure1 = f.createMeasure();
			MeasureType measureType1 = f.createMeasureType();
			measureType1.setValue(type);

			Text measureText1 = ut.getCdataText(text);

			measure1.getTextsAndImagesAndTables().add(measureType1);

			measure1.getTextsAndImagesAndTables().add(measureText1);

			new AddWhenContentRule<Object>().check(ut.getCdataText(text)).beforeAdding(measure1)
					.to(entry.getTextsAndImagesAndTables());
		}

	}

	public void addMeasureToEntry1(GeneralMeasure gm, ManagementMethodEntry entry) {
		addMeasureToEntryGen(gm, entry, FISHING_AREAS, gm.getFishingArea());
	}

	/**
	 * 
	 * <fi:Management> <fi:ManagementMethods>
	 * 
	 * --<fi:ManagementMethodEntry Focus="Vulnerable Marine Ecosystems">
	 * 
	 * ----<dc:Title>VME general measures</dc:Title>
	 * 
	 * ----<fi:Measure>
	 * 
	 * ------<fi:MeasureType Value="Exploratory_fishing_protocol"/>
	 * 
	 * ------<fi:Text><![CDATA[Exploratory fishing covers all bottom fishing
	 * activities (a) outside of the existing bottom fishing area and (b) to
	 * fisheries within the existing bottom fishing area that show significant
	 * change. (Art 15.8). Exploratory fisheries must be conducted according to
	 * an exploratory fisheries protocol (Art 18; Annex 1E.I-IV) and are subject
	 * to review FC and SC. Exploritory fisheries will be allowed only if there
	 * are adequate mitigation measures to prevent SAI to VMEs (Art 19).
	 * ]]></fi:Text>
	 * 
	 * ----</fi:Measure>
	 * 
	 * 
	 * 
	 * 
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:
	 * ManagementMethodEntry@Focus= "Vulnerable Marine Ecosystems"/dc:Title[VME
	 * general measures]
	 * 
	 * fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:
	 * ManagementMethodEntry@Focus=
	 * "Vulnerable Marine Ecosystems"/fi:Measure/MeasureType
	 * 
	 * @Value="VME_encounter_protocols"
	 * 
	 *                                  fi:FIGISDoc/fi:VME/fi:Management/fi:
	 *                                  ManagementMethods/fi:
	 *                                  ManagementMethodEntry/fi:Measure/fi:Text
	 * 
	 * @param generalMeasure
	 * @param entry
	 */

	public void addMeasureToEntry2(GeneralMeasure gm, ManagementMethodEntry entry) {
		addMeasureToEntryGen(gm, entry, EXPLORATORY_FISHING_PROTOCOL, gm.getExplorataryFishingProtocol());
	}

	public void addMeasureToEntry3(GeneralMeasure gm, ManagementMethodEntry entry) {
		addMeasureToEntryGen(gm, entry, VME_ENCOUNTER_PROTOCOLS, gm.getVmeEncounterProtocol());
	}

	public void addMeasureToEntry4(GeneralMeasure gm, ManagementMethodEntry entry) {
		addMeasureToEntryGen(gm, entry, VME_THRESHOLD, gm.getVmeThreshold());
	}

	void addMeasureToEntry5(GeneralMeasure gm, ManagementMethodEntry entry) {
		addMeasureToEntryGen(gm, entry, VME_INDICATORSPECIES, gm.getVmeIndicatorSpecies());

	}

	public void addSources(GeneralMeasure yearObject, ManagementMethodEntry entry) {
		// ManagementMethodEntry Sources
		if (yearObject != null && yearObject.getInformationSourceList() != null) {
			Sources sources = f.createSources();

			for (InformationSource infoSource : yearObject.getInformationSourceList()) {
				BiblioEntry biblioEntry = bu.transform(infoSource);
				new AddWhenContentRule<Object>().check(biblioEntry).beforeAdding(biblioEntry)
						.to(sources.getTextsAndImagesAndTables());
			}
			if (sources.getTextsAndImagesAndTables().size() > 0) {
				entry.getTextsAndImagesAndTables().add(sources);
			}
		}

	}

	public void addRange(GeneralMeasure yearObject, ManagementMethodEntry entry) {
		// ManagementMethodEntry ValidityPeriod
		if (yearObject != null) {
			Min min = f.createMin();

			min.setContent(du.createUiString(yearObject.getValidityPeriod().getBeginDate()));
			JAXBElement<Min> minJAXBElement = f.createRangeMin(min);

			Max max = f.createMax();

			max.setContent(du.createUiString(yearObject.getValidityPeriod().getEndDate()));
			JAXBElement<Max> maxJAXBElement = f.createRangeMax(max);

			Range range = f.createRange();
			range.setType(TIME);
			range.getContent().add(minJAXBElement);
			range.getContent().add(maxJAXBElement);

			new AddWhenContentRule<Object>().check(du.createUiString(yearObject.getValidityPeriod().getBeginDate()))
					.check(du.createUiString(yearObject.getValidityPeriod().getEndDate())).beforeAdding(range)
					.to(entry.getTextsAndImagesAndTables());

		}
	}
}
