package org.vme.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.vme.batch.sync2.SyncBatch2;
import org.fao.fi.vme.domain.model.GeoRef;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.ValidityPeriodMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;
import org.vme.dao.sources.figis.FigisDao;
import org.vme.dao.sources.vme.VmeDao;
import org.vme.dao.test.ReferenceDaoMockImpl;
import org.vme.service.dto.SpecificMeasureDto;
import org.vme.service.dto.VmeSmResponse;

/**
 * This round test does
 * 
 * 
 * 
 * @author Erik van Ingen
 *
 */

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceDaoMockImpl.class, VmePersistenceUnitConfiguration.class,
		FigisPersistenceUnitConfiguration.class, SearchService.class })
@AdditionalClasses({ FigisDataBaseProducer.class, VmeDataBaseProducerApplicationScope.class })
public class SearchServiceRoundTest {

	@Inject
	private VmeDao vdao;

	@Inject
	private FigisDao fdao;

	@Inject
	SyncBatch2 b;

	@Inject
	GetInfoService getInfoService;

	@Test
	public void round() {
		Vme vme = prepareTestData();
		saveVme(vme);
		vdao.persist(vme);
		b.syncFigisWithVme(vme);

		VmeSmResponse response = getInfoService.vmeIdentifier2SpecificMeasure(vme.getInventoryIdentifier(), 0);
		List<SpecificMeasureDto> responseList = response.getResponseList();
		assertTrue(responseList.size() > 0);

		VmeObservationDomain vod = fdao.findVod(vme.getId());

		List<ObservationDomain> list = vod.getObservationDomainList();
		for (ObservationDomain observationDomain : list) {
			System.out.println(observationDomain.getReportingYear());
		}

		assertTrue(vod.getObservationDomainList().get(0).getReportingYear().equals("2000"));
		assertTrue(vod.getObservationDomainList().get(1).getReportingYear().equals("2002"));

		for (SpecificMeasureDto specificMeasureDto : responseList) {
			assertNotNull(specificMeasureDto.getFactsheetURL());
		}

	}

	private void saveVme(Vme vme) {
		Rfmo r = new Rfmo();
		r.setId("CCAMLR");
		vdao.persist(r);

		vme.setRfmo(r);
		vdao.persist(vme);

	}

	private Vme prepareTestData() {

		Vme vme = new Vme();

		vme.setScope(10l);

		SpecificMeasure s2000 = new SpecificMeasure();
		s2000.setYear(2000);
		s2000.setVme(vme);
		s2000.setValidityPeriod(ValidityPeriodMock.create(2001, 2001));

		SpecificMeasure s2002 = new SpecificMeasure();
		s2002.setYear(2002);
		s2002.setVme(vme);
		s2002.setValidityPeriod(ValidityPeriodMock.create(2003, 2003));

		List<SpecificMeasure> smList = new ArrayList<SpecificMeasure>();
		smList.add(s2000);
		smList.add(s2002);

		GeoRef gr = new GeoRef();
		gr.setYear(2001);
		gr.setVme(vme);
		gr.setGeographicFeatureID("TestGeoRefGeographicFeatureID");

		List<GeoRef> geoRefList = new ArrayList<GeoRef>();
		geoRefList.add(gr);

		vme.setSpecificMeasureList(smList);
		vme.setGeoRefList(geoRefList);
		vme.setInventoryIdentifier("VME-X");

		vme.setValidityPeriod(ValidityPeriodMock.create(2001, 2002));

		return vme;
	}
}
