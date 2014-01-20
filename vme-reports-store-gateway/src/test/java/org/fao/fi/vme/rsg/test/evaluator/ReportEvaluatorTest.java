/**
 * (c) 2013 FAO / UN (project: reports-store-gateway-support-compiler)
 */
package org.fao.fi.vme.rsg.test.evaluator;

import static org.gcube.application.rsg.support.model.utils.CompiledReportUtils.LOOSE;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.SpecificMeasure;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.rsg.test.AbstractCompilerDependentTest;
import org.gcube.application.rsg.support.compiler.annotations.Evaluator;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.AbstractDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.DateDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.DoubleDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.FloatDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.IntegerDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.LongDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.StringDataConverter;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.URLDataConverter;
import org.gcube.application.rsg.support.compiler.impl.AnnotationBasedReportCompiler;
import org.gcube.application.rsg.support.evaluator.ReportEvaluator;
import org.gcube.application.rsg.support.evaluator.impl.JEXLReportEvaluator;
import org.gcube.application.rsg.support.model.Bound;
import org.gcube.application.rsg.support.model.components.impl.CompiledReport;
import org.gcube.application.rsg.support.model.components.impl.InputComponent;
import org.gcube.application.rsg.support.model.utils.CompiledReportUtils;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.HardCodedDaoFactory;
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
						 JEXLReportEvaluator.class,
						 HardCodedDaoFactory.class })
@AdditionalClasses({ AbstractDataConverter.class,
					 DateDataConverter.class,
					 DoubleDataConverter.class,
					 FloatDataConverter.class,
					 IntegerDataConverter.class,
					 LongDataConverter.class,
					 StringDataConverter.class,
					 URLDataConverter.class })
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
	
	@Test
	public void testCleanupEmptyList() throws Throwable {
		Vme mocked = VmeMocker.getMock3();
		
		mocked.getProfileList().clear();
		
		CompiledReport report = this.doSimpleTest(mocked);
		
		Vme extracted = this._reportEvaluator.extract(report);
		
		Assert.assertTrue(extracted.getProfileList() == null);
	}
	
	@Test
	public void testCleanupListWithNullElement() throws Throwable {
		Vme mocked = VmeMocker.getMock3();
		
		mocked.getProfileList().set(0, null);
		
		CompiledReport report = this.doSimpleTest(mocked);
		
		Vme extracted = this._reportEvaluator.extract(report);
		
		Assert.assertTrue(extracted.getProfileList() == null);
	}

	@Test
	public void testCleanupListWithEmptyElement() throws Throwable {
		Vme mocked = VmeMocker.getMock3();
		
		Profile current = mocked.getProfileList().get(0);
		current.setDescriptionBiological(null);
		current.setDescriptionImpact(null);
		current.setDescriptionPhisical(null);
		current.setGeoform(null);
		current.setYear(null);
		
		CompiledReport report = this.doSimpleTest(mocked);
		
		Vme extracted = this._reportEvaluator.extract(report);
		
		Assert.assertTrue(extracted.getProfileList() == null);
	}
	
	@Test
	public void testCleanupListWithEmptyElement2() throws Throwable {
		Vme mocked = VmeMocker.getMock3();
				
		SpecificMeasure current = mocked.getSpecificMeasureList().get(0);
		current.setVmeSpecificMeasure(null);
		current.setYear(null);
		current.getValidityPeriod().setBeginYear(null);
		current.getValidityPeriod().setEndYear(null);
		current.getInformationSource().setId(null);
		current.getInformationSource().setCitation(null);
		current.getInformationSource().setCommittee(null);
		current.getInformationSource().setMeetingEndDate(null);
		current.getInformationSource().setMeetingStartDate(null);
		current.getInformationSource().setPublicationYear(null);
		current.getInformationSource().setReportSummary(null);
		current.getInformationSource().setRfmo(null);
		current.getInformationSource().setUrl(null);
		
		CompiledReport report = this.doSimpleTest(mocked);
		
		Vme extracted = this._reportEvaluator.extract(report);
		
		Assert.assertTrue(extracted.getSpecificMeasureList().size() == 2);
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
		
		Bound found = CompiledReportUtils.find(evaluated, "#.specificMeasureList[0].informationSource.publicationYear", LOOSE);
		
		Assert.assertNotNull(found);
		Assert.assertEquals(InputComponent.class, found.getClass());

		found = CompiledReportUtils.find(template, "#.specificMeasureList[0].informationSource.publicationYear", LOOSE);
		Assert.assertNull(found);

		found = CompiledReportUtils.find(evaluated, "#.specificMeasureList[0].informationSource.publicationYearZ", LOOSE);
		Assert.assertNull(found);
		
		found = CompiledReportUtils.find(evaluated, "#.specificMeasureList[1].informationSource.publicationYear", LOOSE);
		
		Assert.assertNotNull(found);
		Assert.assertEquals(InputComponent.class, found.getClass());
	}
}
