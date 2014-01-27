package org.fao.fi.vme.msaccess.component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.msaccess.mapping.RelationVmeGeoRef;
import org.fao.fi.vme.msaccess.model.ObjectCollection;
import org.fao.fi.vme.msaccess.model.Table;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FilesystemMsAccessConnectionProvider.class })
public class LinkerTest {

	@Inject
	private VmeReader reader;

	private final MsAcces2DomainMapper m = new MsAcces2DomainMapper();
	private final Linker linker = new Linker();
	private final MultiLingualStringUtil u = new MultiLingualStringUtil();
	private RelationVmeGeoRef relationVmeGeoRef = new RelationVmeGeoRef();

	@Test
	public void testLink() {

		List<Table> tables = reader.readObjects();

		// convert the table objects to domain objects
		List<ObjectCollection> objectCollectionList = new ArrayList<ObjectCollection>();
		for (Table table : tables) {
			objectCollectionList.add(m.map(table));
		}

		// correct the relation Vme - GeoRef
		relationVmeGeoRef.correct(objectCollectionList);

		// connect/link the domain objects, also using the original information
		// from the table objects
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
				if (object instanceof SpecificMeasure) {
					validateSpecificMeasuresObject(object);
				}
				if (object instanceof GeneralMeasure) {
					validateGeneralMeasuresObject(object);
				}
			}
		}
		// validateRelationVmeSpecificMeasure(objectCollectionList);

	}

	private void validateSpecificMeasuresObject(Object object) {
		SpecificMeasure o = (SpecificMeasure) object;
		assertNotNull(o.getVme());

		if (u.getEnglish(o.getVme().getName()).equals("Northeast Flemish Cap 5")) {
			assertNotNull(o.getInformationSource());
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
		// not all vme's do have a specific measure

		if (u.getEnglish(o.getName()).equals("Fogo Seamounts 1")) {
			assertTrue("VME " + u.getEnglish(o.getName()) + " " + o.getInventoryIdentifier()
					+ " does not have specific measures", o.getSpecificMeasureList().size() > 0);
			assertNotNull(o.getSpecificMeasureList().get(0).getVmeSpecificMeasure());
		}

		assertNotNull(o.getValidityPeriod().getBeginYear());
		assertNotNull(o.getValidityPeriod().getEndYear());

	}

	private void validateRfmoObject(Object object) {
		Rfmo o = (Rfmo) object;
		System.out.println(o.getId());

		List<FisheryAreasHistory> fahList = o.getHasFisheryAreasHistory();
		for (FisheryAreasHistory fisheryAreasHistory : fahList) {
			assertEquals(o, fisheryAreasHistory.getRfmo());
		}
		List<VMEsHistory> vmeHlist = o.getHasVmesHistory();
		for (VMEsHistory vh : vmeHlist) {
			assertEquals(o, vh.getRfmo());
		}

	}

	private void validateGeneralMeasuresObject(Object object) {
		GeneralMeasure o = (GeneralMeasure) object;

		// this one was not defined in the access DB.
		// assertNotNull(o.getPrimairlyConcernedVme());

		assertNotNull(o.getRfmo());
		assertNotNull(o.getValidityPeriod());

		// there is one GM whithout an Link_CEM_Source defined
		// assertTrue(o.getInformationSourceList().size() > 0);

	}

}
