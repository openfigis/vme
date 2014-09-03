package org.fao.fi.vme.domain.datarecovery;

import java.util.List;

import javax.inject.Inject;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.util.MultiLingualStringUtil;
import org.vme.dao.sources.vme.VmeDao;

/**
 * After having applied FixDisappearedNames with corrected 563 strings out of
 * 614 and we have lost 51 strings.
 * 
 * This fix fills the lost strings with LOST_STRING
 * 
 * @author Erik van Ingen
 *
 */
public class FixFillDisappearedNames {

	private String LOST_STRING = "LOST_STRING";

	private MultiLingualStringUtil u = new MultiLingualStringUtil();

	@Inject
	VmeDao vmeDao;

	public void fix() {

		List<MultiLingualString> l = vmeDao.loadObjects(MultiLingualString.class);
		int fixes = 0;
		int numerRegisteredWithoutString = 0;
		for (MultiLingualString m : l) {
			boolean registetedWithoutString = m.getStringMap() == null || m.getStringMap().size() == 0;
			if (registetedWithoutString) {
				numerRegisteredWithoutString++;
				MultiLingualString fill = u.english(LOST_STRING);
				m.setStringMap(fill.getStringMap());
				vmeDao.merge(m);
				fixes++;
			}
		}
		String message = "Total amount of MultiLingualStrings " + l.size() + ". Numer of Registered Without String "
				+ numerRegisteredWithoutString + ". Number of fixes applied: " + fixes + ".";
		System.out.println(message);
	}
}
