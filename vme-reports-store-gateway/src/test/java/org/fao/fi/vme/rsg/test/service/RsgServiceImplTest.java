package org.fao.fi.vme.rsg.test.service;

import org.fao.fi.vme.rsg.service.RsgServiceImplVme;
import org.gcube.application.rsg.support.compiler.impl.AnnotationBasedReportCompiler;
import org.gcube.application.rsg.support.evaluator.impl.JEXLReportEvaluator;
import org.gcube.application.rsg.test.RsgAbstractServiceTest;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.runner.RunWith;
import org.vme.service.dao.config.vme.VmeDataBaseProducer;
import org.vme.service.dao.impl.hardcoded.ReferenceHardcodedDao;

@RunWith(CdiRunner.class)
@AdditionalClasses({ RsgServiceImplVme.class })
@ActivatedAlternatives({ AnnotationBasedReportCompiler.class, 
						 JEXLReportEvaluator.class,
						 ReferenceHardcodedDao.class, 
						 VmeDataBaseProducer.class })
public class RsgServiceImplTest extends RsgAbstractServiceTest {
}