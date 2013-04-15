package org.fao.fi.vme.dao.config;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class Piet {

	@TestDatabase
	@Inject
	private EntityManager em;
}
