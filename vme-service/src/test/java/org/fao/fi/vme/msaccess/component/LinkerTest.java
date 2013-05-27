package org.fao.fi.vme.msaccess.component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.fao.fi.vme.dao.config.EntityManagerFactoryProducer;
import org.fao.fi.vme.dao.config.EntityManagerProducer;
import org.fao.fi.vme.domain.GeneralMeasures;
import org.fao.fi.vme.domain.Meeting;
import org.fao.fi.vme.domain.Rfmo;
import org.fao.fi.vme.domain.SpecificMeasures;
import org.fao.fi.vme.domain.Vme;
import org.fao.fi.vme.msaccess.component.Linker;
import org.fao.fi.vme.msaccess.component.MsAcces2DomainMapper;
import org.fao.fi.vme.msaccess.component.VmeReader;
import org.fao.fi.vme.msaccess.component.VmeWriter;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.model.Table;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ EntityManagerFactoryProducer.class, EntityManagerProducer.class })
public class LinkerTest {

	@Inject
	private VmeWriter writer;

	private final MsAcces2DomainMapper m = new MsAcces2DomainMapper();
	private final Linker linker = new Linker();

	private final VmeReader reader = new VmeReader();

	@Test
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
				if (object instanceof Meeting) {
					validateMeetingObject(object);
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
		ObjectCollection vmeCollection = null;
		ObjectCollection specificMeasuresCollection = null;
		for (ObjectCollection objectCollection : objectCollectionList) {
			if (objectCollection.getClazz().equals(Vme.class)) {
				vmeCollection = objectCollection;
			}
			if (objectCollection.getClazz().equals(SpecificMeasures.class)) {
				specificMeasuresCollection = objectCollection;
			}
		}
		List<Object> vmeList = vmeCollection.getObjectList();
		List<Object> smList = specificMeasuresCollection.getObjectList();
		Set<String> vmeIds = new HashSet<String>();
		for (Object object : smList) {
			SpecificMeasures sm = (SpecificMeasures) object;
			String combi = sm.getVme().getId() + " " + sm.getId();
			System.out.println(combi);
			assertFalse(vmeIds.contains(combi));
			assertTrue(vmeIds.add(combi));
		}

	}

	private void validateMeetingObject(Object object) {
		Meeting o = (Meeting) object;
		assertNotNull(o.getMeetingDocument());
		assertNotNull(o.getMeetingDocument().getUrl());
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
		assertTrue(o.getFishingActivityList().size() > 0);
		assertTrue(o.getGeneralMeasuresList().size() > 0);
		assertTrue(o.getManagedVmeList().size() > 0);
		if (o.getId() == 1 || o.getId() == 3) {
			assertTrue(o.getMeetingList().size() > 0);
		}
	}

	private void validateGeneralMeasuresObject(Object object) {
		GeneralMeasures o = (GeneralMeasures) object;

		// this one was not defined in the access DB.
		// assertNotNull(o.getPrimairlyConcernedVme());

		assertNotNull(o.getRfmo());
		assertNotNull(o.getValidityPeriod());
		assertNotNull(o.getLinkCemSource());

	}

	private void validateSpecificMeasuresObject(Object object) {
		SpecificMeasures o = (SpecificMeasures) object;
		assertNotNull(o.getVme());
		assertTrue(o.getVme().getSpecificMeasuresList().size() > 0);

	}

}
