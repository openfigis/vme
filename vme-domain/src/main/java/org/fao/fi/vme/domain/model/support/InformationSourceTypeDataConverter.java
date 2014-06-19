/**
 * (c) 2014 FAO / UN (project: vme-domain)
 */
package org.fao.fi.vme.domain.model.support;

import org.fao.fi.vme.domain.model.reference.InformationSourceType;
import org.gcube.application.rsg.support.compiler.bridge.converters.impl.AbstractSerializableConceptDataConverter;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 27 Feb 2014   Fiorellato     Creation.
 *
 * @version 1.0
 * @since 27 Feb 2014
 */
public class InformationSourceTypeDataConverter extends AbstractSerializableConceptDataConverter<InformationSourceType> {
	@Override
	protected InformationSourceType getConceptInstance() {
		return new InformationSourceType();
	}
}
