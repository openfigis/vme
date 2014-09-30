package org.fao.fi.vme.batch.sync2.mapping.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.JAXBElement;

import org.apache.commons.lang.StringUtils;
import org.fao.fi.figis.devcon.BiblioEntry;
import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.FigisID;
import org.fao.fi.figis.devcon.ForeignID;
import org.fao.fi.figis.devcon.GeoForm;
import org.fao.fi.figis.devcon.GeoReference;
import org.fao.fi.figis.devcon.HabitatBio;
import org.fao.fi.figis.devcon.Management;
import org.fao.fi.figis.devcon.ManagementMethodEntry;
import org.fao.fi.figis.devcon.ManagementMethods;
import org.fao.fi.figis.devcon.Max;
import org.fao.fi.figis.devcon.Measure;
import org.fao.fi.figis.devcon.Min;
import org.fao.fi.figis.devcon.ObjectFactory;
import org.fao.fi.figis.devcon.Range;
import org.fao.fi.figis.devcon.RelatedResources;
import org.fao.fi.figis.devcon.Sources;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.figis.devcon.VME;
import org.fao.fi.figis.devcon.VMEIdent;
import org.fao.fi.figis.devcon.WaterAreaRef;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.support.VmeSimpleDateFormat;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.purl.agmes._1.CreatorCorporate;
import org.purl.dc.elements._1.Identifier;
import org.purl.dc.terms.Abstrakt;
import org.purl.dc.terms.BibliographicCitation;
import org.purl.dc.terms.Created;
import org.vme.dao.test.ReferenceDaoMockImpl;
import org.vme.fimes.jaxb.JaxbMarshall;

@RunWith(CdiRunner.class)
@ActivatedAlternatives(ReferenceDaoMockImpl.class)
public class FigisDocBuilderVmeTest {

	@Inject
	FigisDocBuilderVme b;
	JaxbMarshall m;
	MultiLingualStringUtil u;
	int nrOfYears = 2;
	Vme vme;

	CdataUtil cu = new CdataUtil();

	ObjectFactory f = new ObjectFactory();
	private VmeSimpleDateFormat du = new VmeSimpleDateFormat();

	@Before
	public void prepareBefore() {
		m = new JaxbMarshall();
		u = new MultiLingualStringUtil();
		vme = VmeMock.generateVme(nrOfYears);
		vme.setId(67l);
	}

	@Test
	public void testMediaReference() {
		FIGISDoc figisDoc = new FIGISDoc();
		figisDoc.setVME(f.createVME());
		b.mediaReference(vme, figisDoc);
		List<Object> l = figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts();
		int media = 0;
		for (Object object : l) {
			if (object instanceof RelatedResources) {
				System.out.println(object.getClass().getSimpleName());
				media++;
			}
		}
		assertEquals(1, media);
	}

	@Test
	public void testHabitatBio() {
		FIGISDoc figisDoc = new FIGISDoc();
		figisDoc.setVME(new VME());

		GeoForm geoform = f.createGeoForm();
		CdataUtil ut = new CdataUtil();
		Text descriptionPhisical = ut.getCdataText(null);
		JAXBElement<Text> geoformJAXBElement = f.createGeoFormText(descriptionPhisical);
		// geoform.getContent().add(geoformJAXBElement);

		new AddWhenContentRule<Serializable>().check(descriptionPhisical).beforeAdding(geoformJAXBElement)
				.to(geoform.getContent());

		HabitatBio habitatBio = f.createHabitatBio();
		habitatBio.getClimaticZonesAndDepthZonesAndDepthBehavs().add(geoform); // geoForm
																				// is
																				// part
																				// of
																				// HabitatBio

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
		InformationSource i = InformationSourceMock.create();
		specificMeasure.setInformationSource(i);

		b.specificMeasures(specificMeasure, figisDoc);
		Management management = (Management) figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().get(0);
		assertNotNull(management);

		ManagementMethods manMethods = (ManagementMethods) management.getTextsAndImagesAndTables().get(0);
		assertNotNull(manMethods);

		ManagementMethodEntry entry = (ManagementMethodEntry) manMethods.getManagementMethodEntriesAndTextsAndImages()
				.get(0);
		assertNotNull(entry);

		assertEquals(FigisDocBuilderAbstract.VULNERABLE_MARINE_ECOSYSTEMS, entry.getFocus());
		assertEquals(FigisDocBuilderAbstract.VME_SPECIFIC_MEASURES, entry.getTitle().getContent());

		Measure measure = (Measure) entry.getTextsAndImagesAndTables().get(0);
		assertNotNull(measure);
		assertTrue(measure.getTextsAndImagesAndTables().size() > 1);

		Sources sources = (Sources) measure.getTextsAndImagesAndTables().get(3);
		BiblioEntry biblioEntry = (BiblioEntry) sources.getTextsAndImagesAndTables().get(0);
		BibliographicCitation c = (BibliographicCitation) biblioEntry.getContent().get(0);

		assertEquals(cu.getCdataString(InformationSourceMock.CIT), c.getContent());

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

	@SuppressWarnings("unchecked")
	@Test
	public void testVme() {
		FIGISDoc figisDoc = new FIGISDoc();

		b.vme(vme, vme.getGeoRefList().get(0), VmeMock.YEAR, figisDoc);

		assertNotNull(figisDoc.getVME());
		assertNotNull(figisDoc.getVME().getVMEIdent());
		assertNotNull(figisDoc.getVME().getVMEIdent().getFigisIDsAndForeignIDsAndWaterAreaReves());
		assertTrue(figisDoc.getVME().getVMEIdent().getFigisIDsAndForeignIDsAndWaterAreaReves().size() > 0);

		int countingGeoReference = 0;

		// test VMEIDent properties encoding
		for (Object obj : figisDoc.getVME().getVMEIdent().getFigisIDsAndForeignIDsAndWaterAreaReves()) {
			if (obj instanceof FigisID) {
				assertEquals(Long.toString(vme.getId()), ((FigisID) obj).getContent());
			} else if (obj instanceof ForeignID) {
				assertEquals(vme.getInventoryIdentifier(), ((ForeignID) obj).getCode());
			} else if (obj instanceof WaterAreaRef) {
				assertEquals(vme.getGeoRefList().get(0).getGeographicFeatureID(), ((ForeignID) ((WaterAreaRef) obj)
						.getFigisIDsAndForeignIDs().get(0)).getCode());
				// } else if (obj instanceof VMEType) {
				// assertEquals(vme.getAreaType().getName(), ((VMEType)
				// obj).getValue());
			} else if (obj instanceof Range) {
				assertEquals("Time", ((Range) obj).getType());
				JAXBElement<Min> min = (JAXBElement<Min>) ((Range) obj).getContent().get(0);

				assertEquals(du.createUiString(vme.getValidityPeriod().getBeginDate()), min.getValue());

				JAXBElement<Max> max = (JAXBElement<Max>) ((Range) obj).getContent().get(1);
				assertEquals(du.createUiString(vme.getValidityPeriod().getEndDate()), max.getValue());
			} else if (obj instanceof GeoReference) {
				countingGeoReference++;
			}
		}
		assertEquals(1, countingGeoReference);
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
		b.informationSource(infoSourceList, VmeMock.YEAR, figisDoc);

		Sources sources = (Sources) figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts().get(0);
		assertNotNull(sources);

		assertTrue(infoSourceList.size() > 0);

		for (int i = 0; i < sources.getTextsAndImagesAndTables().size(); i++) {

			BiblioEntry biblioEntry = (BiblioEntry) sources.getTextsAndImagesAndTables().get(i);
			assertNotNull(biblioEntry);

			for (Object obj : biblioEntry.getContent()) {
				if (obj instanceof CreatorCorporate) {
					assertEquals(cu.getCdataString(infoSourceList.get(i).getCommittee()),
							((CreatorCorporate) obj).getContent());
				} else if (obj instanceof Created) {
					assertEquals(infoSourceList.get(i).getPublicationYear(), new Integer(((Created) obj).getContent()),
							1);
				} else if (obj instanceof Abstrakt) {
					assertEquals(cu.getCdataString(infoSourceList.get(i).getReportSummary()),
							((Abstrakt) obj).getContent());
				} else if (obj instanceof BibliographicCitation) {
					assertEquals(cu.getCdataString(infoSourceList.get(i).getCitation()),
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
		b.vme(vme, vme.getGeoRefList().get(0), VmeMock.YEAR, figisDoc);
		b.profile(vme.getProfileList().get(0), figisDoc);

		b.specificMeasures(vme.getSpecificMeasureList().get(0), figisDoc);
		b.informationSource(vme.getRfmo().getInformationSourceList(), 2000, figisDoc);

		String s = m.marshalToString(figisDoc);

		System.out.println(s);
		assertTrue(s.contains(VMEIdent.class.getSimpleName()));
		// System.out.println(s);
		assertEquals(1, StringUtils.countMatches(s, "<fi:Management>"));
		assertEquals(1, StringUtils.countMatches(s, "<fi:ManagementMethods>"));

	}

}
