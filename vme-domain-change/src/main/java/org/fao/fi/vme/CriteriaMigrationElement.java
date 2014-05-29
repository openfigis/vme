package org.fao.fi.vme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.fao.fi.vme.domain.model.Criteria;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.domain.model.VmeCriteria;
import org.vme.dao.sources.vme.VmeDao;

public class CriteriaMigrationElement implements MigrationElement {
	@Inject
	private VmeDao vmeDao;

	@Override
	public void migrate() {

		List<Vme> vmeList = vmeDao.loadVmes();
		List<VmeCriteria> cList = vmeDao.loadObjects(VmeCriteria.class);
		Map<String, Long> cMap = new HashMap<String, Long>();
		for (VmeCriteria vmeCriteria : cList) {
			cMap.put(vmeCriteria.getName(), vmeCriteria.getId());
		}
		for (Vme vme : vmeList) {
			if (!StringUtils.isBlank(vme.getCriteria())
					&& (vme.getCriteriaList() == null || vme.getCriteriaList().size() == 0)) {
				System.out.println(vme.getCriteria());
				if (cMap.containsKey(vme.getCriteria())) {
					Criteria c = new Criteria();
					c.setId(cMap.get(vme.getCriteria()));
					c.setVme(vme);
					List<Criteria> newList = new ArrayList<Criteria>();
					newList.add(c);
					vme.setCriteriaList(newList);
				}
				vmeDao.merge(vme);
			}
		}

	}

}
