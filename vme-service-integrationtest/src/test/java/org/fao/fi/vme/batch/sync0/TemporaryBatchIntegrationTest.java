package org.fao.fi.vme.batch.sync0;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.vme.batch.sync0.TemporaryBatch;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.fao.fi.vme.msaccess.component.FilesystemMsAccessConnectionProvider;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeDataBaseProducer.class, FigisDataBaseProducer.class,
		FilesystemMsAccessConnectionProvider.class })
public class TemporaryBatchIntegrationTest {

	@Inject
	TemporaryBatch temporaryBatch;

	@Inject
	VmeDao vmeDao;

	@Inject
	FigisDao figisDao;

	MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Test
	public void testRun() {
		temporaryBatch.run();

		// assertEquals(98, vmeDao.count(Vme.class).intValue());
		// assertEquals(197, vmeDao.count(GeoRef.class).intValue());
		// assertEquals(208, figisDao.count(VmeObservation.class).intValue(),
		// 1);
		System.out.println("vme=" + vmeDao.count(Vme.class) + " GeoRef=" + vmeDao.count(GeoRef.class) + " Profile="
				+ vmeDao.count(Profile.class) + " Observation=" + figisDao.count(Observation.class));

		List<Profile> l = vmeDao.selectFrom(vmeDao.getEm(), Profile.class);

		for (Profile p : l) {
			System.out.println("year=" + p.getYear());
			System.out.println("getDescriptionBiological=" + u.getEnglish(p.getDescriptionBiological()));
			System.out.println("getDescriptionImpact=" + u.getEnglish(p.getDescriptionImpact()));
			System.out.println("getDescriptionPhisical=" + u.getEnglish(p.getDescriptionPhisical()));
			System.out.println("getGeoform=" + u.getEnglish(p.getGeoform()));
		}

		List<ObservationXml> x = figisDao.selectFrom(figisDao.getEm(), ObservationXml.class);
		for (ObservationXml xml : x) {
			if (xml.getXml().contains("<fi:ReportingYear>2012</fi:ReportingYear>")) {
				System.out.println(xml.getXml());
			}
		}

		/*
		 * ID RFB_ID VME_ID VME_Inventory_Identifier VME_Feature_ID
		 * VME_Area_Type Year_ID VME_Validity_Start VME_Validity_End VME_Geoform
		 * VME_GeogArea1 VME_GeogArea2 VME_GeogAreaFAO VME_Coord
		 * VME_Description_Physical VME_Description_Biology
		 * VME_Description_Impact 241 SEAFO Schmidt-Ott Seamount SEAFO_164
		 * VME_SEAFO_164_2011 VME 2010 2011 9999 Southeast Atlantic
		 * 
		 * This record has 9999 as endvalue. This would mean that in 2012 there
		 * are 2 years to consider. In this case there is additional data for
		 * the Fisheries history. Therefore it will generate more than 1
		 * observation.
		 */
		// TODO, why is there a difference of 1 between Oracle and H2?

	}
}
