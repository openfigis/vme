package org.vme.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.InformationSourceType;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.test.InformationSourceMock;
import org.fao.fi.vme.domain.test.VmeMock;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.dao.config.figis.FigisDataBaseProducer;
import org.vme.dao.config.figis.FigisTestPersistenceUnitConfiguration;
import org.vme.dao.config.vme.VmeDataBaseProducer;
import org.vme.dao.config.vme.VmeTestPersistenceUnitConfiguration;
import org.vme.dao.sources.vme.VmeDao;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ VmeTestPersistenceUnitConfiguration.class,
		VmeDataBaseProducer.class, FigisDataBaseProducer.class,
		FigisTestPersistenceUnitConfiguration.class })
public class CsvServiceTest {

	@Inject
	CsvService csvService;

	@Inject
	VmeDao vDao;

	@Test
	public void testCreateCsvFile() throws Exception {
		InformationSourceType defaultIST = InformationSourceMock
				.createInformationSourceType();

		vDao.persist(defaultIST);

		Vme vme = VmeMock.generateVme(2);
		vDao.saveVme(vme);

		List<String[]> csv = csvService.stringBuilderFromCollection(vDao
				.loadVmes());

		for (String[] strings : csv) {
			for (String string : strings) {
				System.out.print(string);
				System.out.print(",");
			}
			System.out.println();
		}

	}

}
