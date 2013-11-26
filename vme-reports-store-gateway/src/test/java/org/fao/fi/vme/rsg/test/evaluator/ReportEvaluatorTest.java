/**
 * (c) 2013 FAO / UN (project: reports-store-gateway-support-compiler)
 */
package org.fao.fi.vme.rsg.test.evaluator;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.rsg.test.AbstractCompilerDependentTest;
import org.gcube.application.rsg.support.compiler.ReportCompiler;
import org.gcube.application.rsg.support.compiler.annotations.Compiler;
import org.gcube.application.rsg.support.compiler.annotations.Evaluator;
import org.gcube.application.rsg.support.compiler.impl.AnnotationBasedReportCompiler;
import org.gcube.application.rsg.support.evaluator.ReportEvaluator;
import org.gcube.application.rsg.support.evaluator.impl.JEXLReportEvaluator;
import org.gcube.application.rsg.support.model.components.impl.CompiledReport;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.impl.hardcoded.ReferenceHardcodedDao;
import org.vme.test.mock.VmeMocker;

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
public class ReportEvaluatorTest extends AbstractCompilerDependentTest {
	@Inject @Evaluator private ReportEvaluator _reportEvaluator;
	
	@Test
	public void testMocked1() throws Throwable {
		this.doSimpleTest(VmeMocker.getMock1());
	}
	
	@Test
	public void testMocked2() throws Throwable {
		this.doSimpleTest(VmeMocker.getMock2());
	}
	
	@Test
	public void testMocked3() throws Throwable {
		this.doSimpleTest(VmeMocker.getMock3());
	}
	
	private void doSimpleTest(Vme vme) throws Throwable {
		CompiledReport evaluated = this._reportEvaluator.evaluate(this._reportCompiler.compile(Vme.class), vme);
		
		Assert.assertTrue(evaluated.getIsEvaluated());
	}
}
