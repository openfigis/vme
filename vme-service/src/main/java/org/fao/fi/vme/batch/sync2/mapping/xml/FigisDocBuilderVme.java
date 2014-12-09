package org.fao.fi.vme.batch.sync2.mapping.xml;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.fao.fi.figis.devcon.AdditionalValue;
import org.fao.fi.figis.devcon.BiblioEntry;
import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.GeneralBiology;
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
import org.fao.fi.figis.devcon.Range;
import org.fao.fi.figis.devcon.Sources;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.vme.batch.sync2.mapping.DisseminationYearSlice;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.purl.dc.elements._1.Title;

/**
 * FigisDocBuilder, to build a FIGISDoc from VME Domain database for vme factsheets
 * 
 * 
 * 
 * @author Emmanuel Blondel
 * @author Erik van Ingen
 * 
 */
public class FigisDocBuilderVme extends FigisDocBuilderAbstract {

	@Override
	public void docIt(Vme vme, DisseminationYearSlice disseminationYearSlice, FIGISDoc figisDoc) {
		dataEntryObjectSource(disseminationYearSlice.getVme().getRfmo().getId(), figisDoc);
		vme(vme, disseminationYearSlice.getGeoRef(), disseminationYearSlice.getYear(), figisDoc);
		mediaReference(vme, figisDoc);
		// specificMeasures(disseminationYearSlice.getSpecificMeasure(), figisDoc);
		specificMeasureList(disseminationYearSlice.getSpecificMeasureList(), figisDoc);
		profile(disseminationYearSlice.getProfile(), figisDoc);
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
			// • AddInfo
			// • RelatedResources

			GeneralBiology gb = f.createGeneralBiology();
			Text text1 = cu.getCdataText(profile.getDescriptionBiological());
			gb.getTextsAndImagesAndTables().add(text1);
			HabitatBio habitatBio = f.createHabitatBio();

			new AddWhenContentRule<Object>().check(cu.getCdataString(profile.getDescriptionBiological()))
					.beforeAdding(gb).to(habitatBio.getClimaticZonesAndDepthZonesAndDepthBehavs());

			// Physical profile
			// terrible workaround because if there is no Text, profileEnglish
			// will also not be added by Jaxb.
			Text descriptionPhisical = cu.getCdataText(profile.getDescriptionPhisical());
			if (descriptionPhisical == null) {
				descriptionPhisical = new Text();
				descriptionPhisical.getContent().add(" ");
			}

			JAXBElement<Text> geoformJAXBElement = f.createGeoFormText(descriptionPhisical);
			GeoForm geoform = f.createGeoForm();
			new AddWhenContentRule<Serializable>().check(profile.getDescriptionPhisical())
					.beforeAdding(geoformJAXBElement).to(geoform.getContent());

			String profileEnglish = u.getEnglish(profile.getGeoform());
			// fi:FIGISDoc/fi:VME/fi:HabitatBio/fi:GeoForm@Value (if Value =
			// Seamounts or Canyons) or
			// fi:FIGISDoc/fi:VME/fi:HabitatBio/fi:GeoForm@FreeValue (for other
			// values)

			// FFiorellato: fixed following conditions. Previously they were
			// using '==' and a variable LHS that would produce NPE when the LHS
			// was null
			if ("Seamounts".equals(profileEnglish) || "Canyons".equals(profileEnglish)) {
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
			new AddWhenContentRule<Object>().check(profile.getDescriptionPhisical())
					.check(profile.getDescriptionImpact()).check(profile.getDescriptionBiological())
					.check(profile.getGeoform()).beforeAdding(habitatBio)
					.to(figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts());

			// Impacts profile
			Impacts impacts = f.createImpacts();
			Text text3 = cu.getCdataText(profile.getDescriptionImpact());
			impacts.getTextsAndImagesAndTables().add(text3);
			new AddWhenContentRule<Object>().check(text3).beforeAdding(impacts)
					.to(figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts());

		}

	}

	public void specificMeasureList(List<SpecificMeasure> specificMeasureList, FIGISDoc figisDoc) {
		for (SpecificMeasure specificMeasure : specificMeasureList) {
			specificMeasure(specificMeasure, figisDoc);
		}
	}

	/**
	 * Adds specificMeasures to a FIGISDoc
	 * 
	 * measureSummary fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi: ManagementMethodEntry@Focus=
	 * "Vulnerable Marine Ecosystems"/fi:Measure/fi:Text fi:FIGISDoc/fi:VME/fi:Management
	 * /fi:ManagementMethods/fi:ManagementMethodEntry
	 * 
	 * @Focus="Vulnerable Marine Ecosystems"/dc:Title[VME-specific measures]
	 *                    fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods /fi:ManagementMethodEntry@Focus=
	 *                    "Vulnerable Marine Ecosystems"/fi:Measure/MeasureType Value="vulnerable marine ecosystem"
	 *                    fi:FIGISDoc/fi:VME/ fi:Management/fi:ManagementMethods/fi :ManagementMethodEntry
	 *                    /fi:Measure/fi:Text
	 * 
	 *                    Source/url fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 *                    /fi:ManagementMethodEntry/fi:Measure /fi:Sources/fi :BiblioEntry/dc:Identifier@Type="URI"
	 * 
	 *                    Source/citation fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods
	 *                    /fi:ManagementMethodEntry/ fi:Measure/fi:Sources /fi:BiblioEntry/dcterms:bibliographicCitation
	 * 
	 *                    ValidityPeriod/beginYear fi:FIGISDoc/fi:VME/fi:Management /fi:ManagementMethods/fi
	 *                    :ManagementMethodEntry/fi:Measure /fi:Range@Type="Time"/fi:Min
	 * 
	 *                    ValidityPeriod/endYear fi:FIGISDoc/fi:VME/fi:Management /fi:ManagementMethods/fi
	 *                    :ManagementMethodEntry/fi:Measure/fi :Range@Type="Time"/fi:Max
	 * 
	 * 
	 * @param specificMeasure
	 * @param figisDoc
	 */
	public void specificMeasure(SpecificMeasure specificMeasure, FIGISDoc figisDoc) {

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

			Text measureText = cu.getCdataText(specificMeasure.getVmeSpecificMeasure());
			measure.getTextsAndImagesAndTables().add(measureText);

			// contentRule.add(content, measureType,
			// measure.getTextsAndImagesAndTables())

			// range (time)
			Range range = f.createRange();
			range.setType("Time");
			measure.getTextsAndImagesAndTables().add(range);

			if (specificMeasure.getValidityPeriod() != null) {
				JAXBElement<Min> minJAXBElement = f.createRangeMin(f.createMin());
				uj.fillObject(du.createUiString(specificMeasure.getValidityPeriod().getBeginDate()), minJAXBElement);

				JAXBElement<Max> maxJAXBElement = f.createRangeMax(f.createMax());
				uj.fillObject(du.createUiString(specificMeasure.getValidityPeriod().getEndDate()), maxJAXBElement);

				uj.fillObject(du.createUiString(specificMeasure.getValidityPeriod().getEndDate()), maxJAXBElement);

				range.getContent().add(minJAXBElement);
				range.getContent().add(maxJAXBElement);
			}

			// review year
			// fi:FIGISDoc/fi:VME/fi:Management/fi:ManagementMethods/fi:ManagementMethodEntry/fi:Measure/fi:Range@Type="Time"/fi:AdditionalValue
			if (specificMeasure.getReviewYear() != null) {
				JAXBElement<AdditionalValue> additionalValueJAXBElement = f.createRangeAdditionalValue(f
						.createAdditionalValue());
				uj.fillObject(specificMeasure.getReviewYear(), additionalValueJAXBElement);
				range.getContent().add(additionalValueJAXBElement);
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

}
