/**
 * (c) 2013 FAO / UN (project: vme-reports-store-gateway)
 */
package org.fao.fi.vme.rsg.compiler.test;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Vme;
import org.gcube.application.rsg.support.compiler.ReportCompiler;
import org.gcube.application.rsg.support.compiler.annotations.Compiler;
import org.gcube.application.rsg.support.compiler.impl.AnnotationBasedReportCompiler;
import org.gcube.application.rsg.support.compiler.utils.CompiledReportUtils;
import org.gcube.application.rsg.support.evaluator.impl.JEXLReportEvaluator;
import org.gcube.application.rsg.support.model.components.impl.CompiledReport;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.impl.hardcoded.ReferenceHardcodedDao;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 24/nov/2013   Fabio     Creation.
 *
 * @version 1.0
 * @since 24/nov/2013
 */
@RunWith(CdiRunner.class)
@ActivatedAlternatives({ ReferenceHardcodedDao.class, 
						 AnnotationBasedReportCompiler.class,
						 JEXLReportEvaluator.class })
public class AnnotationBasedReportCompilerTest {
	@Inject @Compiler private ReportCompiler _reportCompiler;
	
	@Test
	public void testCompileReport() throws Throwable {
		CompiledReport template = this._reportCompiler.compile(Vme.class);

		String xml = CompiledReportUtils.toXML(template); 
		
		CompiledReport nTemplate = (CompiledReport)CompiledReportUtils.fromXML(xml);
		
		String nXml = CompiledReportUtils.toXML(nTemplate);
		
		Assert.assertEquals(xml, nXml);
		Assert.assertEquals(template, nTemplate);
		
		System.out.println(xml);
	}
}
