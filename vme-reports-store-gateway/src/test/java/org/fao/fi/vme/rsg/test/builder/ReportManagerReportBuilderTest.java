/**
 * (c) 2013 FAO / UN (project: reports-store-gateway-factory)
 */
package org.fao.fi.vme.rsg.test.builder;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.GeneralMeasure;
import org.fao.fi.vme.domain.model.InformationSource;
import org.fao.fi.vme.domain.model.Rfmo;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.extended.FisheryAreasHistory;
import org.fao.fi.vme.domain.model.extended.VMEsHistory;
import org.fao.fi.vme.rsg.test.AbstractCompilerDependentTest;
import org.gcube.application.reporting.ReportsModeler;
import org.gcube.application.reporting.persistence.PersistenceManager;
import org.gcube.application.rsg.support.builder.ReportBuilder;
import org.gcube.application.rsg.support.builder.annotations.Builder;
import org.gcube.application.rsg.support.builder.impl.ReportManagerReportBuilder;
import org.gcube.application.rsg.support.compiler.annotations.Evaluator;
import org.gcube.application.rsg.support.compiler.impl.AnnotationBasedReportCompiler;
import org.gcube.application.rsg.support.compiler.utils.CompiledReportUtils;
import org.gcube.application.rsg.support.evaluator.ReportEvaluator;
import org.gcube.application.rsg.support.evaluator.impl.JEXLReportEvaluator;
import org.gcube.application.rsg.support.model.components.impl.CompiledReport;
import org.gcube.portlets.d4sreporting.common.shared.Model;
import org.jglue.cdiunit.ActivatedAlternatives;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.vme.service.dao.HardCodedDaoFactory;
import org.vme.service.dao.impl.hardcoded.ReferenceHardcodedDao;
import org.vme.test.mock.VmeMocker;

import com.thoughtworks.xstream.XStream;

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
@ActivatedAlternatives({ HardCodedDaoFactory.class, 
						 AnnotationBasedReportCompiler.class,
						 ReportManagerReportBuilder.class,
						 JEXLReportEvaluator.class })
@AdditionalClasses({ReferenceHardcodedDao.class})
public class ReportManagerReportBuilderTest extends AbstractCompilerDependentTest {
	@Inject @Builder private ReportBuilder<ReportsModeler> _reportBuilder;
	@Inject @Evaluator private ReportEvaluator _reportEvaluator;
	
	@Test
	public void testBuildAndExtractVmeReport() throws Throwable {
		CompiledReport template = this._reportCompiler.compile(Vme.class);
		template = this._reportEvaluator.evaluate(template, VmeMocker.getMock1());
		
		ReportsModeler modeler = this._reportBuilder.buildReport(template, "id:foo", "name:bar", "author:baz", new Date(), new Date(), "lastEditorId:foobaz");

		CompiledReport extracted = this._reportBuilder.extract(template, modeler);
		
		//System.out.println(new XStream().toXML(modeler.getReportInstance()));
		System.out.println(CompiledReportUtils.toXML(extracted));
	}
	
	@Test
	public void testBuildVmeTemplate() throws Throwable {
		CompiledReport template = this._reportCompiler.compile(Vme.class);

		ReportsModeler modeler = this._reportBuilder.buildReport(template, "id:foo", "name:bar", "author:baz", new Date(), new Date(), "lastEditorId:foobaz");

		System.out.println(new XStream().toXML(modeler.getReportInstance()));
	}

	@Test
	public void testSerializeDeserializeVmeTemplate() throws Throwable {
		File tempDir = this.createTempDir();
		File tempFile = new File(tempDir, "template.d4st");

		CompiledReport template = this._reportCompiler.compile(Vme.class);

		ReportsModeler modeler = this._reportBuilder.buildReport(template, "id:foo", "name:bar", "author:baz", new Date(), new Date(), "lastEditorId:foobaz");
		Model model = modeler.getReportInstance();
		
		PersistenceManager.writeModel(modeler.getReportInstance(), tempFile);

		Model nModel = PersistenceManager.readModel(tempFile.getAbsolutePath());
		
		String xModel = new XStream().toXML(model);
		String xNModel = new XStream().toXML(nModel);
		
		System.out.println(xModel);
		
		Assert.assertEquals(xModel, xNModel);
	}
	
	@Test
	public void testBuildSerializeAndStoreVmeReport() throws Throwable {
		this.doTestBuildSerializeStoreReport(VmeMocker.getMock1());
	}
	
	@Test
	public void testBuildSerializeAndStoreVmeTemplate() throws Throwable {
		this.doTestBuildSerializeStoreTemplate(Vme.class);
	}
	
	@Test
	public void testBuildSerializeAndStoreRfmoRefTemplate() throws Throwable {
		this.doTestBuildSerializeStoreTemplate(Rfmo.class);
	}

	@Test
	public void testBuildSerializeAndStoreGeneralMeasureRefTemplate() throws Throwable {
		this.doTestBuildSerializeStoreTemplate(GeneralMeasure.class);
	}

	@Test
	public void testBuildSerializeAndStoreInformationSourceRefTemplate() throws Throwable {
		this.doTestBuildSerializeStoreTemplate(InformationSource.class);
	}

	@Test
	public void testBuildSerializeAndStoreVmesHistoryRefTemplate() throws Throwable {
		this.doTestBuildSerializeStoreTemplate(VMEsHistory.class);
	}

	@Test
	public void testBuildSerializeAndStoreFisheryAreasHistoryRefTemplate() throws Throwable {
		this.doTestBuildSerializeStoreTemplate(FisheryAreasHistory.class);
	}
	
	private void doTestBuildSerializeStoreTemplate(Class<?> reportClass) throws Throwable {
		String name = reportClass.getSimpleName();
		
		File tempFile = new File("./compiled/templates/" + name, name + ".d4st");
		
		CompiledReport template = this._reportCompiler.compile(reportClass);
		
		ReportsModeler modeler = this._reportBuilder.buildReport(template, "id:foo", "name:bar", "author:baz", new Date(), new Date(), "lastEditorId:foobaz");
		Model model = modeler.getReportInstance();
		
		PersistenceManager.writeModel(modeler.getReportInstance(), tempFile);
	
		Model nModel = PersistenceManager.readModel(tempFile.getAbsolutePath());
		
		String xModel = new XStream().toXML(model);
		String xNModel = new XStream().toXML(nModel);
		
		Assert.assertEquals(xModel, xNModel);
	}
	
	private void doTestBuildSerializeStoreReport(Object data) throws Throwable {
		String name = data.getClass().getSimpleName();
		
		File tempFile = new File("./compiled/reports/" + name, name + ".d4st");
		
		CompiledReport template = this._reportCompiler.compile(data.getClass());
		CompiledReport report = this._reportEvaluator.evaluate(template, data);
		
		System.out.println(CompiledReportUtils.toXML(report));
		
		ReportsModeler modeler = this._reportBuilder.buildReport(report, "id:foo", "name:bar", "author:baz", new Date(), new Date(), "lastEditorId:foobaz");
		Model model = modeler.getReportInstance();
		
		PersistenceManager.writeModel(modeler.getReportInstance(), tempFile);
	
		Model nModel = PersistenceManager.readModel(tempFile.getAbsolutePath());
		
		String xModel = new XStream().toXML(model);
		String xNModel = new XStream().toXML(nModel);
		
		Assert.assertEquals(xModel, xNModel);
	}

	private File createTempDir() throws IOException {
		File tempDir = File.createTempFile("d4sTtemp", Long.toString(System.nanoTime()));

		if (!(tempDir.delete())) {
			throw new IOException("Could not delete temp file: " + tempDir.getAbsolutePath());
		}

		if (!(tempDir.mkdir())) {
			throw new IOException("Could not create temp directory: " + tempDir.getAbsolutePath());
		}

		return tempDir;
	}
}