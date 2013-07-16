package org.fao.fi.vme.sync2.mapping.xml;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.FigisID;
import org.fao.fi.figis.devcon.ForeignID;
import org.fao.fi.figis.devcon.HabitatBio;
import org.fao.fi.figis.devcon.Impacts;
import org.fao.fi.figis.devcon.Max;
import org.fao.fi.figis.devcon.Min;
import org.fao.fi.figis.devcon.Range;
import org.fao.fi.figis.devcon.Text;
import org.fao.fi.figis.devcon.VME;
import org.fao.fi.figis.devcon.VMECriteria;
import org.fao.fi.figis.devcon.VMEIdent;
import org.fao.fi.figis.devcon.VMEType;
import org.fao.fi.figis.devcon.WaterAreaRef;
import org.fao.fi.vme.domain.Profile;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.test.VmeMock;
import org.junit.Before;
import org.junit.Test;
import org.vme.fimes.jaxb.JaxbMarshall;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FigisDocBuilderTest {

	FigisDocBuilder b;
	JaxbMarshall m;
	MultiLingualStringUtil u;
	int nrOfYears = 2;
	Vme vme;
	
	@Before
	public void prepareBefore(){
		 b = new FigisDocBuilder();
		 m = new JaxbMarshall();
		 u = new MultiLingualStringUtil();
		 vme = VmeMock.generateVme(nrOfYears);
	}
	
	@Test
	public void testSpecificMeasures() {
		// TODO
	}

	@Test
	public void testVmeHistory() {
		// TODO
	}

	@Test
	public void testRfmoHistory() {
		// TODO
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

		Profile profile = new Profile();
		profile.setDescriptionBiological(u.english(descriptionBiological));
		profile.setDescriptionPhisical(u.english(descriptionPhysical));
		profile.setDescriptionImpact(u.english(descriptionImpact));

		FIGISDoc figisDoc = new FIGISDoc();

		// assumed the existence of VME.
		figisDoc.setVME(new VME());

		// perform the logic
		b.profile(profile, figisDoc);

		// test
		List<Object> list = figisDoc.getVME().getOverviewsAndHabitatBiosAndImpacts();
		assertEquals(2, list.size());

		HabitatBio habitatBio = (HabitatBio) list.get(0);
		Text text1 = (Text) habitatBio.getClimaticZonesAndDepthZonesAndDepthBehavs().get(0);
		assertEquals(descriptionBiological, text1.getContent().get(0));

		Impacts Impacts = (Impacts) list.get(1);
		Text text3 = (Text) Impacts.getTextsAndImagesAndTables().get(0);
		assertEquals(descriptionImpact, text3.getContent().get(0));

	}

	@Test
	public void testGeneralMeasures() {
		// TODO
	}

	@Test
	public void testVme() {
		FIGISDoc figisDoc = new FIGISDoc();
		b.vme(vme, figisDoc);
		
		assertNotNull(figisDoc.getVME());
		assertNotNull(figisDoc.getVME().getVMEIdent());
		assertNotNull(figisDoc.getVME().getVMEIdent()
				.getFigisIDsAndForeignIDsAndWaterAreaReves());
		assertTrue(figisDoc.getVME().getVMEIdent()
				.getFigisIDsAndForeignIDsAndWaterAreaReves().size() > 0);

		// test VMEIDent properties encoding
		for (Object obj : figisDoc.getVME().getVMEIdent()
				.getFigisIDsAndForeignIDsAndWaterAreaReves()) {
			if (obj instanceof FigisID) {
				assertEquals(Long.toString(vme.getId()),
						((FigisID) obj).getContent());
			} else if (obj instanceof ForeignID) {
				assertEquals(vme.getInventoryIdentifier(),
						((ForeignID) obj).getCode());
			} else if (obj instanceof WaterAreaRef) {
				assertEquals(vme.getGeoRefList().get(0)
						.getGeographicFeatureID(),
						((ForeignID) ((WaterAreaRef) obj)
								.getFigisIDsAndForeignIDs().get(0)).getCode());
			} else if (obj instanceof VMEType) {
				assertEquals(vme.getAreaType(), ((VMEType) obj).getValue());
			} else if (obj instanceof VMECriteria) {
				assertEquals(vme.getCriteria(), ((VMECriteria) obj).getValue());
			} else if (obj instanceof Range) {
				JAXBElement<Min> min = (JAXBElement<Min>) ((Range) obj)
						.getContent().get(0);
				assertEquals(vme.getValidityPeriod().getBeginYear().toString(),
						min.getValue().getContent());

				JAXBElement<Max> max = (JAXBElement<Max>) ((Range) obj)
						.getContent().get(1);
				assertEquals(vme.getValidityPeriod().getEndYear().toString(),
						max.getValue().getContent());
			}
		}
	}

	@Test
	public void testYear() {
		String reportingYear = Integer.toString(2013);
		FIGISDoc figisDoc = new FIGISDoc();
  
		figisDoc.setVME(new VME());
		figisDoc.getVME().setVMEIdent(new VMEIdent());
		b.year(reportingYear, figisDoc);
		
		for(Object obj : figisDoc.getVME().getVMEIdent().getFigisIDsAndForeignIDsAndWaterAreaReves()){
			if( obj instanceof String){
				assertEquals(reportingYear, obj);
			}
		}
		
	}

	@Test
	public void testFigisDocMarshall(){
		FIGISDoc figisDoc = new FIGISDoc();
		b.vme(vme, figisDoc);
		b.year(vme.getValidityPeriod().getBeginYear().toString(), figisDoc);
		
		String s = m.marshalToString(figisDoc);
		assertTrue(s.contains(VMEIdent.class.getSimpleName()));
		
		System.out.println(s);
	}
	
}
