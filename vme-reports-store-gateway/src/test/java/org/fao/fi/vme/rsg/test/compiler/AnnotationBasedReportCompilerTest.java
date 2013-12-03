/**
 * (c) 2013 FAO / UN (project: vme-reports-store-gateway)
 */
package org.fao.fi.vme.rsg.test.compiler;

import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.rsg.test.AbstractCompilerDependentTest;
import org.gcube.application.rsg.support.compiler.impl.AnnotationBasedReportCompiler;
import org.gcube.application.rsg.support.compiler.utils.CompiledReportUtils;
import org.gcube.application.rsg.support.evaluator.impl.JEXLReportEvaluator;
import org.gcube.application.rsg.support.model.Bound;
import org.gcube.application.rsg.support.model.components.impl.CompiledReport;
import org.gcube.application.rsg.support.model.components.impl.InputComponent;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.HardCodedDaoFactory;

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
@ActivatedAlternatives({ AnnotationBasedReportCompiler.class,
						 JEXLReportEvaluator.class,
						 HardCodedDaoFactory.class })
public class AnnotationBasedReportCompilerTest extends AbstractCompilerDependentTest {
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
	
	@Test
	public void testFindBindingsInTemplate() throws Throwable {
		CompiledReport template = this._reportCompiler.compile(Vme.class);
		
		Bound found = CompiledReportUtils.find(template, "#.rfmo.informationSourceList.publicationYear");
		
		Assert.assertNotNull(found);
		Assert.assertEquals(InputComponent.class, found.getClass());
		
		found = CompiledReportUtils.find(template, "#.rfmo.informationSourceList.publicationYearZ");
		Assert.assertNull(found);
	}
	
	@Test
	public void testFindBindingsInReport() throws Throwable {
		CompiledReport template = this._reportCompiler.compile(Vme.class);
		
		Bound found = CompiledReportUtils.find(template, "#.rfmo.informationSourceList.publicationYear");
		
		Assert.assertNotNull(found);
		Assert.assertEquals(InputComponent.class, found.getClass());
		
		found = CompiledReportUtils.find(template, "#.rfmo.informationSourceList.publicationYearZ");
		Assert.assertNull(found);
	}
}