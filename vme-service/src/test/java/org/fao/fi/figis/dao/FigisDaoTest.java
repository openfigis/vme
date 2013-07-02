package org.fao.fi.figis.dao;

import java.sql.Date;
import java.util.ArrayList;

import javax.inject.Inject;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.rule.DomainRule4ObservationXmlId;
import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.fao.fi.vme.figis.component.VmeRefSync;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class })
public class FigisDaoTest {

	@Inject
	private FigisDao dao;

	@Test
	public void testsyncRefVme() {
		RefVme r = createRefVme();
		assertNull(dao.find(RefVme.class, r.getId()));
		dao.syncRefVme(r);
		assertNotNull(dao.find(RefVme.class, r.getId()));
		dao.syncRefVme(r);
		assertNotNull(dao.find(RefVme.class, r.getId()));

	}

	@Test
	public void testLoadRefVmes() {
		int number = 50;
		for (int i = 0; i < number; i++) {
			RefVme o = new RefVme();

			o.setId(new Long(i));
			dao.persist(o);
		}
		assertEquals(number, dao.loadRefVmes().size());

	}

	@Test
	public void testLoadRefVme() {
		Long id = new Long(4354);
		RefVme o = new RefVme();
		o.setId(id);
		dao.persist(o);

		RefVme found = (RefVme) dao.find(RefVme.class, o.getId());
		assertNotNull(found);
		assertEquals(id, found.getId());

	}

	@Test
	public void testObservationXml() {
		ObservationXml xml = createObservationXml();
		Observation o = createObservation();
		dao.persist(o);
		xml.setObservation(o);
		DomainRule4ObservationXmlId r = new DomainRule4ObservationXmlId();
		r.composeId(xml);
		dao.persist(xml);
		assertNotNull(dao.find(ObservationXml.class, xml.getId()));

	}

	@Test
	public void testObservation() {
		Observation o = createObservation();
		dao.persist(o);
		assertNotNull(dao.find(Observation.class, o.getId()));
	}

	@Test
	public void testPersistVmeObservation() {
		delegateCheckOnNumberOfObjectsInModel(0);

		// pre-register RefVme
		RefVme r = createRefVme();
		dao.syncRefVme(r);

		// save the related factsheet
		VmeObservationDomain vod = createVmeObservationDomain();
		vod.setRefVme(r);
		dao.persistVmeObservationDomain(vod);

		delegateCheckOnNumberOfObjectsInModel(1);
	}

	/**
	 * return null when no object is found.
	 */
	@Test
	public void testLoadRefVmeNull() {
		RefVme found = (RefVme) dao.find(RefVme.class, 4561l);
		assertNull(found);
	}

	private RefVme createRefVme() {

		Long id = new Long(4354);
		RefVme r = new RefVme();
		r.setId(id);
		return r;
	}

	private VmeObservationDomain createVmeObservationDomain() {
		VmeObservationDomain vo = new VmeObservationDomain();
		vo.setReportingYear("2013");
		vo.setObservationList(new ArrayList<Observation>());
		Observation o = createObservation();
		ObservationXml xml = createObservationXml();
		o.setObservationsPerLanguage(new ArrayList<ObservationXml>());
		o.getObservationsPerLanguage().add(xml);
		vo.getObservationList().add(o);
		return vo;
	}

	private void delegateCheckOnNumberOfObjectsInModel(int i) {
		assertEquals(i, dao.loadObjects(ObservationXml.class).size());
		assertEquals(i, dao.loadObjects(Observation.class).size());
		assertEquals(i, dao.loadObjects(VmeObservation.class).size());
	}

	private ObservationXml createObservationXml() {
		ObservationXml xml = new ObservationXml();
		xml.setLanguage(2);
		xml.setStatus(0);
		xml.setLastEditDate(new Date(456456l));
		xml.setCreationDate(new Date(7897890l));
		return xml;
	}

	private Observation createObservation() {
		Observation o = new Observation();
		o.setOrder(VmeRefSync.ORDER);
		o.setCollection(VmeRefSync.COLLECTION);
		return o;
	}

}
