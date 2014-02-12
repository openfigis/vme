/**
 * (c) 2013 FAO / UN (project: vme-dao)
 */
package org.vme.dao;

import java.util.Collection;
import java.util.Map;

import javax.persistence.EntityManager;

/**
 * Place your class / interface description here.
 *
 * History:
 *
 * ------------- --------------- -----------------------
 * Date			 Author			 Comment
 * ------------- --------------- -----------------------
 * 28/nov/2013   Fabio     Creation.
 *
 * @version 1.0
 * @since 28/nov/2013
 */
public interface Dao {
	<E> E getEntityById(EntityManager em, Class<E> entity, Object id);
	<E> Collection<E> filterEntities(EntityManager em, Class<E> entity, Map<String, Object> criteria);
}
