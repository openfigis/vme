/**
 * (c) 2013 FAO / UN (project: reports-store-gateway-support)
 */
package org.fao.fi.vme.domain.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 13/nov/2013   Fabio     Creation.
 *
 * @version 1.0
 * @since 13/nov/2013
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ReferenceConceptName {
	String value();
}
