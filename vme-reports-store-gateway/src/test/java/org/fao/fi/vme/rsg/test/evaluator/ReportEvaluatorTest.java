/**
 * (c) 2013 FAO / UN (project: reports-store-gateway-support-compiler)
 */
package org.fao.fi.vme.rsg.test.evaluator;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.rsg.test.AbstractCompilerDependentTest;
import org.gcube.application.rsg.support.compiler.annotations.Evaluator;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.StringDataConverter;
import org.gcube.application.rsg.support.compiler.impl.AnnotationBasedReportCompiler;
import org.gcube.application.rsg.support.compiler.utils.CompiledReportUtils;
import org.gcube.application.rsg.support.evaluator.ReportEvaluator;
import org.gcube.application.rsg.support.evaluator.impl.JEXLReportEvaluator;
import org.gcube.application.rsg.support.model.Bound;
import org.gcube.application.rsg.support.model.components.impl.CompiledReport;
import org.gcube.application.rsg.support.model.components.impl.InputComponent;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
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
@ActivatedAlternatives({ AnnotationBasedReportCompiler.class,
						 JEXLReportEvaluator.class })
@AdditionalClasses({ ReferenceHardcodedDao.class, 
					 StringDataConverter.class,
					 MultiLingualString.class })
public class ReportEvaluatorTest extends AbstractCompilerDependentTest {
	@Inject @Evaluator private ReportEvaluator _reportEvaluator;

	@Test
	public void testMocked1() throws Throwable {
		this.doSimpleTest(VmeMocker.getMock1());
	}
	
	@Test
	public void reverseTestMocked1() throws Throwable {
		Vme original = VmeMocker.getMock1();
		Vme extracted = this.doReversedTest(this.doSimpleTest(original));
		
		CompiledReport originalReport = this._reportCompiler.compile(Vme.class);
		CompiledReport extractedReport = this._reportCompiler.compile(Vme.class);
		
		originalReport = this._reportEvaluator.evaluate(originalReport, original);
		extractedReport = this._reportEvaluator.evaluate(extractedReport, extracted);

		Assert.assertEquals(originalReport, extractedReport);
	}
	
	@Test
	public void testMocked2() throws Throwable {
		this.doSimpleTest(VmeMocker.getMock2());
	}
	
	@Test
	public void testMocked3() throws Throwable {
		this.doSimpleTest(VmeMocker.getMock3());
	}
	
	private CompiledReport doSimpleTest(Vme vme) throws Throwable {
		CompiledReport evaluated = this._reportEvaluator.evaluate(this._reportCompiler.compile(Vme.class), vme);
		
		Assert.assertTrue(evaluated.getIsEvaluated());
		
		return evaluated;
	}
	
	@SuppressWarnings("unchecked")
	private <E extends Object> E doReversedTest(CompiledReport report) throws Throwable {
		Assert.assertTrue(report.getIsEvaluated());
		
		return (E)this._reportEvaluator.extract(report);
	}
	
	@Test
	public void testFindBindingsInReport() throws Throwable {
		CompiledReport template = this._reportCompiler.compile(Vme.class);
		CompiledReport evaluated = this._reportEvaluator.evaluate(template, VmeMocker.getMock1());
		
		Bound found = CompiledReportUtils.find(evaluated, "#.rfmo.informationSourceList[0].publicationYear");
		
		Assert.assertNotNull(found);
		Assert.assertEquals(InputComponent.class, found.getClass());

		found = CompiledReportUtils.find(template, "#.rfmo.informationSourceList[0].publicationYear");
		Assert.assertNull(found);

		found = CompiledReportUtils.find(evaluated, "#.rfmo.informationSourceList[0].publicationYearZ");
		Assert.assertNull(found);
		
		found = CompiledReportUtils.find(evaluated, "#.rfmo.generalMeasureList[1].informationSourceList[0].publicationYear");
		
		Assert.assertNotNull(found);
		Assert.assertEquals(InputComponent.class, found.getClass());
	}
}
