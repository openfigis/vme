package org.fao.fi.vme.domain.change2;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.Vme;
import org.vme.dao.sources.vme.VmeDao;

/**
 * 
 * 
 * 
 * 
 * @author Erik van Ingen
 * 
 */
public class VmeTypeChange {

	@Inject
	private VmeDao vmeDao;

	public void migrate() {

		vmeDao.loadObjects(Vme.class);

		System.out.println("Doing nothing, let Hibernate work. ");

	}

	private Map<String, Long> getMapping() {
		Map<String, Long> mapping = new HashMap<String, Long>();
		mapping.put("Risk area", 30l);
		return mapping;

	}

}
