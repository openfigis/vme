package org.fao.fi.figis.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservation;
import org.fao.fi.figis.domain.rule.DomainRule;
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
		DomainRule r = new DomainRule();
		r.composeId(xml);
		dao.persist(xml);
		assertNotNull(dao.find(ObservationXml.class, xml.getId()));

	}

	@Test
	public void testObservation() {

		ObservationXml xml = createObservationXml();
		Observation o = createObservation();
		dao.persist(o);
		assertNotNull(dao.find(Observation.class, o.getId()));

		// xml.setObservation(o);
		// DomainRule r = new DomainRule();
		// r.composeId(xml);
		// dao.persist(xml);
		// assertNotNull(dao.find(ObservationXml.class, xml.getId()));
		//
		// dao.merge(o);
		//
		// Observation found = (Observation) dao.find(Observation.class, o.getId());
		// assertNotNull(found);
		// assertNotNull(found.getObservationsPerLanguage().get(0).getId());
	}

	@Test
	public void testObservationWithXml() {
		ObservationXml xml = createObservationXml();
	}

	private Observation createObservation() {
		Observation o = new Observation();
		o.setOrder(VmeRefSync.ORDER);
		o.setCollection(VmeRefSync.COLLECTION);
		return o;
	}

	private Observation createObservationWithXml() {
		Observation o = createObservation();
		ObservationXml xml = createObservationXml();
		List<ObservationXml> observationsPerLanguage = new ArrayList<ObservationXml>();
		observationsPerLanguage.add(xml);
		o.setObservationsPerLanguage(observationsPerLanguage);
		return o;
	}

	@Test
	public void VmeObservation() {
		VmeObservation vo = createVmeObservation();
		RefVme r = createRefVme();
		dao.syncRefVme(r);
		dao.syncVmeObservation(vo);
	}

	private ObservationXml createObservationXml() {
		ObservationXml xml = new ObservationXml();
		xml.setLanguage(2);
		xml.setStatus(0);
		xml.setLastEditDate(new Date(456456l));
		xml.setCreationDate(new Date(7897890l));
		return xml;
	}

	@Test
	public void testPersistVmeObservation() {

		delegateCheckOnNumberOfObjectsInModel(0);

		// create xml for a language
		ObservationXml xml = createObservationXml();

		// create list of language xmls
		List<ObservationXml> observationsPerLanguage = new ArrayList<ObservationXml>();
		observationsPerLanguage.add(xml);

		// add this list to the observation
		Observation o = new Observation();
		o.setObservationsPerLanguage(observationsPerLanguage);

		// bidirectional stuff.
		xml.setObservation(o);

		RefVme r = createRefVme();

		Long id = new Long(4354);

		r.setId(id);
		RefVme found = (RefVme) dao.find(RefVme.class, r);
		assertNull(found);
		dao.persist(o);

		// formalise it as a vme observation

		VmeObservation vo = createVmeObservation();

		vo.setRefVme(r);
		vo.setObservation(o);
		dao.persist(vo);

		delegateCheckOnNumberOfObjectsInModel(1);
	}

	private RefVme createRefVme() {

		Long id = new Long(4354);
		RefVme r = new RefVme();
		r.setId(id);
		return r;
	}

	private VmeObservation createVmeObservation() {
		VmeObservation vo = new VmeObservation();
		return vo;
	}

	private void delegateCheckOnNumberOfObjectsInModel(int i) {
		assertEquals(i, dao.loadObjects(ObservationXml.class).size());
		assertEquals(i, dao.loadObjects(Observation.class).size());
		assertEquals(i, dao.loadObjects(VmeObservation.class).size());

	}

	/**
	 * return null when no object is found.
	 */

	@Test
	public void testLoadRefVmeNull() {
		RefVme found = (RefVme) dao.find(RefVme.class, 4561);
		assertNull(found);
	}
}
