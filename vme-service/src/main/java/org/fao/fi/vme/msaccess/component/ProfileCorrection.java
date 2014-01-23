package org.fao.fi.vme.msaccess.component;

import java.util.ArrayList;
import java.util.List;

import org.fao.fi.vme.domain.model.MultiLingualString;
import org.fao.fi.vme.domain.model.Profile;
import org.fao.fi.vme.domain.model.Vme;
import org.fao.fi.vme.msaccess.model.ObjectCollection;

public class ProfileCorrection {

	public void correct(List<ObjectCollection> objectCollectionList) {
		for (ObjectCollection objectCollection : objectCollectionList) {
			if (objectCollection.getClazz().equals(Vme.class)) {
				List<Object> list = objectCollection.getObjectList();
				for (Object object : list) {
					Vme vme = (Vme) object;
					cleanList(vme.getProfileList());
				}
			}
		}
	}

	private void cleanList(List<Profile> profileList) {
		List<Profile> doubles = new ArrayList<Profile>();
		for (int i = 0; i < profileList.size(); i++) {
			Profile c = profileList.get(i);
			int before = i - 1;
			if (before >= 0) {
				Profile b = profileList.get(before);
				if (dubbel(b.getDescriptionBiological(), c.getDescriptionBiological())
						&& dubbel(b.getDescriptionImpact(), c.getDescriptionImpact())
						&& dubbel(b.getDescriptionPhisical(), c.getDescriptionPhisical())
						&& dubbel(b.getGeoform(), c.getGeoform())) {
					doubles.add(c);

				}
			}
		}
		for (Profile dubbel : doubles) {
			profileList.remove(dubbel);
		}

	}

	private boolean dubbel(MultiLingualString s1, MultiLingualString s2) {
		boolean dubbel = true;
		if (s1 == null && s2 != null) {
			// from empty to filled
			dubbel = false;
		}
		if (s1 != null && s2 != null && !s1.equals(s2)) {
			// from filled to a different filled
			dubbel = false;
		}

		return dubbel;
	}
}
