package org.fao.fi.vme.rsg.test.service;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.msaccess.component.EmbeddedMsAccessConnectionProvider;
import org.fao.fi.vme.rsg.service.RsgServiceImplVme;
import org.gcube.application.rsg.support.builder.impl.ReportManagerReportBuilder;
import org.gcube.application.rsg.support.compiler.impl.AnnotationBasedReportCompiler;
import org.gcube.application.rsg.support.evaluator.impl.JEXLReportEvaluator;
import org.gcube.application.rsg.test.RsgAbstractServiceTest;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.runner.RunWith;
import org.vme.service.dao.HardCodedDaoFactory;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;

@RunWith(CdiRunner.class)
@ActivatedAlternatives({ AnnotationBasedReportCompiler.class, 
						 JEXLReportEvaluator.class,
						 ReportManagerReportBuilder.class,
						 VmeDataBaseProducer.class,
						 HardCodedDaoFactory.class,
						 RsgServiceImplVme.class,
						 EmbeddedMsAccessConnectionProvider.class })
@AdditionalClasses({ Vme.class, Rfmo.class, GeneralMeasure.class, InformationSource.class, FisheryAreasHistory.class, VMEsHistory.class })
public class RsgServiceImplTest extends RsgAbstractServiceTest {
}