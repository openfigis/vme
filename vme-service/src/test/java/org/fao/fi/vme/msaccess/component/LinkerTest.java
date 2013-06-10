package org.fao.fi.vme.msaccess.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.fao.fi.vme.dao.config.VmeDataBaseProducer;
import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.InformationSource;
import org.fao.fi.vme.domain.Rfmo;
import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.model.Table;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class })
public class LinkerTest {

	@Inject
	private VmeWriter writer;

	private final MsAcces2DomainMapper m = new MsAcces2DomainMapper();
	private final Linker linker = new Linker();

	private final VmeReader reader = new VmeReader();

	@Test
	@Ignore
	public void testLink() {
		// read from MSAccess
		List<Table> tables = reader.readObjects();

		// convert the table objects to domain objects
		List<ObjectCollection> objectCollectionList = new ArrayList<ObjectCollection>();
		for (Table table : tables) {
			objectCollectionList.add(m.map(table));
		}

		// connect/link the domain objects, also using the original information from the table objects
		linker.link(objectCollectionList, tables);

		for (ObjectCollection objectCollection : objectCollectionList) {
			List<Object> objects = objectCollection.getObjectList();
			for (Object object : objects) {
				if (object instanceof Vme) {
					validateVmeObject(object);
				}
				if (object instanceof Rfmo) {
					validateRfmoObject(object);
				}
				if (object instanceof InformationSource) {
					validateInformationSource(object);
				}
				if (object instanceof SpecificMeasures) {
					validateSpecificMeasuresObject(object);
				}
				if (object instanceof GeneralMeasures) {
					validateGeneralMeasuresObject(object);
				}
			}
		}
		validateRelationVmeSpecificMeasure(objectCollectionList);

	}

	private void validateRelationVmeSpecificMeasure(List<ObjectCollection> objectCollectionList) {
		ObjectCollection specificMeasuresCollection = null;
		for (ObjectCollection objectCollection : objectCollectionList) {
			// if (objectCollection.getClazz().equals(VmeDB.class)) {
			// vmeCollection = objectCollection;
			// }
			if (objectCollection.getClazz().equals(SpecificMeasures.class)) {
				specificMeasuresCollection = objectCollection;
			}
		}
		List<Object> smList = specificMeasuresCollection.getObjectList();
		Set<String> vmeIds = new HashSet<String>();
		for (Object object : smList) {
			SpecificMeasures sm = (SpecificMeasures) object;
			String combi = sm.getVmeList().get(0).getId() + " " + sm.getId();
			System.out.println(combi);
			assertFalse(vmeIds.contains(combi));
			assertTrue(vmeIds.add(combi));
		}

	}

	private void validateInformationSource(Object object) {
		InformationSource o = (InformationSource) object;
		// assertNotNull(o.getMeetingDocument());

		assertNotNull(o.getReportSummary());
		assertNotNull(o.getCommittee());

	}

	private void validateVmeObject(Object object) {
		Vme o = (Vme) object;
		assertNotNull(o.getRfmo().getId());
		assertTrue(o.getSpecificMeasuresList().size() > 0);
		assertNotNull(o.getValidityPeriod().getBeginYear());
		assertNotNull(o.getValidityPeriod().getEndYear());
		assertTrue(o.getId() <= 212);

	}

	private void validateRfmoObject(Object object) {
		Rfmo o = (Rfmo) object;
		assertTrue(o.getFishingHistoryList().size() > 0);
		assertNotNull(o.getGeneralMeasures());
		assertTrue(o.getListOfManagedVmes().size() > 0);
		if (o.getId() == 1 || o.getId() == 3) {
			assertTrue(o.getInformationSourceList().size() > 0);
		}
	}

	private void validateGeneralMeasuresObject(Object object) {
		GeneralMeasures o = (GeneralMeasures) object;

		// this one was not defined in the access DB.
		// assertNotNull(o.getPrimairlyConcernedVme());

		assertNotNull(o.getRfmo());
		assertNotNull(o.getValidityPeriod());
		assertTrue(o.getInformationSourceList().size() > 0);

	}

	private void validateSpecificMeasuresObject(Object object) {
		SpecificMeasures o = (SpecificMeasures) object;
		assertTrue(o.getVmeList().size() > 0);

	}

}
