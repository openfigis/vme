package org.vme.dao.sources.vme;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityTransaction;

import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.VmeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.vme.VmeDataBaseProducerApplicationScope;
import org.vme.dao.config.vme.VmePersistenceUnitConfiguration;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmePersistenceUnitConfiguration.class })
@AdditionalClasses({ VmeDataBaseProducerApplicationScope.class })
public class AddSpecificMeasureInformationSourceTest {

	@Inject
	private VmeDao dao;

	@Test
	public void testAddSpecificMeasureInformationSource() throws Throwable {
		Rfmo r = makeTheRfmo();
		InformationSource i = new InformationSource();
		i.setRfmo(r);
		List<InformationSource> informationSourceList = new ArrayList<InformationSource>();
		informationSourceList.add(i);
		r.setInformationSourceList(informationSourceList);
		Vme vme = VmeMock.create();

		dao.persist(r);
		dao.persist(vme);
		dao.persist(i);

		InformationSource iDto = new InformationSource();
		iDto.setId(i.getId());
		iDto.setRfmo(makeTheRfmo());

		System.out.println("Information source id = " + i.getId());

		SpecificMeasure s = new SpecificMeasure();
		s.setInformationSource(iDto);

		Vme vmeDto = VmeMock.create();

		vmeDto.setId(vme.getId());
		vmeDto.setSpecificMeasureList(new ArrayList<SpecificMeasure>());
		vmeDto.getSpecificMeasureList().add(s);
		vmeDto.setRfmo(makeTheRfmo());

		EntityTransaction et = dao.getEm().getTransaction();
		et.begin();
		dao.update(vmeDto);
		et.commit();

	}

	private Rfmo makeTheRfmo() {
		Rfmo r = new Rfmo();
		r.setId("peter");
		return r;
	}

}
