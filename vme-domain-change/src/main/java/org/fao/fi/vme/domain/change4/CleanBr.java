package org.fao.fi.vme.domain.change4;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.vme.dao.sources.vme.VmeDao;

/**
 * 
 * There are many strings with only <br>
 * , this logic replaces those with an empty string
 * 
 * Development : Total amount of MultiLingualStrings 6073. Number of fixes applied: 13.
 * 
 * QA: Total amount of MultiLingualStrings 6336. Number of fixes applied: 138.
 * 
 * Prod Total amount of MultiLingualStrings 7195. Number of fixes applied: 388.
 * 
 * @author Erik van Ingen
 *
 */
public class CleanBr {

	private String BR = "<br>";

	private MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Inject
	VmeDao vmeDao;

	public void fix() {

		List<MultiLingualString> l = vmeDao.loadObjects(MultiLingualString.class);
		int fixes = 0;
		for (MultiLingualString m : l) {
			if (m.getStringMap() != null) {
				if (u.getEnglish(m) != null && u.getEnglish(m).equals(BR)) {
					u.replaceEnglish(m, "");
					vmeDao.merge(m);
					fixes++;
				}
			}
		}
		String message = "Total amount of MultiLingualStrings " + l.size() + ". Number of fixes applied: " + fixes
				+ ".";
		System.out.println(message);
	}
}
