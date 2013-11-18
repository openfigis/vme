package org.fao.fi.vme.sync2.mapping.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.StringUtils;
import org.fao.fi.figis.devcon.BiblioEntry;
import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.FigisID;
import org.fao.fi.figis.devcon.FisheryArea;
import org.fao.fi.figis.devcon.ForeignID;
import org.fao.fi.figis.devcon.GeoForm;
import org.fao.fi.figis.devcon.HabitatBio;
import org.fao.fi.figis.devcon.History;
import org.fao.fi.figis.devcon.Management;
import org.fao.fi.figis.devcon.ManagementMethodEntry;
import org.fao.fi.figis.devcon.ManagementMethods;
import org.fao.fi.figis.devcon.Max;
import org.fao.fi.figis.devcon.Measure;
import org.fao.fi.figis.devcon.MeasureType;
import org.fao.fi.figis.devcon.Min;
import org.fao.fi.figis.devcon.ObjectFactory;
import org.fao.fi.figis.devcon.Range;
import org.fao.fi.figis.devcon.Sources;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.figis.devcon.VME;
import org.fao.fi.figis.devcon.VMECriteria;
import org.fao.fi.figis.devcon.VMEIdent;
import org.fao.fi.figis.devcon.VMEType;
import org.fao.fi.figis.devcon.WaterAreaRef;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.junit.Before;
import org.junit.Test;
import org.purl.agmes._1.CreatorCorporate;
import org.purl.dc.elements._1.Identifier;
import org.purl.dc.terms.Abstrakt;
import org.purl.dc.terms.BibliographicCitation;
import org.purl.dc.terms.Created;
import org.vme.fimes.jaxb.JaxbMarshall;

public class FigisDocBuilderTest {

	FigisDocBuilder b;
	JaxbMarshall m;
	MultiLingualStringUtil u;
	int nrOfYears = 2;
	Vme vme;

	ObjectFactory f = new ObjectFactory();

	@Before
	public void prepareBefore() {
		b = new FigisDocBuilder();
		m = new JaxbMarshall();
		u = new MultiLingualStringUtil();
		vme = VmeMock.generateVme(nrOfYears);
	}

	@Test
	public void testHabitatBio() {
		FIGISDoc figisDoc = new FIGISDoc();
		figisDoc.setVME(new VME());

		GeoForm geoform = f.createGeoForm();
		EnglishTextUtil ut = new EnglishTextUtil();
		Text descriptionPhisical = ut.getEnglishText(null);
		JAXBElement<Text> geoformJAXBElement = f.createGeoFormText(descriptionPhisical);
		// geoform.getContent().add(geoformJAXBElement);

		new AddWhenContentRule<Serializable>().check(descriptionPhisical).beforeAdding(geoformJAXBElement)
				.to(geoform.getContent());

		HabitatBio habitatBio = f.createHabitatBio();
		habitatBio.getClimaticZonesAndDepthZonesAndDepthBehavs().add(geoform); // geoForm is part of HabitatBio

		habitatBio.getClimaticZonesAndDepthZonesAndDepthBehavs();
		figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().add(habitatBio);
		String xmlString = m.marshalToString(figisDoc);
		System.out.println(xmlString);
		assertFalse(xmlString.contains("<fi:Text xsi:nil="));

	}

	@Test
	public void testSpecificMeasures() {
		FIGISDoc figisDoc = new FIGISDoc();
		figisDoc.setVME(new VME());

		SpecificMeasure specificMeasure = vme.getSpecificMeasureList().get(0);
		b.specificMeasures(specificMeasure, figisDoc);
		Management management = (Management) figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().get(0);
		assertNotNull(management);

		ManagementMethods manMethods = (ManagementMethods) management.getTextsAndImagesAndTables().get(0);
		assertNotNull(manMethods);

		ManagementMethodEntry entry = (ManagementMethodEntry) manMethods.getManagementMethodEntriesAndTextsAndImages()
				.get(0);
		assertNotNull(entry);
		assertEquals(FigisDocBuilder.VULNERABLE_MARINE_ECOSYSTEMS, entry.getFocus());
		assertEquals(FigisDocBuilder.VME_SPECIFIC_MEASURES, entry.getTitle().getContent());

		Measure measure = (Measure) entry.getTextsAndImagesAndTables().get(0);
		assertNotNull(measure);
		assertTrue(measure.getTextsAndImagesAndTables().size() > 1);

	}

	/**
	 * FishingArea_history fi:FIGISDoc/fi:VME/fi:FisheryArea/fi:Text
	 * 
	 * VME_history fi:FIGISDoc/fi:VME/fi:History/fi:Text
	 * 
	 */
	@Test
	public void testHistory() {
		FIGISDoc figisDoc = new FIGISDoc();
		figisDoc.setVME(new VME());
		b.fisheryArea(vme.getRfmo().getHasFisheryAreasHistory().get(0), figisDoc);
		b.vmesHistory(vme.getRfmo().getHasFisheryAreasHistory().get(0), figisDoc);
		assertNotNull(figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().get(0));
		assertTrue(figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().get(0) instanceof FisheryArea);
		assertTrue(figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().get(1) instanceof History);

	}

	/**
	 * 
	 * descriptionBiological fi:FIGISDoc/fi:VME/fi:HabitatBio/fi:Text
	 * 
	 * descriptionPhysical fi:FIGISDoc/fi:VME/fi:HabitatBio/fi:GeoForm/fi:Text
	 * 
	 * descriptionImpact fi:FIGISDoc/fi:VME/fi:Impacts/fi
	 */
	@Test
	public void testProfile() {
		String descriptionBiological = "111111111111111";
		String descriptionPhysical = "22222222222222222";
		String descriptionImpact = "333333333333333333333333333";
		String geoform = "geoform";

		Profile profile = new Profile();
		profile.setDescriptionBiological(u.english(descriptionBiological));
		profile.setDescriptionPhisical(u.english(descriptionPhysical));
		profile.setDescriptionImpact(u.english(descriptionImpact));
		profile.setGeoform(u.english(geoform));

		FIGISDoc figisDoc = new FIGISDoc();

		// assumed the existence of VME.
		figisDoc.setVME(new VME());

		// perform the logic
		b.profile(profile, figisDoc);

		// test
		List<Object> list = figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts();
		assertEquals(2, list.size());

	}

	@Test
	public void testGeneralMeasures() {
		FIGISDoc figisDoc = new FIGISDoc();

		VME vmeJAXB = new VME();
		figisDoc.setVME(vmeJAXB);

		GeneralMeasure generalMeasure = vme.getRfmo().getGeneralMeasureList().get(0);
		b.generalMeasures(generalMeasure, figisDoc);

		Management management = (Management) figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().get(0);
		assertNotNull(management);

		ManagementMethods manMethods = (ManagementMethods) management.getTextsAndImagesAndTables().get(0);
		assertNotNull(manMethods);

		ManagementMethodEntry entry = (ManagementMethodEntry) manMethods.getManagementMethodEntriesAndTextsAndImages()
				.get(0);
		assertNotNull(entry);
		assertEquals("Vulnerable Marine Ecosystems", entry.getFocus());
		assertEquals("VME general measures", entry.getTitle().getContent());

		for (Object obj : entry.getTextsAndImagesAndTables()) {
			if (obj instanceof Measure) {
				MeasureType mType = (MeasureType) ((Measure) obj).getTextsAndImagesAndTables().get(0);
				if (mType.getValue().equals("Fishing_Areas")) {
					assertEquals(generalMeasure.getFishingAreas(), ((Measure) obj).getTextsAndImagesAndTables().get(1));
				} else if (mType.getValue().equals("Exploratory_fishing_protocol")) {
					assertEquals(u.getEnglish(generalMeasure.getExplorataryFishingProtocols()), ((Text) ((Measure) obj)
							.getTextsAndImagesAndTables().get(1)).getContent().get(0));
				} else if (mType.getValue().equals("VME_encounter_protocols")) {
					assertEquals(u.getEnglish(generalMeasure.getVmeEncounterProtocols()), ((Text) ((Measure) obj)
							.getTextsAndImagesAndTables().get(1)).getContent().get(0));
				} else if (mType.getValue().equals("VME_threshold")) {
					assertEquals(u.getEnglish(generalMeasure.getVmeThreshold()), ((Text) ((Measure) obj)
							.getTextsAndImagesAndTables().get(1)).getContent().get(0));
				} else if (mType.getValue().equals("VME_indicatorspecies")) {
					assertEquals(u.getEnglish(generalMeasure.getVmeIndicatorSpecies()), ((Text) ((Measure) obj)
							.getTextsAndImagesAndTables().get(1)).getContent().get(0));
				}

			} else if (obj instanceof Range) {
				assertEquals("Time", ((Range) obj).getType());
				assertEquals(generalMeasure.getValidityPeriod().getBeginYear().toString(),
						((JAXBElement<Min>) ((Range) obj).getContent().get(0)).getValue().getContent());
				assertEquals(generalMeasure.getValidityPeriod().getEndYear().toString(),
						((JAXBElement<Max>) ((Range) obj).getContent().get(1)).getValue().getContent());

			} else if (obj instanceof Sources) {
				List<InformationSource> infoSourceList = generalMeasure.getInformationSourceList();
				for (int i = 0; i < infoSourceList.size(); i++) {
					BiblioEntry biblioEntry = (BiblioEntry) ((Sources) obj).getTextsAndImagesAndTables().get(i);
					for (Object bObj : biblioEntry.getContent()) {
						if (bObj instanceof BibliographicCitation) {
							assertEquals(u.getEnglish(infoSourceList.get(i).getCitation()),
									((BibliographicCitation) bObj).getContent());
						} else if (bObj instanceof Identifier) {
							assertEquals("URI", ((Identifier) bObj).getType());
							assertEquals(infoSourceList.get(i).getUrl().toString(), ((Identifier) bObj).getContent());
						}
					}
				}
			}

		}

	}

	@Test
	public void testVme() {
		FIGISDoc figisDoc = new FIGISDoc();
		b.vme(vme, VmeMock.YEAR, figisDoc);

		assertNotNull(figisDoc.getVME());
		assertNotNull(figisDoc.getVME().getVMEIdent());
		assertNotNull(figisDoc.getVME().getVMEIdent().getFigisIDsAndForeignIDsAndWaterAreaReves());
		assertTrue(figisDoc.getVME().getVMEIdent().getFigisIDsAndForeignIDsAndWaterAreaReves().size() > 0);

		// test VMEIDent properties encoding
		for (Object obj : figisDoc.getVME().getVMEIdent().getFigisIDsAndForeignIDsAndWaterAreaReves()) {
			if (obj instanceof FigisID) {
				assertEquals(Long.toString(vme.getId()), ((FigisID) obj).getContent());
			} else if (obj instanceof ForeignID) {
				assertEquals(vme.getInventoryIdentifier(), ((ForeignID) obj).getCode());
			} else if (obj instanceof WaterAreaRef) {
				assertEquals(vme.getGeoRefList().get(0).getGeographicFeatureID(), ((ForeignID) ((WaterAreaRef) obj)
						.getFigisIDsAndForeignIDs().get(0)).getCode());
			} else if (obj instanceof VMEType) {
				assertEquals(vme.getAreaType(), ((VMEType) obj).getValue());
			} else if (obj instanceof VMECriteria) {
				assertEquals(vme.getCriteria(), ((VMECriteria) obj).getValue());
			} else if (obj instanceof Range) {
				assertEquals("Time", ((Range) obj).getType());
				JAXBElement<Min> min = (JAXBElement<Min>) ((Range) obj).getContent().get(0);
				assertEquals(vme.getValidityPeriod().getBeginYear().toString(), min.getValue().getContent());

				JAXBElement<Max> max = (JAXBElement<Max>) ((Range) obj).getContent().get(1);
				assertEquals(vme.getValidityPeriod().getEndYear().toString(), max.getValue().getContent());
			}
		}
	}

	@Test
	public void testYear() {
		String reportingYear = Integer.toString(2013);
		FIGISDoc figisDoc = new FIGISDoc();

		figisDoc.setVME(new VME());
		figisDoc.getVME().setVMEIdent(new VMEIdent());

		for (Object obj : figisDoc.getVME().getVMEIdent().getFigisIDsAndForeignIDsAndWaterAreaReves()) {
			if (obj instanceof String) {
				assertEquals(reportingYear, obj);
			}
		}

	}

	@Test
	public void testInformationSource() {
		FIGISDoc figisDoc = new FIGISDoc();
		figisDoc.setVME(new VME());

		List<InformationSource> infoSourceList = vme.getRfmo().getInformationSourceList();
		b.informationSource(infoSourceList, figisDoc);

		Sources sources = (Sources) figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().get(0);
		assertNotNull(sources);

		for (int i = 0; i < infoSourceList.size(); i++) {

			BiblioEntry biblioEntry = (BiblioEntry) sources.getTextsAndImagesAndTables().get(i);
			assertNotNull(biblioEntry);

			for (Object obj : biblioEntry.getContent()) {
				if (obj instanceof CreatorCorporate) {
					assertEquals(u.getEnglish(infoSourceList.get(i).getCommittee()),
							((CreatorCorporate) obj).getContent());
				} else if (obj instanceof Created) {
					assertEquals(Integer.toString(infoSourceList.get(i).getPublicationYear()),
							((Created) obj).getContent());
				} else if (obj instanceof Abstrakt) {
					assertEquals(u.getEnglish(infoSourceList.get(i).getReportSummary()), ((Abstrakt) obj).getContent());
				} else if (obj instanceof BibliographicCitation) {
					assertEquals(u.getEnglish(infoSourceList.get(i).getCitation()),
							((BibliographicCitation) obj).getContent());
				} else if (obj instanceof Identifier) {
					assertEquals("URI", ((Identifier) obj).getType());
					assertEquals(infoSourceList.get(i).getUrl().toString(), ((Identifier) obj).getContent());
				}
			}
		}
	}

	@Test
	public void testFigisDocMarshall() {
		FIGISDoc figisDoc = new FIGISDoc();
		b.vme(vme, VmeMock.YEAR, figisDoc);
		b.profile(vme.getProfileList().get(0), figisDoc);

		b.specificMeasures(vme.getSpecificMeasureList().get(0), figisDoc);
		b.generalMeasures(vme.getRfmo().getGeneralMeasureList().get(0), figisDoc);
		b.informationSource(vme.getRfmo().getInformationSourceList(), figisDoc);

		String s = m.marshalToString(figisDoc);

		System.out.println(s);
		assertTrue(s.contains(VMEIdent.class.getSimpleName()));
		// System.out.println(s);
		assertEquals(1, StringUtils.countMatches(s, "<fi:Management>"));
		assertEquals(1, StringUtils.countMatches(s, "<fi:ManagementMethods>"));

	}

}
