package org.vme.dao.config.vme;

import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * 
 * @author Erik van Ingen
 *
 */
// typed because it should only be produced
@Typed
public class DoubleEntityManager {

	private EntityManager em;
	private EntityManagerFactory emf;

	/**
	 * Call this method to create the first Em. Or call this method to replace
	 * the old Em with a new one because the underlying DB has been updated by
	 * another Em.
	 */
	public void createNewEm() {
		this.em = emf.createEntityManager();
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManager getEm() {
		return em;
	}

}
