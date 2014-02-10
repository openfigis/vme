package org.fao.fi.vme.batch.sync2.mapping.xml;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.figis.devcon.ManagementMethodEntry;
import org.fao.fi.vme.batch.sync2.mapping.xml.GeneralMeasureManagementMethodEntryBuilder;
import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.test.GeneralMeasureMock;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.junit.Test;

public class GeneralMeasureManagementMethodEntryBuilderTest {

	GeneralMeasureManagementMethodEntryBuilder b = new GeneralMeasureManagementMethodEntryBuilder();

	@Test
	public void testAddSources() {
		InformationSource i = InformationSourceMock.create();
		List<InformationSource> l = new ArrayList<InformationSource>();
		l.add(i);
		GeneralMeasure g = GeneralMeasureMock.create();
		g.setInformationSourceList(l);

		ManagementMethodEntry m = new ManagementMethodEntry();

		assertEquals(0, m.getTextsAndImagesAndTables().size());
		b.addSources(g, m);
		assertEquals(1, m.getTextsAndImagesAndTables().size());
	}
}
