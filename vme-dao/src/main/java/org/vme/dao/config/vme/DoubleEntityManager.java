package org.vme.dao.config.vme;

import javax.enterprise.inject.Typed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Update: Erik van Ingen, 12 September. My latest observations do not justify
 * the use of DoubleEntityManager. Therefore its use will be eleminated. It does
 * not solve the could not extract ResultSet problems.
 * 
 * 
 * 
 * 
 * 
 * The double in DoubleEntityManager has a 'double' meaning:
 * 
 * 1) There are 2 entitymanagers which are connecting to the VME Oracle DB. The
 * first is through vme-web and the next through rsg-web.
 * 
 * 2) When the RSG entitymanager changes the VME DB, the vme-web EntityManager
 * needs to be replaced with another one. Apparentely replacing is the simplest
 * and less risky way. Other methods have been explored like refresh, clear but
 * it all led to all sorts of Hibernate consistency complains.
 * 
 * This class gives the possibility to create a new one, when the RSG has
 * updated the underlying DB.
 * 
 * Apparently JPA does not envisage the use of 2 entitymanager to the same DB.
 * DoubleEntityManager solves the related issues when you do use 2
 * entitymanagers.
 * 
 * 
 * 
 * @author Erik van Ingen
 * @deprecated
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
