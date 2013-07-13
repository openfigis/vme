package org.fao.fi.vme.sync2.mapping.xml;

import org.fao.fi.figis.devcon.FIGISDoc;
import org.fao.fi.figis.devcon.VMEIdent;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.test.VmeMock;
import org.junit.Test;
import org.vme.fimes.jaxb.JaxbMarshall;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FigisDocBuilderTest {

	FigisDocBuilder b = new FigisDocBuilder();
	int nrOfYears = 2;
	JaxbMarshall m = new JaxbMarshall();

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

	@Test
	public void testProfile() {
		// TODO
	}

	@Test
	public void testGeneralMeasures() {
		// TODO
	}

	@Test
	public void testVme() {
		Vme vme = VmeMock.generateVme(nrOfYears);
		FIGISDoc figisDoc = new FIGISDoc();
		b.vme(vme, figisDoc);
		assertNotNull(figisDoc.getVME());
		assertNotNull(figisDoc.getVME().getVMEIdent());
		String s = m.marshalToString(figisDoc);
		System.out.println(s);
		assertTrue(s.contains(VMEIdent.class.getSimpleName()));

	}

	@Test
	public void testYear() {
		// TODO
	}

}
