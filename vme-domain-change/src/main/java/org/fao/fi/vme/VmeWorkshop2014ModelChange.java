package org.fao.fi.vme;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.fao.fi.vme.domain.model.Criteria;
import org.fao.fi.vme.domain.model.Vme;
import org.vme.dao.sources.vme.VmeDao;

public class VmeWorkshop2014ModelChange {

	@Inject
	private VmeDao vmeDao;

	void migrate() {
		migrateCriteria();

	}

	private void migrateCriteria() {
		List<Vme> vmeList = vmeDao.loadVmes();
		for (Vme vme : vmeList) {
			if (StringUtils.isBlank(vme.getCriteria()) && vme.getCriteriaList() == null) {
				List<Criteria> cList = vme.getCriteriaList();
				Criteria c = new Criteria();
				cList.add(c);
			}
		}

	}
}
