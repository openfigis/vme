/**
 * (c) 2013 FAO / UN (project: vme-reports-store-gateway)
 */
package org.fao.fi.vme.rsg.test;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.gcube.application.rsg.support.compiler.ReportCompiler;
import org.gcube.application.rsg.support.compiler.annotations.Compiler;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 25 Nov 2013   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 25 Nov 2013
 */
abstract public class AbstractCompilerDependentTest {
	@Inject @Compiler protected ReportCompiler _reportCompiler;

	@PostConstruct
	public void initializeCompiler() {
		this._reportCompiler.registerPrimitiveType(MultiLingualString.class);
	}
}
