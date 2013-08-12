package org.fao.fi.figis.dao;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationDomain;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.VmeObservationPk;
import org.fao.fi.figis.domain.rule.DomainRule4ObservationXmlId;
import org.fao.fi.figis.domain.rule.Figis;
import org.fao.fi.figis.domain.test.ObservationXmlMock;
import org.fao.fi.figis.domain.test.RefVmeMock;
import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.fao.fi.vme.domain.test.VmeMock;
import org.fao.fi.vme.test.FigisDaoTestLogic;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class })
public class FigisDaoIntegrationTest extends FigisDaoTestLogic {

	@Inject
	FigisDao figisDao;

	@Before
	public void testBefore() {
		clean();
	}

	@After
	public void testAfter() {
		clean();
	}

	void clean() {
		VmeObservationDomain vod = createVmeObservationDomain();
		vod.setRefVme(RefVmeMock.create());
		add1Observation2Vod(vod);
		List<ObservationDomain> oList = vod.getObservationDomainList();
		for (ObservationDomain od : oList) {
			// find VmeObservation
			VmeObservation vo = figisDao.findVmeObservationByVme(vod.getRefVme().getId(), od.getReportingYear());
			if (vo != null) {
				Observation o = (Observation) figisDao.find(Observation.class, vo.getId().getObservationId());
				DomainRule4ObservationXmlId rule = new DomainRule4ObservationXmlId();
				ObservationXml xml = ObservationXmlMock.create();
				xml.setObservation(o);
				rule.composeId(xml);
				ObservationXml xmlFound = (ObservationXml) figisDao.find(ObservationXml.class, xml.getId());
				if (xmlFound != null) {
					figisDao.remove(xmlFound);
					figisDao.remove(vo);
					figisDao.remove(o);
				}
			}
		}
	}

	/**
	 * TODO Bizarre problem. When it finds a RefVme, it will just block and the program never stops.
	 * 
	 * One hour later. Bizar, it looks like not using this anymore was the solution:
	 * dao.loadObjects(ObservationXml.class).size()
	 * 
	 */
	@Test
	public void testDeleteRefVme() {
		RefVme r = (RefVme) figisDao.find(RefVme.class, VmeMock.VME_ID);
		if (r != null) {
			figisDao.getEm().refresh(r);
			figisDao.remove(r);
		}
	}

	@Test
	public void testPersistVmeObservation2() {

		VmeObservationPk id = new VmeObservationPk();
		id.setObservationId(10000l);
		id.setReportingYear("1010");
		id.setVmeId(5050l);

		figisDao.find(VmeObservation.class, id);
		if (figisDao.find(VmeObservation.class, id) != null) {
			figisDao.remove(figisDao.find(VmeObservation.class, id));
		}
		VmeObservation vo = new VmeObservation();

		// TODO
		// vo.setObservationId(id.longValue());
		// vo.setReportingYear("2013");
		// vo.setVmeId(10l);
		// dao.persist(vo);
		// figisDao.remove(figisDao.find(VmeObservation.class, vo.getObservationId()));
	}

	@Test
	public void testPersistObservation() {
		Observation observation = new Observation();
		observation.setOrder(Figis.ORDER);
		observation.setCollection(Figis.COLLECTION);
		observation.setPrimary(Figis.PRIMARY);
		observation.setReference(Figis.REFERENCE);

		figisDao.persist(observation);
		figisDao.remove(observation);
	}

}
