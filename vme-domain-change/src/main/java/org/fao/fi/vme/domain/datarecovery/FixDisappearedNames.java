package org.fao.fi.vme.domain.datarecovery;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.vme.dao.sources.vme.VmeDao;

public class FixDisappearedNames {

	@Inject
	ProduceList produceList;

	@Inject
	VmeDao vmeDao;

	public void fix() {
		Map<Long, MultiLingualString> map = produceList.loadFromCsv();
		List<MultiLingualString> l = vmeDao.loadObjects(MultiLingualString.class);
		int fixes = 0;
		int numerRegisteredWithoutString = 0;
		for (MultiLingualString m : l) {
			boolean registetedWithoutString = m.getStringMap() == null || m.getStringMap().size() == 0;
			if (registetedWithoutString) {
				numerRegisteredWithoutString++;
			}
			if (map.containsKey(m.getId()) && registetedWithoutString) {
				m.setStringMap(map.get(m.getId()).getStringMap());
				vmeDao.merge(m);
				fixes++;
			}
		}
		String message = "Total amount of MultiLingualStrings " + l.size() + ". Numer of Registered Without String "
				+ numerRegisteredWithoutString + ". Number of fixes applied: " + fixes + ".";
		System.out.println(message);
	}
}
