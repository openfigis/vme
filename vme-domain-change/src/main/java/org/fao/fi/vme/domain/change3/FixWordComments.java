package org.fao.fi.vme.domain.change3;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.vme.dao.sources.vme.VmeDao;

/**
 * deleting msword comments from string
 * 
 * @author Erik van Ingen
 *
 */
public class FixWordComments {

	private MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Inject
	VmeDao vmeDao;

	public void fix() {

		List<MultiLingualString> l = vmeDao.loadObjects(MultiLingualString.class);
		int fixes = 0;
		int inconsistent = 0;
		for (MultiLingualString m : l) {
			System.out.println("=============================================================" + m.getId());

			boolean valid = m.getStringMap() == null || m.getStringMap().size() == 1;
			if (valid) {
				String foundString = u.getEnglish(m);

				if (foundString != null) {

					Correction c = new Correction(foundString);
					if (c.corrected) {
						System.out.println("============applying fix=================================================");
						u.replaceEnglish(m, c.correctedString);
						System.out.println("============applying fix=================================================");

						vmeDao.merge(m);
						fixes++;
					}
					if (c.isInConsistent()) {
						inconsistent++;
					}
				}

			}
		}
		String message = "Total amount of MultiLingualStrings " + l.size() + ". Number of fixes applied: " + fixes
				+ ". Inconsists " + inconsistent;
		System.out.println(message);
	}
}
