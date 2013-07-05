package org.fao.fi.figis.dao;

import org.fao.fi.figis.domain.Observation;
import org.fao.fi.figis.domain.ObservationXml;
import org.fao.fi.figis.domain.RefVme;
import org.fao.fi.figis.domain.VmeObservationDomain;
import org.fao.fi.figis.domain.rule.DomainRule4ObservationXmlId;
import org.fao.fi.vme.dao.config.FigisDataBaseProducer;
import org.fao.fi.vme.test.FigisDaoTestLogic;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ FigisDataBaseProducer.class })
public class FigisDaoTest extends FigisDaoTestLogic {

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

		assertEquals(number, dao.count(RefVme.class).intValue());

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

	/**
	 * return null when no object is found.
	 */
	@Test
	public void testLoadRefVmeNull() {
		RefVme found = (RefVme) dao.find(RefVme.class, 4561l);
		assertNull(found);
	}

	/**
	 * to be promoted to the parent class.
	 */
	@Test
	public void testFindVmeObservationDomain() {
		RefVme r = registerRefVme();

		VmeObservationDomain vod = createVmeObservationDomain();
		vod.setRefVme(r);

		dao.persistVmeObservationDomain(vod);

		VmeObservationDomain found = dao.findVmeObservationDomain(vod.getObservationList().get(0).getId());
		delegateTest(vod, found);

	}

	@Test
	public void testFindVmeObservationDomainByVme() {
		RefVme r = registerRefVme();
		VmeObservationDomain vod = createVmeObservationDomain();
		vod.setRefVme(r);
		dao.persistVmeObservationDomain(vod);
		VmeObservationDomain found = dao.findVmeObservationDomainByVme(r.getId(), vod.getReportingYear());
		delegateTest(vod, found);
	}

	@Test
	public void testFindVmeObservationDomainByVmeNull() {
		VmeObservationDomain found = dao.findVmeObservationDomainByVme(3000l, "3000");
		assertNull(found);
	}

	private void delegateTest(VmeObservationDomain vod, VmeObservationDomain found) {
		assertEquals(vod.getRefVme(), found.getRefVme());
		assertEquals(vod.getObservationList().get(0), found.getObservationList().get(0));
		assertEquals(vod.getReportingYear(), found.getReportingYear());
	}

}
